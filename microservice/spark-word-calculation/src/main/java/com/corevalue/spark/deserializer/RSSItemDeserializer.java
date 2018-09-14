package com.corevalue.spark.deserializer;

import com.corevalue.spark.model.RSSItemDTO;
import org.springframework.kafka.support.serializer.JsonDeserializer;

public class RSSItemDeserializer extends JsonDeserializer<RSSItemDTO> {
    public RSSItemDeserializer() {
        super(RSSItemDTO.class);
    }
}
