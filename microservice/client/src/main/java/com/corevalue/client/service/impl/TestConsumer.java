package com.corevalue.client.service.impl;

import com.corevalue.client.model.TestModel;
import com.corevalue.client.service.ITestConsumer;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Service
public class TestConsumer implements ITestConsumer {

    @Override
    @KafkaListener(topics = "onlineStream")
    public TestModel listen(TestModel testModel, Acknowledgment acknowledgment) {
        acknowledgment.acknowledge();
        return testModel;
    }
}
