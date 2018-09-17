package com.corevalue.spark.config;

import org.apache.spark.SparkConf;
import org.apache.spark.sql.SparkSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SparkConfiguration {
    @Bean
    public SparkConf conf() {
        return new SparkConf()
                .setAppName("WordCounter")
                .setMaster("local[*]")
                .set("spark.executor.memory", "1g");
    }

    @Bean
    public SparkSession spark() {
        return SparkSession.builder()
                .config(conf())
                .getOrCreate();
    }
}
