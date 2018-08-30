package com.corevalue.watcher.web;

import com.corevalue.watcher.service.IKafkaService;
import com.corevalue.watcher.service.IRSSService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class RSSController {
    private IRSSService RSSService;
    private final IKafkaService kafkaService;

    @GetMapping()
    public String testProducer(@RequestParam("topic") String topic,
                              @RequestParam("msg") String message) {

        kafkaService.send(topic, message);
        return "Send status: OK";
    }

    @GetMapping("/list")
    public void RSSProducer(@RequestParam("topic") String topic) {
        RSSService.fetchAndSendRSS(topic);
    }

    @GetMapping("/async")
    public void RSSProducerAsync(@RequestParam("topic") String topic) {
        RSSService.fetchAndSendRSSAsync(topic);
    }
}
