package com.corevalue.watcher.domain.repository;

import com.corevalue.watcher.domain.entity.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ResourceRepository extends JpaRepository<Resource, Long> {

    @Query(value = "SELECT * FROM resources WHERE is_valid=true", nativeQuery = true)
    List<Resource> findValidResources();

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE resources SET is_valid=false WHERE url=:url", nativeQuery = true)
    void invalidateResource(@Param("url") String resourceUrl);
}
