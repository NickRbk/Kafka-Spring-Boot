package com.corevalue.spark.service;

import com.corevalue.spark.model.RSSItemDTO;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang.StringUtils;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.*;

import org.apache.spark.sql.streaming.OutputMode;
import org.apache.spark.sql.streaming.StreamingQuery;
import org.apache.spark.sql.streaming.StreamingQueryException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static org.apache.spark.sql.functions.from_json;
import static org.apache.spark.sql.functions.col;

import static com.corevalue.spark.config.SchemaDefinitions.rssSchema;

@Service
@Slf4j
public class SparkConsumerService {
    @Value(value = "${kafka.topic}")
    private String topic;

    @Value(value = "${spring.kafka.consumer.bootstrap-servers}")
    private String bootstrapServers;

    private final SparkSession spark;

    public SparkConsumerService(SparkSession spark) {
        this.spark = spark;
    }

    public void run() {
        spark.sparkContext().setLogLevel("ERROR");

        Dataset<Row> kafkaStreamDS = spark
                .readStream()
                .format("kafka")
                .option("kafka.bootstrap.servers", bootstrapServers)
                .option("startingOffsets", "earliest")
                .option("subscribe", topic)
                .load();

        Dataset<RSSItemDTO> rssItemDS = kafkaStreamDS
                .select(from_json(col("value").cast("string"), rssSchema).as("data"))
                .select("data.*")
                .as(Encoders.bean(RSSItemDTO.class));

        Dataset<String> urlDS = rssItemDS.map((MapFunction<RSSItemDTO, String>) data -> {
            int index = StringUtils.ordinalIndexOf(data.getUrl(), "/", 3);
            return data.getUrl().substring(0, index);
        }, Encoders.STRING());

//        Dataset<String> words = urlDS.flatMap((FlatMapFunction<String, String>) w -> {
//           return Arrays.asList(w.toLowerCase().split(" ")).iterator();
//        }, Encoders.STRING());
//                .filter((FilterFunction<String>) String::isEmpty)
//                .coalesce(1);

        Dataset<Row> scoring = urlDS.groupBy("value")
                .count()
                .orderBy(col("count").desc());

        StreamingQuery console = scoring
                .writeStream()
                .format("console")
//                .option("checkpointLocation", "~/Desktop/checkpoint")
                .outputMode(OutputMode.Complete())
//                .trigger(Trigger.ProcessingTime(3000))
                .start();

        try {
            console.awaitTermination();
        } catch (StreamingQueryException e) {
            e.printStackTrace();
        }
    }
}
