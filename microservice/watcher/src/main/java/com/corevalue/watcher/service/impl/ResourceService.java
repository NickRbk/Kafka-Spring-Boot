package com.corevalue.watcher.service.impl;

import com.corevalue.watcher.domain.entity.InvalidResource;
import com.corevalue.watcher.domain.repository.InvalidResourceRepository;
import com.corevalue.watcher.domain.repository.ResourceRepository;
import com.corevalue.watcher.service.IResourceService;
import com.corevalue.watcher.service.RSSErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class ResourceService implements IResourceService {

    private final ResourceRepository resourceRepository;
    private final InvalidResourceRepository invalidResourceRepository;

    @Override
    public void saveInvalidResource(String resourceUrl, RSSErrorCode errorCode, String errorMsg) {
        this.invalidResourceRepository.save(
                InvalidResource.builder()
                        .url(resourceUrl)
                        .errorCode(errorCode.getCode())
                        .errorMsg(errorMsg)
                        .build()
        );
    }

    @Override
    @Transactional
    public void invalidateResource(String resourceUrl) {
        this.resourceRepository.invalidateResource(resourceUrl);
    }
}
