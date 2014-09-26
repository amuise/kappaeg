/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hortonworks.amuise.cdrstorm.storm.topologies;

import backtype.storm.Config;
import backtype.storm.StormSubmitter;
import backtype.storm.spout.SchemeAsMultiScheme;
import backtype.storm.topology.TopologyBuilder;
import com.hortonworks.amuise.cdrstorm.storm.bolts.LoggingBolt;
import com.hortonworks.amuise.cdrstorm.storm.spouts.CDRScheme;
import com.hortonworks.amuise.cdrstorm.storm.spouts.TwitterScheme;
import com.hortonworks.amuise.cdrstorm.storm.utils.CDRStormContext;
import java.util.Properties;
import org.apache.log4j.Logger;
import storm.kafka.BrokerHosts;
import storm.kafka.KafkaSpout;
import storm.kafka.SpoutConfig;
import storm.kafka.ZkHosts;

/**
 *
 * @author adammuise
 */
public class CDRStormTopology {

    Properties globalconfigs;
        private static final Logger logger = Logger.getLogger(CDRStormTopology.class);

    public CDRStormTopology() {
        CDRStormContext cdrstormcontext = new CDRStormContext();
        this.globalconfigs = cdrstormcontext.config;

    }

    public void setupLoggingBolts(TopologyBuilder bldr) {

        LoggingBolt cdrLoggingBolt = new LoggingBolt();
        bldr.setBolt("cdrLoggingBolt", cdrLoggingBolt, 4).shuffleGrouping("cdrKafkaSpout");

        LoggingBolt twitterLoggingBolt = new LoggingBolt();
        bldr.setBolt("twitterLoggingBolt", twitterLoggingBolt, 4).shuffleGrouping("twitterKafkaSpout");

    }

    public void setupTwitterSpout(TopologyBuilder bldr) {
        KafkaSpout kafkaSpout = constructTwitterKafkaSpout();

        int spoutCount = Integer.valueOf(globalconfigs.getProperty("cdrstorm.kafkaspout.spout.thread.count"));

        bldr.setSpout("twitterKafkaSpout", kafkaSpout, spoutCount);
    }

    public void setupCDRSpout(TopologyBuilder bldr) {
        KafkaSpout kafkaSpout = constructCDRKafkaSpout();

        int spoutCount = Integer.valueOf(globalconfigs.getProperty("cdrstorm.kafkaspout.spout.thread.count"));

        bldr.setSpout("cdrKafkaSpout", kafkaSpout, spoutCount);
    }

    private KafkaSpout constructCDRKafkaSpout() {
        BrokerHosts zkhosts = new ZkHosts(globalconfigs.getProperty("cdrstorm.kafkaspout.zkhosts"));
        String topic = globalconfigs.getProperty("cdr.kafkatopic");
        String zkRoot = globalconfigs.getProperty("cdrstorm.kafkaspout.zkroot");
        String consumerGroupId = globalconfigs.getProperty("cdrstorm.kafkaspout.cdr.consumergroupid");
        SpoutConfig spoutConfig = new SpoutConfig(zkhosts, topic, zkRoot, consumerGroupId);
        spoutConfig.scheme = new SchemeAsMultiScheme(new CDRScheme());
        KafkaSpout kafkaspout = new KafkaSpout(spoutConfig);
        return kafkaspout;
    }

    private KafkaSpout constructTwitterKafkaSpout() {
        BrokerHosts zkhosts = new ZkHosts(globalconfigs.getProperty("cdrstorm.kafkaspout.zkhosts"));
        String topic = globalconfigs.getProperty("twitter4j.kafkatopic");
        String zkRoot = globalconfigs.getProperty("cdrstorm.kafkaspout.zkroot");
        String consumerGroupId = globalconfigs.getProperty("cdrstorm.kafkaspout.cdr.consumergroupid");
        SpoutConfig spoutConfig = new SpoutConfig(zkhosts, topic, zkRoot, consumerGroupId);
        
        //Create scheme for Twitter
        spoutConfig.scheme = new SchemeAsMultiScheme(new TwitterScheme());
        
        KafkaSpout kafkaspout = new KafkaSpout(spoutConfig);
        return kafkaspout;
    }

    private void buildAndSubmitTopology() throws Exception {
        TopologyBuilder builder = new TopologyBuilder();

        setupCDRSpout(builder);

        setupTwitterSpout(builder);

        setupLoggingBolts(builder);

        //submit
        /* This conf is for Storm and it needs be configured with things like the following:
         * 	Zookeeper server, nimbus server, ports, etc... All of this configuration will be picked up
         * in the ~/.storm/storm.yaml file that will be located on each storm node.
         */
        Config conf = new Config();
        conf.setDebug(true);
        /* Set the number of workers that will be spun up for this topology. 
         * Each worker represents a JVM where executor thread will be spawned from */
        Integer topologyWorkers = Integer.valueOf(globalconfigs.getProperty("cdrstorm.topologyworkers"));
        conf.put(Config.TOPOLOGY_WORKERS, topologyWorkers);

        try {
            StormSubmitter.submitTopology("cdrstorm", conf, builder.createTopology());
        } catch (Exception e) {
            logger.error("Error submiting Topology", e);
        }
    }

    public static void main(String[] args) {
        CDRStormTopology cdrstorm = new CDRStormTopology();
        try {
            cdrstorm.buildAndSubmitTopology();
        } catch (Exception e) {
            System.out.println("Error starting topology: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
