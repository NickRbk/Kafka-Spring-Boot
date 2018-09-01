package com.corevalue.producer.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;


public class NewRSSEvent extends ApplicationEvent {

    @Getter
    private String topic, resourceUrl;

    public NewRSSEvent(Object source, String topic, String resourceUrl) {
        super(source);
        this.topic = topic;
        this.resourceUrl = resourceUrl;
    }
}
