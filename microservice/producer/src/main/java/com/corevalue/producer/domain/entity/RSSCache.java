package com.corevalue.producer.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.redis.core.RedisHash;

@ToString
@Getter
@Setter
@AllArgsConstructor
@RedisHash("RSSCache")
public class RSSCache {
    private String id;
    private String lastResource;
}
