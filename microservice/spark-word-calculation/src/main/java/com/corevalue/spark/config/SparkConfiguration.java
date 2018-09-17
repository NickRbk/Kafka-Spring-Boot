package com.corevalue.spark.config;

import org.apache.spark.SparkConf;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SparkConfiguration {
    @Bean
    public SparkConf conf() {
        return new SparkConf()
                .setAppName("WordCounter")
                .setMaster("local[*]")
//                .set("spark.cassandra.connection.host", cassandraHost);
                .set("spark.executor.memory", "1g");
    }

    @Bean
    public JavaStreamingContext ssc() {
        return new JavaStreamingContext(conf(), Durations.seconds(5));
    }

    @Bean
    public SparkSession spark() {
        return SparkSession.builder()
                .config(conf())
                .getOrCreate();
    }
}
