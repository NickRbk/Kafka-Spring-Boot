package com.corevalue.watcher.service;

import com.rometools.rome.feed.synd.SyndEntry;

public interface IKafkaService {
    void send(String topic, SyndEntry data);
}
