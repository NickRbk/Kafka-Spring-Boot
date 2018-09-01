package com.corevalue.watcher.service.impl;

import com.corevalue.watcher.domain.entity.RSSCache;
import com.corevalue.watcher.domain.entity.Resource;
import com.corevalue.watcher.domain.repository.RSSCacheRepository;
import com.corevalue.watcher.domain.repository.ResourceRepository;
import com.corevalue.watcher.event.NewRSSEvent;
import com.corevalue.watcher.service.*;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class RSSService implements IRSSService {

    private final IKafkaService kafkaService;
    private final ApplicationEventPublisher publisher;
    private final ResourceRepository resourceRepository;
    private final IResourceService resourceService;
    private final RSSCacheRepository rssCacheRepository;
    private final IRedisCache redisCache;

    @Override
    public void fetchAndSendRSSAsync(String topic) {
        List<Resource> resources = resourceRepository.findValidResources();
        resources.forEach(resource -> publisher.publishEvent(new NewRSSEvent(this, topic, resource.getUrl())));
    }

    @Override
    public void fetchAndSendRSS(String topic) {
        List<Resource> resources = resourceRepository.findValidResources();
        resources.forEach(resource -> this.sendRSSItemsToKafka(topic, resource.getUrl()));
    }

    @Override
    public void sendRSSItemsToKafka(String topic, String resourceUrl) {
        try (CloseableHttpClient httpClient = HttpClients.custom()
                .setRedirectStrategy(new LaxRedirectStrategy())
                .build()) {

            HttpUriRequest request = new HttpGet(resourceUrl);
            tryToParseAndSendRSS(httpClient, request, topic, resourceUrl);
        } catch (IOException e) {
            this.processInvalidResource(resourceUrl, RSSErrorCode.ACCESS_ERROR, e.getMessage());
            log.error(e.getMessage());
        }
    }

    private void tryToParseAndSendRSS(CloseableHttpClient httpClient, HttpUriRequest request,
                                      String topic, String resourceUrl) {
        try (CloseableHttpResponse response = httpClient.execute(request);
             InputStream stream = response.getEntity().getContent()) {

            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(stream));
            List<SyndEntry> rssItems = feed.getEntries();
            sendRSSAndCacheLastItem(rssItems, topic, resourceUrl);
        } catch (FeedException e) {
            this.processInvalidResource(resourceUrl, RSSErrorCode.PARSE_ERROR, e.getMessage());
            log.error(e.getMessage());
        } catch (Exception e) {
            log.error("Something went wrong ================> " + e.getMessage());
        }
    }

    private void sendRSSAndCacheLastItem(List<SyndEntry> rssItems, String topic, String resourceUrl) {
        List<SyndEntry> rssNewItems = this.getNewRSSItems(rssItems, resourceUrl);
        redisCache.save(rssItems, resourceUrl);
        rssNewItems.forEach(item -> kafkaService.send(topic, item));
    }

    private void processInvalidResource(String resourceUrl, RSSErrorCode errorCode, String errorMsg) {
        resourceService.saveInvalidResource(resourceUrl, errorCode, errorMsg);
        resourceService.invalidateResource(resourceUrl);
        log.error(errorMsg);
    }

    private List<SyndEntry> getNewRSSItems(List<SyndEntry> rssItems, String resourceUrl) {
        Optional<RSSCache> rssCacheOptional = rssCacheRepository.findById(resourceUrl);

        if (rssCacheOptional.isPresent()) {
            List<SyndEntry> newItems = new ArrayList<>();
            RSSCache rssCache = rssCacheOptional.get();
            for (SyndEntry rssItem : rssItems) {
                if (rssItem.getLink().equals( rssCache.getLastResource() )) {
                    break;
                }
                newItems.add(rssItem);
            }
            return newItems;
        }
        return rssItems;
    }
}
