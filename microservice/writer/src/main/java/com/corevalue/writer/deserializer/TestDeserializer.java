package com.corevalue.writer.deserializer;

import com.corevalue.writer.model.TestModel;
import org.springframework.kafka.support.serializer.JsonDeserializer;

public class TestDeserializer extends JsonDeserializer<TestModel> {
    public TestDeserializer() {
        super(TestModel.class);
    }
}
