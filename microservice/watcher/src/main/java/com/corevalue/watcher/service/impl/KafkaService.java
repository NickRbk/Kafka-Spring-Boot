package com.corevalue.watcher.service.impl;

import com.corevalue.watcher.model.TestModel;
import com.corevalue.watcher.service.IKafkaService;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class KafkaService implements IKafkaService {

    private final KafkaTemplate<String, TestModel> kafkaProducer;

    @Override
    public void send(String topic, String data) {
        kafkaProducer.send(topic, "", new TestModel(data));
    }
}
