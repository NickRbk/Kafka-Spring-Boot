package com.corevalue.watcher.service;

public interface IRSSService {
    void fetchAndSendRSS(String topic);
    void fetchAndSendRSSAsync(String topic);
    void getAndParseRSS(String topic, String resourceUrl);
}
