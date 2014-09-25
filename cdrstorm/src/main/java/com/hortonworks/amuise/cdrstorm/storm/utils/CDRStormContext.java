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
        config = new Properties();

        //Twitter4j Producer 
        config.put("twitter4j.consumerkey","nHvIMHu64tv8Mzsce1QWgZE3M");
        config.put("twitter4j.consumersecretkey","UbtyI1aLlR8iDvg20G8tW8QBcNLSuB4vW1dIkg1hEDnAXvBvkD");
        config.put("twitter4j.accesstokenkey","ZQbxFrYMXbzbdg3w1ZjuCVJIWpDWcflxLbhGAdYfx");
        config.put("twitter4j.accesstokensecretkey","GWegjsaUlQWQHGqTBvg09F1TrRC1ERLpIMkCociFDd48W");
        config.put("twitter4j.kafkatopic","twitter");
        config.put("twitter4j.brokerlist","192.168.37.130:9092");
        config.put("twitter4j.serializer","kafka.serializer.StringEncoder");
        config.put("twitter4j.requiredacks","1");
        
        //CDR Test Data Producer
        config.put("cdr.kafkatopic","cdr");
        config.put("cdr.brokerlist","192.168.37.130:9092");
        config.put("cdr.serializer","kafka.serializer.StringEncoder");
        config.put("cdr.requiredacks","1");

        
                
    }

}
