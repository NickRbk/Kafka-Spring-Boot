package com.corevalue.watcher.domain.repository;

import com.corevalue.watcher.domain.entity.RSSCache;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RSSCacheRepository extends JpaRepository<RSSCache, String> {
}
