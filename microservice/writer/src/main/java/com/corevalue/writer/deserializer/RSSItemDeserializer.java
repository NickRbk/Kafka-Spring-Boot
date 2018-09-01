package com.corevalue.writer.deserializer;

import com.corevalue.writer.model.RSSItemDTO;
import org.springframework.kafka.support.serializer.JsonDeserializer;

public class RSSItemDeserializer extends JsonDeserializer<RSSItemDTO> {
    public RSSItemDeserializer() {
        super(RSSItemDTO.class);
    }
}
