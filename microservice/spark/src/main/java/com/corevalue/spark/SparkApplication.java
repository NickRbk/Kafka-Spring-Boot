package com.corevalue.spark;

import com.corevalue.spark.service.SparkConsumerService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class SparkApplication implements CommandLineRunner {

    private final SparkConsumerService sparkConsumerService;

    public static void main(String[] args) {
        SpringApplication.run(SparkApplication.class, args);
    }

    @Override
    public void run(String... args) {
        sparkConsumerService.run();
    }
}
