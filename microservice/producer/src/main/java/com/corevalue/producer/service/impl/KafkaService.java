package com.corevalue.producer.service.impl;

import com.corevalue.producer.model.RSSItemDTO;
import com.corevalue.producer.service.IKafkaService;
import com.rometools.rome.feed.synd.SyndCategory;
import com.rometools.rome.feed.synd.SyndEntry;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class KafkaService implements IKafkaService {

    private final KafkaTemplate<String, RSSItemDTO> kafkaProducer;

    @Override
    public void send(String topic, SyndEntry data) {
        String type = data.getCategories().stream()
                .map(SyndCategory::getName)
                .collect(Collectors.joining(", "));


        kafkaProducer.send(topic, "",
               RSSItemDTO.builder()
                       .url(data.getLink())
                       .title(data.getTitle())
                       .type(type)
                       .description(data.getDescription().getValue())
                       .publishedDate(data.getPublishedDate().getTime())
                       .author(data.getAuthor())
                       .build()
        );
    }
}
