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
        List<Object> tvals = new ArrayList<Object>();
        logger.debug("CDRScheme Bytes.toString: " + bytes.toString());
        
        try {
            String payload = new String(bytes, "UTF-8");
            logger.debug("TwitterScheme payload (from Stirng constructor): " + payload);
            String[] items = payload.split("\\|");

            for (String item : items) {
                tvals.add(item);
            }

        } catch (Exception e) {
            return tvals;
        }

        return tvals;
    }

    @Override
    public Fields getOutputFields() {
        List<String> predefinedScheme = new ArrayList<String>();
        String[] fields = globalconfigs.getProperty("twitter4j.schema").split(",");
        for (String field : fields) {
            predefinedScheme.add(field);
        }

        Fields tSchemeFields = new Fields(predefinedScheme);

        return tSchemeFields;
    }

}
