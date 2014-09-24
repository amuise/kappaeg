/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hortonworks.amuise.cdrstorm.kafka.producers;

import java.util.Properties;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

/**
 *
 * @author adammuise
 */
public class CDRTestDataProducer {

    final static String zookeeper_url = "127.0.0.1:2181";
    final static String kafka_url = "localhost";
    final static int kafkaServerPort = 9092;
    final static String cdr_topic = "cdr";
    final static String twitter_topic = "twitter";

    Properties props = new Properties();
    kafka.javaapi.producer.Producer<Integer, String> producer;

    public CDRTestDataProducer() {
        props.put("serializer.class", "kafka.serializer.StringEncoder");
        props.put("metadata.broker.list", "localhost:9092");
        producer = new kafka.javaapi.producer.Producer<Integer, String>(new ProducerConfig(props));

    }

}
