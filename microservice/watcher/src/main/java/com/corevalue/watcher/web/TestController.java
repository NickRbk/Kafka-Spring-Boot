package com.corevalue.watcher.web;

import com.corevalue.watcher.service.ITestProducer;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class TestController {
    private ITestProducer testProducer;

    @GetMapping()
    public String testProduce(@RequestParam("topic") String topic,
                              @RequestParam("msg") String message) {
        testProducer.send(topic, message);
        return "Send status: OK";
    }
}
