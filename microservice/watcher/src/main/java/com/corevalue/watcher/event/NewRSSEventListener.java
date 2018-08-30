package com.corevalue.watcher.event;

import com.corevalue.watcher.service.IRSSService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NewRSSEventListener {

    private final IRSSService RSSService;

    @Async
    @EventListener
    public void onNewRSSItem(NewRSSEvent event) {
        String topic = event.getTopic();
        String resourceUrl = event.getResourceUrl();
        RSSService.getAndParseRSS(topic, resourceUrl);
    }
}
