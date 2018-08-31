package com.corevalue.watcher.service.impl;

import com.corevalue.watcher.domain.entity.Resource;
import com.corevalue.watcher.domain.repository.ResourceRepository;
import com.corevalue.watcher.event.NewRSSEvent;
import com.corevalue.watcher.service.IKafkaService;
import com.corevalue.watcher.service.IRSSService;
import com.corevalue.watcher.service.IResourceService;
import com.corevalue.watcher.service.RSSErrorCode;
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
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class RSSService implements IRSSService {

    private final IKafkaService kafkaService;
    private final ApplicationEventPublisher publisher;
    private final ResourceRepository resourceRepository;
    private final IResourceService resourceService;

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
        try (CloseableHttpClient client = HttpClients.createMinimal()) {

            HttpUriRequest request = new HttpGet(resourceUrl);
            tryToParseAndSendRSS(client, request, topic, resourceUrl);
        } catch (IOException e) {
            this.processInvalidResource(resourceUrl, RSSErrorCode.ACCESS_ERROR, e.getMessage());
            log.error(e.getMessage());
        }
    }

    private void tryToParseAndSendRSS(CloseableHttpClient client, HttpUriRequest request,
                                      String topic, String resourceUrl) {
        try (CloseableHttpResponse response = client.execute(request);
             InputStream stream = response.getEntity().getContent()) {

            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(stream));
            List<SyndEntry> rssItems = feed.getEntries();
            rssItems.forEach(item -> kafkaService.send(topic, item.getLink()));
        } catch (FeedException e) {
            this.processInvalidResource(resourceUrl, RSSErrorCode.PARSE_ERROR, e.getMessage());
            log.error(e.getMessage());
        } catch (Exception e) {
            log.error("Something went wrong ================> " + e.getMessage());
        }
    }

    private void processInvalidResource(String resourceUrl, RSSErrorCode errorCode, String errorMsg) {
        resourceService.saveInvalidResource(resourceUrl, errorCode, errorMsg);
        resourceService.invalidateResource(resourceUrl);
    }
}
