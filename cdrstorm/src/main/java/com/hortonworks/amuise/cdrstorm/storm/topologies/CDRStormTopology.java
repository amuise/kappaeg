/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hortonworks.amuise.cdrstorm.storm.topologies;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.spout.SchemeAsMultiScheme;
import backtype.storm.testing.TestWordSpout;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.utils.Utils;
import backtype.storm.topology.IRichSpout;
import storm.kafka.BrokerHosts;
import storm.kafka.KafkaSpout;
import storm.kafka.SpoutConfig;
import storm.kafka.ZkHosts;

/**
 *
 * @author adammuise
 */
public class CDRStormTopology {

    public CDRStormTopology() {

    }

    public static void main(String[] args) throws Exception {
        TopologyBuilder builder = new TopologyBuilder();
 

    }
    
    public void setupDebugBolt(TopologyBuilder bldr) {
        return; 
    }
    
    public void setupTwitterSpout(TopologyBuilder bldr) {

    
    }
    
    public void setupCDRSpout(TopologyBuilder bldr) {
        
    }
}
