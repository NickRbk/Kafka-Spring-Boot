package com.corevalue.producer.service;

public interface IRSSService {
    void fetchAndSendRSS(String topic);
    void fetchAndSendRSSAsync(String topic);
    void sendRSSItemsToKafka(String topic, String resourceUrl);
}
