package com.corevalue.listener.listener;

import com.corevalue.listener.model.RSSItemDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
public class Listener {
    private SimpMessagingTemplate socket;

    @KafkaListener(topics = "${kafka.topic}")
    public void testConsumption(@Payload RSSItemDTO RSSItemModel) {
        socket.convertAndSend("/news", RSSItemModel);
    }
}
