package com.corevalue.writer.writer;

import com.corevalue.writer.domain.RSSItem;
import com.corevalue.writer.domain.RSSItemRepository;
import com.corevalue.writer.model.RSSItemDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
public class Writer {
    private final RSSItemRepository rssItemRepository;

    @KafkaListener(topics = "${kafka.topic}")
    public void testConsumption(@Payload RSSItemDTO rssItem) {
        rssItemRepository.save(
                RSSItem.builder()
                        .url(rssItem.getUrl())
                        .title(rssItem.getTitle())
                        .type(rssItem.getType())
                        .description(rssItem.getDescription())
                        .publishedDate(rssItem.getPublishedDate())
                        .author(rssItem.getAuthor())
                        .build()
        );
    }
}
