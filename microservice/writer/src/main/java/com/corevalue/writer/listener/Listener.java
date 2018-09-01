package com.corevalue.writer.listener;

import com.corevalue.writer.domain.RSSItem;
import com.corevalue.writer.domain.RSSItemRepository;
import com.corevalue.writer.model.TestModel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
public class Listener {
    private final RSSItemRepository rssItemRepository;

    @KafkaListener(topics = "onlineStream")
    public void testConsumption(@Payload TestModel testModel) {
        rssItemRepository.save(RSSItem.builder().name(testModel.getName()).build());
    }
}
