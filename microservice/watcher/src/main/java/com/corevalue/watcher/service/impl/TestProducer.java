package com.corevalue.watcher.service.impl;

import com.corevalue.watcher.model.TestModel;
import com.corevalue.watcher.service.ITestProducer;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TestProducer implements ITestProducer {

    private KafkaTemplate<String, TestModel> kafkaProducer;

    @Override
    public void send(String topic, String data) {
        System.out.println("MY DATA HERE ===================================>" + data);
        kafkaProducer.send(topic, "", new TestModel(data));
    }
}
