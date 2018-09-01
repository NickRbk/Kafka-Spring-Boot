package com.corevalue.listener.model;

import lombok.*;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RSSItemModel {
    private String url;
    private String title;
    private String type;
    private String description;
    private long publishedDate;
    private String author;
}
