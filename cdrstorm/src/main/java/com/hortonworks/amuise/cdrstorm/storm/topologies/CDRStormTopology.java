/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hortonworks.amuise.cdrstorm.storm.topologies;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.testing.TestWordSpout;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.utils.Utils;
import backtype.storm.topology.IRichSpout;

/**
 *
 * @author adammuise
 */
public class CDRStormTopology {

    public CDRStormTopology() {

    }

    public static void main(String[] args) throws Exception {
        TopologyBuilder builder = new TopologyBuilder();
        
        

        builder.setSpout("word", new TestWordSpout(), 10);
//    builder.setBolt("exclaim1", new ExclamationBolt(), 3).shuffleGrouping("word");
//    builder.setBolt("exclaim2", new ExclamationBolt(), 2).shuffleGrouping("exclaim1");

        Config conf = new Config();
        conf.setDebug(true);

        conf.setNumWorkers(3);

        StormSubmitter.submitTopology(args[0], conf, builder.createTopology());

    }
    
    public void setupDebugBolt(TopologyBuilder bldr) {
        return; 
    }
    
}
