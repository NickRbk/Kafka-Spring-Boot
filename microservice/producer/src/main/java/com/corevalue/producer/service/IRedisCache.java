package com.corevalue.producer.service;

import com.corevalue.producer.domain.entity.RSSCache;
import com.rometools.rome.feed.synd.SyndEntry;

import java.util.List;
import java.util.Optional;

public interface IRedisCache {
    Optional<RSSCache> find(String resourceUrl);
    void save(List<SyndEntry> rssItems, String resourceUrl);
}
