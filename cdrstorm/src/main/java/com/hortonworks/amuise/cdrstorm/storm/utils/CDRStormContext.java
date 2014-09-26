/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hortonworks.amuise.cdrstorm.storm.utils;

import java.util.Properties;

/**
 *
 * @author adammuise
 */
public class CDRStormContext {

    public Properties config;

    public CDRStormContext() {

        //TO-DO Implement a file based config file if you like...
        //This stuff is hard coded in one place so you can extend it
        Properties configcontext = new Properties();

        //Twitter4j Producer 
        configcontext.put("twitter4j.consumerkey","y4g8s2B7h0Wun80BIoEoriIMT");
        configcontext.put("twitter4j.consumersecretkey","7edLC5k2Wbz1TYMoeJL4OETgkntZa9r3XSyEeV2rFuqhbRFuRf");
        configcontext.put("twitter4j.accesstokenkey","14829490-ZQbxFrYMXbzbdg3w1ZjuCVJIWpDWcflxLbhGAdYfx");
        configcontext.put("twitter4j.accesstokensecretkey","GWegjsaUlQWQHGqTBvg09F1TrRC1ERLpIMkCociFDd48W");
        configcontext.put("twitter4j.filterwords","hadoop,hdfs,tez,hive,oozie,flume,kafka,mapreduce,knox,hortonworks");
        configcontext.put("twitter4j.kafkatopic","twitter");
        configcontext.put("twitter4j.brokerlist","192.168.37.130:9092");
        configcontext.put("twitter4j.serializer","kafka.serializer.StringEncoder");
        configcontext.put("twitter4j.requiredacks","1");
        
        //CDR Test Data Producer
        configcontext.put("cdr.kafkatopic","cdr");
        configcontext.put("cdr.brokerlist","192.168.37.130:9092");
        configcontext.put("cdr.serializer","kafka.serializer.StringEncoder");
        configcontext.put("cdr.requiredacks","1");

        this.config = configcontext;
                
    }

}
