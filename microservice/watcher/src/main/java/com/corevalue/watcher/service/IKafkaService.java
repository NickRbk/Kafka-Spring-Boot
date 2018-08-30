package com.corevalue.watcher.service;

public interface IKafkaService {
    void send(String topic, String data);
}
