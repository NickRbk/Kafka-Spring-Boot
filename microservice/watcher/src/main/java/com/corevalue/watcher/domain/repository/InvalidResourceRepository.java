package com.corevalue.watcher.domain.repository;

import com.corevalue.watcher.domain.entity.InvalidResource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvalidResourceRepository extends JpaRepository<InvalidResource, Long> {
}
