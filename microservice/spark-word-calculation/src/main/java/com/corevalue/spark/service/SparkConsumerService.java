package com.corevalue.spark.service;

import com.corevalue.spark.config.KafkaConsumerConfiguration;
import com.corevalue.spark.model.RSSItemDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.spark.SparkConf;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka010.ConsumerStrategies;
import org.apache.spark.streaming.kafka010.KafkaUtils;
import org.apache.spark.streaming.kafka010.LocationStrategies;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import scala.Tuple2;

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
        JavaStreamingContext ssc = new JavaStreamingContext(conf, Durations.seconds(2));

        JavaInputDStream<ConsumerRecord<String, RSSItemDTO>> messages = KafkaUtils.createDirectStream(
                ssc,
                LocationStrategies.PreferConsistent(),
                ConsumerStrategies.Subscribe(topics, kafkaConsumer.consumerConfigs())
        );

        JavaDStream<String> lines = messages.map(data -> {
            int index = StringUtils.ordinalIndexOf(data.value().getUrl(), "/", 3);
            return data.value().getUrl().substring(0, index);
        });

        JavaPairDStream<String, Integer> urlCounts = lines
                .mapToPair(url -> new Tuple2<>(url, 1))
                .reduceByKey(Integer::sum);

//        lines.map(data -> data.getUrl().toLowerCase())
//                .mapToPair(url -> new Tuple2<String, Integer>(url, 1))
//                .reduceByKey(Integer::sum)
//                .mapToPair(Tuple2::swap)
//                .map(Tuple2::_2);

        urlCounts.print();

        ssc.start();
        try {
            ssc.awaitTermination();
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }
    }
}
