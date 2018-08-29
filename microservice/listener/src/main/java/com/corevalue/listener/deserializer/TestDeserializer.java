package com.corevalue.listener.deserializer;

import com.corevalue.listener.model.TestModel;
import org.springframework.kafka.support.serializer.JsonDeserializer;

public class TestDeserializer extends JsonDeserializer<TestModel> {
    public TestDeserializer() {
        super(TestModel.class);
    }
}
