package com.corevalue.producer.event;

import com.corevalue.producer.service.IRSSService;
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
        RSSService.sendRSSItemsToKafka(topic, resourceUrl);
    }
}
