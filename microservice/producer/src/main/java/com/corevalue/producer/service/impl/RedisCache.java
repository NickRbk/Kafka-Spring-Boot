package com.corevalue.producer.service.impl;

import com.corevalue.producer.domain.entity.RSSCache;
import com.corevalue.producer.domain.repository.RSSCacheRepository;
import com.corevalue.producer.service.IRedisCache;
import com.rometools.rome.feed.synd.SyndEntry;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class RedisCache implements IRedisCache {

    private final RSSCacheRepository rssCacheRepository;

    @Override
    public Optional<RSSCache> find(String resourceUrl) {
        return rssCacheRepository.findById(resourceUrl);
    }

    @Override
    public void save(List<SyndEntry> rssItems, String resourceUrl) {
        if (rssItems.size() > 0) {
            RSSCache rssCache = new RSSCache(resourceUrl, rssItems.get(0).getLink());
            rssCacheRepository.save(rssCache);
        }
    }
}
