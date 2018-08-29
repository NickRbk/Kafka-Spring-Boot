package com.corevalue.watcher.web;

import com.corevalue.watcher.model.TestModel;
import com.corevalue.watcher.service.ITestProducer;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class TestController {
//    private ITestProducer testProducer;
    private KafkaTemplate<String, TestModel> kafkaProducer;

    @GetMapping()
    public String testProduce(@RequestParam("topic") String topic,
                              @RequestParam("msg") String message) {

//        testProducer.send(topic, message);
        kafkaProducer.send(topic, "", new TestModel(message));
        return "Send status: OK";
    }
}
