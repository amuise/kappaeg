/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hortonworks.amuise.cdrstorm.storm.spouts;

import backtype.storm.spout.Scheme;
import backtype.storm.tuple.Fields;
import com.hortonworks.amuise.cdrstorm.storm.utils.CDRStormContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.apache.log4j.Logger;

/**
 *
 * @author adammuise
 */
public class TwitterScheme implements Scheme {

    private static final Logger logger = Logger.getLogger(TwitterScheme.class);
    Properties globalconfigs;

    public TwitterScheme() {
        CDRStormContext ctx = new CDRStormContext();
        this.globalconfigs = ctx.config;
    }

    @Override
    public List<Object> deserialize(byte[] bytes) {
        List<Object> tvals = new ArrayList();
        String payload = "";
        try {
            payload = new String(bytes, "UTF-8");
        } catch (Exception e) {
            tvals.add("BAD");
        }
        tvals.add(payload);
        return tvals;
    }

    @Override
    public Fields getOutputFields() {

        return new Fields("Tweet");
        
    }

}
