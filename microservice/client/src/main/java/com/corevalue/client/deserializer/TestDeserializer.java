package com.corevalue.client.deserializer;

import com.corevalue.client.model.TestModel;
import org.springframework.kafka.support.serializer.JsonDeserializer;

public class TestDeserializer extends JsonDeserializer<TestModel> {
    public TestDeserializer() {
        super(TestModel.class);
    }
}
