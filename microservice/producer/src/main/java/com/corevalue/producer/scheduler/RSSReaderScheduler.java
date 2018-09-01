package com.corevalue.producer.scheduler;

import com.corevalue.producer.service.IRSSService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class RSSReaderScheduler {
    @Value("${kafka.topic}")
    private String topic;

    private final IRSSService rssService;

    public RSSReaderScheduler(IRSSService rssService) {
        this.rssService = rssService;
    }

    @Scheduled(cron = "${scheduler.cron.updater.rss}")
    public void readRSS() {
        rssService.fetchAndSendRSSAsync(topic);
    }
}
