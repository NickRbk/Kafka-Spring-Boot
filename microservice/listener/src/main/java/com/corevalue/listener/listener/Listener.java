package com.corevalue.listener.listener;

import com.corevalue.listener.model.RSSItemModel;
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

    @KafkaListener(topics = "onlineStream")
    public void testConsumption(@Payload RSSItemModel RSSItemModel) {
        log.error(RSSItemModel.toString());
        socket.convertAndSend("/news", RSSItemModel);
    }
}
