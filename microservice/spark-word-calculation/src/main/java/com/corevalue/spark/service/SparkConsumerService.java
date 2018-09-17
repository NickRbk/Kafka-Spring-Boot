package com.corevalue.spark.service;

import com.corevalue.spark.config.KafkaConsumerConfiguration;
import com.corevalue.spark.model.RSSItemDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.Optional;
import org.apache.spark.api.java.function.FilterFunction;
import org.apache.spark.api.java.function.FlatMapFunction;

import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.*;
import org.apache.spark.sql.streaming.OutputMode;
import org.apache.spark.sql.streaming.StreamingQuery;
import org.apache.spark.sql.streaming.StreamingQueryException;
import org.apache.spark.sql.types.*;
//import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka010.ConsumerStrategies;
import org.apache.spark.streaming.kafka010.KafkaUtils;
import org.apache.spark.streaming.kafka010.LocationStrategies;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import scala.Function2;
import scala.Tuple2;

import java.util.Arrays;
import java.util.List;


import static org.apache.spark.sql.functions.from_json;
import static org.apache.spark.sql.functions.col;

//import static com.datastax.spark.connector.japi.CassandraJavaUtil.javaFunctions;
//import static com.datastax.spark.connector.japi.CassandraJavaUtil.mapToRow;

@Service
@Slf4j
public class SparkConsumerService {

//    private static JavaSparkContext sc;

    @Value("#{'${kafka.topic}'.split(',')}")
    private List<String> topics;

    @Value(value = "${kafka.topic}")
    private String topic;

    @Value(value = "${spring.kafka.consumer.bootstrap-servers}")
    private String bootstrapServers;

    private final JavaStreamingContext ssc;
    private final SparkSession spark;
    private final KafkaConsumerConfiguration kafkaConsumer;

    public SparkConsumerService(JavaStreamingContext ssc,
                                SparkSession spark,
                                KafkaConsumerConfiguration kafkaConsumer) {
        this.ssc = ssc;
        this.spark = spark;
        this.kafkaConsumer = kafkaConsumer;
    }

    public void run() {

        ssc.checkpoint("~/Desktop/checkpoint");
        ssc.sparkContext().setLogLevel("ERROR");

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
                .updateStateByKey((values, state) -> {
                    Integer newSum = state.or(0);
                    Integer prevSum = values.stream().reduce((i1, i2) -> i1 + i2).orElse(0);
                    newSum += prevSum;
                    return Optional.of(newSum);
                });

        urlCounts.print();

        ssc.start();
        try {
            ssc.awaitTermination();
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }

//        StructType schema = new StructType()
//                .add("url", DataTypes.StringType)
//                .add("title", DataTypes.StringType)
//                .add("type", DataTypes.StringType)
//                .add("description", DataTypes.StringType)
//                .add("publishedDate", DataTypes.LongType)
//                .add("author", DataTypes.StringType);
//
//        Dataset<RSSItemDTO> rssItemDS = spark
//                .readStream()
//                .format("kafka")
//                .option("kafka.bootstrap.servers", bootstrapServers)
////                .option("failOnDataLoss", false)
//                .option("subscribe", topic)
//                .load()
//                .select(from_json(col("value").cast("string"), schema).as("data"))
//                .select("data.*")
//                .as(Encoders.bean(RSSItemDTO.class));
//
//        Dataset<String> urlDS = rssItemDS.map((MapFunction<RSSItemDTO, String>) data -> {
//            int index = StringUtils.ordinalIndexOf(data.getUrl(), "/", 3);
//            return data.getUrl().substring(0, index);
//        }, Encoders.STRING());
//
//
////        Dataset<String> words = urlDS.flatMap((FlatMapFunction<String, String>) w -> {
////           return Arrays.asList(w.toLowerCase().split(" ")).iterator();
////        }, Encoders.STRING());
////                .filter((FilterFunction<String>) String::isEmpty)
////                .coalesce(1);
//
//
//        Dataset<Row> scoring = urlDS.groupBy("value")
//                .count()
//                .orderBy(col("count").desc());
////                .toDF("word", "count")
////                .withWatermark("timestamp", "1 minutes");
////        JavaPairRDD<String, Integer> totalCounter = urlDS.javaRDD()
////                .mapToPair(url -> new Tuple2<>(url, 1))
////                .reduceByKey((i1, i2) -> i1 + i2);
////
//        StreamingQuery console = scoring
//                .writeStream()
//                .format("console")
////                .option("checkpointLocation", "~/Desktop/checkpoint")
//                .outputMode(OutputMode.Complete())
//                .start();
//
//        try {
//            console.awaitTermination();
//        } catch (StreamingQueryException e) {
//            e.printStackTrace();
//        }
    }
}
