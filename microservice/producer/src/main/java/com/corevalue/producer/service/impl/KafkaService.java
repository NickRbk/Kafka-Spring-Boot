package com.corevalue.producer.service.impl;

import com.corevalue.producer.model.TestModel;
import com.corevalue.producer.service.IKafkaService;
import com.rometools.rome.feed.synd.SyndEntry;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class KafkaService implements IKafkaService {

    private final KafkaTemplate<String, TestModel> kafkaProducer;

    @Override
    public void send(String topic, SyndEntry data) {
        kafkaProducer.send(topic, "", new TestModel(data.getLink()));
    }
}
