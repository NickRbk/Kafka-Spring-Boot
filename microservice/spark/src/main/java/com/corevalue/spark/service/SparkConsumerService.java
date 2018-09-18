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
import static org.apache.spark.sql.functions.*;

import static com.corevalue.spark.config.SchemaDefinitions.rssSchema;

@Service
@Slf4j
public class SparkConsumerService {
    @Value(value = "${kafka.input.topic}")
    private String inputTopic;

    @Value(value = "${kafka.output.topic}")
    private String outputTopic;

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
                .option("subscribe", inputTopic)
                .load();

        Dataset<RSSItemDTO> rssItemDS = kafkaStreamDS
                .select(from_json(col("value").cast("string"), rssSchema).as("data"))
                .select("data.*")
                .as(Encoders.bean(RSSItemDTO.class));

        Dataset<String> urlDS = rssItemDS.map((MapFunction<RSSItemDTO, String>) data -> {
            int index = StringUtils.ordinalIndexOf(data.getUrl(), "/", 3);
            return data.getUrl().substring(0, index);
        }, Encoders.STRING());

        Dataset<Row> scoring = urlDS.groupBy("value")
                .count()
                .orderBy(col("count").desc());

        StreamingQuery kafka = scoring.toJSON()
                .writeStream()
                .format("kafka")
                .outputMode(OutputMode.Complete())
                .option("kafka.bootstrap.servers", bootstrapServers)
                .option("topic", outputTopic)
                .option("checkpointLocation", "/app/checkpoint/scoring")
                .queryName("urlCounterKafkaStream")
                .start();

        StreamingQuery console = scoring
                .writeStream()
                .format("console")
                .outputMode(OutputMode.Complete())
                .start();

        try {
            kafka.awaitTermination();
            console.awaitTermination();
        } catch (StreamingQueryException e) {
            e.printStackTrace();
        }
    }
}
