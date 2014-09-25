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
    
  private final kafka.javaapi.producer.Producer<Integer, String> producer;
  private final String topic = "twitter";
  private final Properties props = new Properties();

  public SampleProducer()
  {
    props.put("serializer.class", "kafka.serializer.StringEncoder");
    props.put("metadata.broker.list", "localhost:9092");
    // Use random partitioner. Don't need the key type. Just set it to Integer.
    // The message is of type String.
    producer = new kafka.javaapi.producer.Producer<Integer, String>(new ProducerConfig(props));

  }
  
  public void run() {
    int messageNo = 1;
    while(true)
    {
      String messageStr = new String("Message_" + messageNo);
      producer.send(new KeyedMessage<Integer, String>(topic, messageStr));
      messageNo++;
    }
  }
  
    public static void main(String[] args)
  {
    SampleProducer producerThread = new SampleProducer();
    producerThread.start();

  }
    
}
