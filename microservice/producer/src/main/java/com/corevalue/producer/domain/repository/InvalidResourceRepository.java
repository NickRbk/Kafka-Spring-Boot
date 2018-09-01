package com.corevalue.producer.domain.repository;

import com.corevalue.producer.domain.entity.InvalidResource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvalidResourceRepository extends JpaRepository<InvalidResource, Long> {
}
