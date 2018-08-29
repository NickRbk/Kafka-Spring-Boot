package com.corevalue.client.service;

import com.corevalue.client.model.TestModel;
import org.springframework.kafka.support.Acknowledgment;

public interface ITestConsumer {
    TestModel listen(TestModel testModel, Acknowledgment acknowledgment);
}
