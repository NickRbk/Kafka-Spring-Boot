package com.corevalue.producer.service;

public interface IResourceService {
    void saveInvalidResource(String resourceUrl, RSSErrorCode errorCode, String errorMsg);
    void invalidateResource(String resourceUrl);
}
