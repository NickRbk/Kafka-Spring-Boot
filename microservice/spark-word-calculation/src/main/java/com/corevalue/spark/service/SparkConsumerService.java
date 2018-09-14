package com.corevalue.spark.service;

import com.corevalue.spark.config.KafkaConsumerConfiguration;
import com.corevalue.spark.model.RSSItemDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.spark.SparkConf;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka010.ConsumerStrategies;
import org.apache.spark.streaming.kafka010.KafkaUtils;
import org.apache.spark.streaming.kafka010.LocationStrategies;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SparkConsumerService {

    @Value("#{'${kafka.topic}'.split(',')}")
    private List<String> topics;

    private final SparkConf conf;
    private final KafkaConsumerConfiguration kafkaConsumer;

    public SparkConsumerService(SparkConf conf, KafkaConsumerConfiguration kafkaConsumer) {
        this.conf = conf;
        this.kafkaConsumer = kafkaConsumer;
    }

    public void run() {
        JavaStreamingContext ssc = new JavaStreamingContext(conf, Durations.seconds(10));

        JavaInputDStream<ConsumerRecord<String, RSSItemDTO>> messages = KafkaUtils.createDirectStream(
                ssc,
                LocationStrategies.PreferConsistent(),
                ConsumerStrategies.Subscribe(topics, kafkaConsumer.consumerConfigs())
        );

        JavaDStream<RSSItemDTO> lines = messages.map(ConsumerRecord::value);

        lines.count().print();

        ssc.start();
        try {
            ssc.awaitTermination();
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }
    }
}
