package com.corevalue.writer.deserializer;

import com.corevalue.writer.model.RSSItemModel;
import org.springframework.kafka.support.serializer.JsonDeserializer;

public class RSSItemDeserializer extends JsonDeserializer<RSSItemModel> {
    public RSSItemDeserializer() {
        super(RSSItemModel.class);
    }
}
