/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hortonworks.amuise.cdrstorm.storm.topologies;

import backtype.storm.spout.SchemeAsMultiScheme;
import backtype.storm.topology.TopologyBuilder;
import com.hortonworks.amuise.cdrstorm.storm.spouts.CDRScheme;
import com.hortonworks.amuise.cdrstorm.storm.utils.CDRStormContext;
import java.util.Properties;
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

    public CDRStormTopology() {
        CDRStormContext cdrstormcontext = new CDRStormContext();
        this.globalconfigs = cdrstormcontext.config;

    }

    public void setupDebugBolt(TopologyBuilder bldr) {
        return;
    }

    public void setupTwitterSpout(TopologyBuilder bldr) {

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

    private void buildAndSubmitTopology() throws Exception {
        TopologyBuilder builder = new TopologyBuilder();
        setupCDRSpout(builder);
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
