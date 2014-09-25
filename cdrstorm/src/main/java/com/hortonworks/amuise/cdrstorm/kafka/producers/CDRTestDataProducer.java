/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hortonworks.amuise.cdrstorm.kafka.producers;

import java.util.Properties;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
 * @author adammuise 
 * Twitter Producer inspired by:
 *          https://github.com/NFLabs/kafka-twitter
 * 
 */
public class CDRTestDataProducer {

    private static final Logger logger = LoggerFactory.getLogger(CDRTestDataProducer.class);

    final static String CDR_TOPIC = "cdr";
    final static String TWITTER_TOPIC = "twitter";

    /**
     * Information necessary for accessing the Twitter API
     */
    private TwitterStream twitterStream;

    public static final String CONSUMER_KEY_KEY = "nHvIMHu64tv8Mzsce1QWgZE3M";
    public static final String CONSUMER_SECRET_KEY = "UbtyI1aLlR8iDvg20G8tW8QBcNLSuB4vW1dIkg1hEDnAXvBvkD";
    public static final String ACCESS_TOKEN_KEY = "14829490-ZQbxFrYMXbzbdg3w1ZjuCVJIWpDWcflxLbhGAdYfx";
    public static final String ACCESS_TOKEN_SECRET_KEY = "GWegjsaUlQWQHGqTBvg09F1TrRC1ERLpIMkCociFDd48W";



    public static final String BROKER_LIST = "192.168.37.130:9092";
    public static final String SERIALIZER = "kafka.serializer.StringEncoder";
    public static final String REQUIRED_ACKS = "0";


    Properties props = new Properties();
    kafka.javaapi.producer.Producer<Integer, String> producer;

    private void start() {

        /**
         * Kafka Producer properties *
         */
        Properties props = new Properties();
        props.put("metadata.broker.list", BROKER_LIST);
        props.put("serializer.class", SERIALIZER);
        props.put("request.required.acks", REQUIRED_ACKS);


        ProducerConfig config = new ProducerConfig(props);

        final Producer<String, String> producer = new Producer<String, String>(config);

        /**
         * Twitter4j properties *
         */
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setOAuthConsumerKey(CONSUMER_KEY_KEY);
        cb.setOAuthConsumerSecret(CONSUMER_SECRET_KEY);
        cb.setOAuthAccessToken(ACCESS_TOKEN_KEY);
        cb.setOAuthAccessTokenSecret(ACCESS_TOKEN_SECRET_KEY);
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
                // The EventBuilder is used to build an event using the
                // the raw JSON of a tweet
                //logger.info(status.getUser().getScreenName() + ": " + status.getText());
                System.out.println("Tweet|" + status.getUser().getScreenName() + ": " + status.getText() + "|");

                KeyedMessage<String, String> data = new KeyedMessage<String, String>(TWITTER_TOPIC, DataObjectFactory.getRawJSON(status));
                
                producer.send(data);

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

        twitterStream.sample();

    
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

}
