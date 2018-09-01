package com.corevalue.producer.domain.repository;

import com.corevalue.producer.domain.entity.RSSCache;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RSSCacheRepository extends JpaRepository<RSSCache, String> {
}
