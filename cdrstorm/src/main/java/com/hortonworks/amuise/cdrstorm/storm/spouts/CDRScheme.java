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

/**
 *
 * @author adammuise
 */
public class CDRScheme implements Scheme {

    Properties globalconfigs;
    
    public CDRScheme() {
        CDRStormContext ctx = new CDRStormContext();
        this.globalconfigs = ctx.config;
    }

    @Override
    public List<Object> deserialize(byte[] bytes) {
        List<Object> cdrvals = new ArrayList<Object>();
        try {
            String payload = new String(bytes, "UTF-8");
            String[] items = payload.split("|");
            for (String item : items) {
                cdrvals.add(item);
            }
            
        } catch (Exception e) {
            return cdrvals;
        }
        
        
        
        return cdrvals;
    }

    @Override
    public Fields getOutputFields() {
       List<String> predefinedCDRScheme = new ArrayList<String>();
       String[] cdrFields = globalconfigs.getProperty("cdr.schema").split(",");
       for (String field : cdrFields) {
           predefinedCDRScheme.add(field);
       }
        
        Fields cdrSchemeFields = new Fields(predefinedCDRScheme);

        return cdrSchemeFields;
    }
    
    
}
