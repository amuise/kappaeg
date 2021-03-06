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
        //This stuff is hard coded in one place so you can extend it.
        //Was thinking about dynamic config values for different environments.
        Properties configcontext = new Properties();

        //Twitter4j Producer 
        configcontext.put("twitter4j.consumerkey", "");
        configcontext.put("twitter4j.consumersecretkey", "");
        configcontext.put("twitter4j.accesstokenkey", "");
        configcontext.put("twitter4j.accesstokensecretkey", "");
        configcontext.put("twitter4j.filterwords", "hadoop,hdfs,tez,hive,oozie,flume,kafka,mapreduce,knox,hortonworks");
        configcontext.put("twitter4j.kafkatopic", "twitter");
        configcontext.put("twitter4j.brokerlist", "localhost:9092");
        configcontext.put("twitter4j.serializer", "kafka.serializer.StringEncoder");
        configcontext.put("twitter4j.requiredacks", "1");
        configcontext.put("twitter4j.schema", "screenname,"
                + "createdat,"
                + "retweetcount,"
                + "source,"
                + "text");

        //Kafka Spout info
        configcontext.put("cdrstorm.kafkaspout.zkhosts", "localhost:2181");
        configcontext.put("cdrstorm.kafkaspout.zkroot", "");
        configcontext.put("cdrstorm.kafkaspout.cdr.consumergroupid", "group1");
        configcontext.put("cdrstorm.kafkaspout.spout.thread.count", "1");
        configcontext.put("cdrstorm.kafkaspout.bolt.thread.count", "2");

        //Storm config
        configcontext.put("cdrstorm.topologyworkers", "4");

        //CDR Test Data Producer 
        configcontext.put("cdr.kafkatopic", "cdr");
        configcontext.put("cdr.brokerlist", "localhost:9092");
        configcontext.put("cdr.serializer", "kafka.serializer.StringEncoder");
        configcontext.put("cdr.requiredacks", "1");
        configcontext.put("cdr.schema", "subscriber_no,"
                + "subscriber_no_char,"
                + "record_sequence_number,"
                + "served_imsi,"
                + "record_opening_time,"
                + "served_msisdn,"
                + "data_volume_uplink_archive,"
                + "data_volume_downlink_archive,"
                + "routing_area,"
                + "location_area_code,"
                + "access_point_name,"
                + "time_key,"
                + "switch_id,"
                + "reporting_centre_id,"
                + "eng_date_id,"
                + "audit_key,"
                + "destination_url,"
                + "spid,"
                + "service_class_group,"
                + "content_delivered,"
                + "event_protocol_type,"
                + "wireless_generation,"
                + "event_count,"
                + "domain,"
                + "cdr_type_ind,"
                + "served_imei,"
                + "sgsn_address,"
                + "served_pdp_address,"
                + "plmn_id,"
                + "duration,"
                + "charging_id,"
                + "cell_id,"
                + "customer_type,"
                + "monum,"
                + "tracking_area_code,"
                + "eutran_cellid,"
                + "record_opening_date");

        this.config = configcontext;

    }

}
