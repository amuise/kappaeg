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
public class SampleProducer extends Thread {

    private final kafka.javaapi.producer.Producer<String, String> producer;
    private final String topic = "test";
    private final Properties props = new Properties();

    public SampleProducer() {

        props.put("metadata.broker.list", "192.168.37.130:9092");
        props.put("serializer.class", "kafka.serializer.StringEncoder");
        props.put("request.required.acks", "1");

        producer = new kafka.javaapi.producer.Producer<String, String>(new ProducerConfig(props));

    }

    public void run() {
        int messageNo = 1;
        while (true) {
            try {
                String messageStr = new String("Message_" + messageNo);
                producer.send(new KeyedMessage<String, String>(topic, messageStr));
                messageNo++;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SampleProducer producerThread = new SampleProducer();
        producerThread.start();

    }

}
