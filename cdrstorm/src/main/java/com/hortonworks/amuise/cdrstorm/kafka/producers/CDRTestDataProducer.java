/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hortonworks.amuise.cdrstorm.kafka.producers;

import com.hortonworks.amuise.cdrstorm.storm.utils.CDRStormContext;
import com.hortonworks.amuise.cdrstorm.storm.utils.Toolbox;
import java.util.ArrayList;
import java.util.Properties;

import kafka.javaapi.producer.Producer;
import kafka.producer.ProducerConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.FilterQuery;

import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.json.DataObjectFactory;

/**
 *
 * @author adammuise Twitter Producer inspired by:
 * https://github.com/NFLabs/kafka-twitter
 *
 */
public class CDRTestDataProducer {

    private static final Logger logger = LoggerFactory.getLogger(CDRTestDataProducer.class);
    private TwitterStream twitterStream;

    Properties globalconfigs;

    public CDRTestDataProducer() {
        CDRStormContext cdrstormcontext = new CDRStormContext();
        this.globalconfigs = cdrstormcontext.config;

    }

    private void start() {

        /**
         * Kafka Twitter Producer properties *
         */
        Properties twitterconprops = new Properties();
        twitterconprops.put("metadata.broker.list", globalconfigs.getProperty("twitter4j.brokerlist"));
        twitterconprops.put("serializer.class", globalconfigs.getProperty("twitter4j.serializer"));
        twitterconprops.put("request.required.acks", globalconfigs.getProperty("twitter4j.requiredacks"));
        ProducerConfig twitterproducerconfig = new ProducerConfig(twitterconprops);
        final Producer<String, String> twitterproducer = new Producer<String, String>(twitterproducerconfig);

        /**
         * Kafka CDR Producer properties *
         */
        Properties cdrconprops = new Properties();
        cdrconprops.put("metadata.broker.list", globalconfigs.getProperty("cdr.brokerlist"));
        cdrconprops.put("serializer.class", globalconfigs.getProperty("cdr.serializer"));
        cdrconprops.put("request.required.acks", globalconfigs.getProperty("cdr.requiredacks"));
        ProducerConfig cdrproducerconfig = new ProducerConfig(cdrconprops);
        final Producer<String, String> cdrproducer = new Producer<String, String>(cdrproducerconfig);

        /**
         * Twitter4j properties *
         */
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setOAuthConsumerKey(globalconfigs.getProperty("twitter4j.consumerkey"));
        cb.setOAuthConsumerSecret(globalconfigs.getProperty("twitter4j.consumersecretkey"));
        cb.setOAuthAccessToken(globalconfigs.getProperty("twitter4j.accesstokenkey"));
        cb.setOAuthAccessTokenSecret(globalconfigs.getProperty("twitter4j.accesstokensecretkey"));
        cb.setJSONStoreEnabled(true);
        cb.setIncludeEntitiesEnabled(true);

        twitterStream = new TwitterStreamFactory(cb.build()).getInstance();

        /**
         * Twitter listener *
         */
        StatusListener listener = new StatusListener() {
            // The onStatus method is executed every time a new tweet comes
            // in.
            public void onStatus(Status status) {

                StringBuilder sb = new StringBuilder();
                sb.append(status.getUser().getScreenName());
                sb.append("|");
                sb.append(status.getCreatedAt());
                sb.append("|");
                sb.append(status.getRetweetCount());
                sb.append("|");
                sb.append(status.getSource());
                sb.append("|");
                sb.append(status.getText());
                sb.append("|");
                sb.append(DataObjectFactory.getRawJSON(status));

                System.out.println("_________________________________________________________");
                System.out.println(sb.toString());
                System.out.println("_________________________________________________________");

                /*
                KeyedMessage<String, String> twitterdata = new KeyedMessage<String, String>(globalconfigs.getProperty("twitter4j.kafkatopic"), sb.toString());
                twitterproducer.send(twitterdata);
                
                
                //call CDR create message
                String cdrmessage = createCDRMessage(status.getText());
                //call producer to cdr
                KeyedMessage<String, String> cdrmessagedata = new KeyedMessage<String, String>(globalconfigs.getProperty("cdr.kafkatopic"), sb.toString());
                cdrproducer.send(cdrmessagedata);
                */        
            }

            public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
            }

            public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
            }

            public void onScrubGeo(long userId, long upToStatusId) {
            }

            public void onException(Exception ex) {
                System.out.println("General Exception: shutting down Twitter sample stream...");
                System.out.println(ex.getMessage());
                ex.printStackTrace();
                twitterStream.shutdown();
            }

            public void onStallWarning(StallWarning warning) {
            }
        };

        twitterStream.addListener(listener);

        // Filter stream with targeted words
        String filterstring = globalconfigs.getProperty("twitter4j.filterwords");
        FilterQuery filterq = new FilterQuery();
        filterq.track(filterstring.split(","));
        twitterStream.filter(filterq);

        //twitterStream.sample();
    }

    public static void main(String[] args) {
        try {
            System.out.println("Starting the CDRTestDataProducer...");
            CDRTestDataProducer cdrtestgen = new CDRTestDataProducer();
            cdrtestgen.start();

        } catch (Exception e) {
            logger.info(e.getMessage());
        }
    }
    
    private String createCDRMessage(String rawTweetText) {
        
        Toolbox tb = new Toolbox();
        ArrayList<String> urls = tb.extractURLfromString(rawTweetText);
        
        
        
        return null;
    }

}
