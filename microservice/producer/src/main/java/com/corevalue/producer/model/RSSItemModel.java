package com.corevalue.producer.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RSSItemModel {
    private String url;
    private String title;
    private String type;
    private String description;
    private long publishedDate;
    private String author;
}
