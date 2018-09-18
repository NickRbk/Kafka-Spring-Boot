package com.corevalue.listener.deserializer;

import com.corevalue.listener.model.DomainCounter;
import org.springframework.kafka.support.serializer.JsonDeserializer;

public class DomainCounterDeserializer extends JsonDeserializer<DomainCounter> {
    public DomainCounterDeserializer() {
        super(DomainCounter.class);
    }
}
