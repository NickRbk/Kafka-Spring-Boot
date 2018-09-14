package com.corevalue.spark.config;

import org.apache.spark.SparkConf;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SparkConfiguration {

    @Bean
    public SparkConf sparkConf() {
        return new SparkConf()
                .setAppName("WordCounter")
                .setMaster("local[2]")
                .set("spark.executor.memory", "2g")
                .set("spark.driver.memory", "2g");
    }
}
