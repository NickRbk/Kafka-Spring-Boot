package com.corevalue.listener.deserializer;

import com.corevalue.listener.model.RSSItemDTO;
import org.springframework.kafka.support.serializer.JsonDeserializer;

public class RSSItemDeserializer extends JsonDeserializer<RSSItemDTO> {
    public RSSItemDeserializer() {
        super(RSSItemDTO.class);
    }
}
