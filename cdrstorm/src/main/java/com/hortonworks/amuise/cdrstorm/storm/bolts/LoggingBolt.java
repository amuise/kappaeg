/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hortonworks.amuise.cdrstorm.storm.bolts;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Tuple;
import java.util.Map;
import java.util.Properties;
import org.apache.log4j.Logger;

/**
 *
 * @author adammuise
 */
public class LoggingBolt implements IRichBolt {

    	private static final Logger logger = Logger.getLogger(LoggingBolt.class);
	
	private OutputCollector outputcollector;

    
    @Override
    public void declareOutputFields(OutputFieldsDeclarer ofd) {
       //nothing to do
    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
     return null;
    }

    @Override
    public void prepare(Map map, TopologyContext tc, OutputCollector oc) {
        this.outputcollector = oc;
    }

    @Override
    public void execute(Tuple tuple) {
        logger.warn("LoggingBolt processing tuple|" + tuple +"|");
        outputcollector.ack(tuple);
    }

    @Override
    public void cleanup() {
        logger.info("LoggingBolt.cleanup() called");
    }

    public LoggingBolt() {
    }
    
}
