package com.corevalue.listener.deserializer;

import com.corevalue.listener.model.RSSItemModel;
import org.springframework.kafka.support.serializer.JsonDeserializer;

public class RSSItemDeserializer extends JsonDeserializer<RSSItemModel> {
    public RSSItemDeserializer() {
        super(RSSItemModel.class);
    }
}
