package com.corevalue.watcher.service.impl;

import com.corevalue.watcher.domain.Resource;
import com.corevalue.watcher.domain.ResourceRepository;
import com.corevalue.watcher.event.NewRSSEvent;
import com.corevalue.watcher.service.IKafkaService;
import com.corevalue.watcher.service.IRSSService;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
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

    @Override
    public void fetchAndSendRSSAsync(String topic) {
        List<Resource> resources = resourceRepository.findAll();
        resources.forEach(resource -> publisher.publishEvent(new NewRSSEvent(this, topic, resource.getUrl())));
    }

    @Override
    public void fetchAndSendRSS(String topic) {
        List<Resource> resources = resourceRepository.findAll();
        resources.forEach(resource -> this.getAndParseRSS(topic, resource.getUrl()));
    }

    @Override
    public void getAndParseRSS(String topic, String resourceUrl) {
        try (CloseableHttpClient client = HttpClients.createMinimal()) {
            HttpUriRequest request = new HttpGet(resourceUrl);
            try (CloseableHttpResponse response = client.execute(request);
                 InputStream stream = response.getEntity().getContent()) {

                SyndFeedInput input = new SyndFeedInput();
                SyndFeed feed = input.build(new XmlReader(stream));
                List<SyndEntry> entries = feed.getEntries();

                entries.forEach(entry -> kafkaService.send(topic, entry.getLink()));
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
