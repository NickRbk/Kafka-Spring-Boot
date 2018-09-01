package com.corevalue.writer.model;

import lombok.*;

@ToString
@Getter
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
