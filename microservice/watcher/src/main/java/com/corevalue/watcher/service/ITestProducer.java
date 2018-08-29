package com.corevalue.watcher.service;

public interface ITestProducer {
    void send(String topic, String data);
}
