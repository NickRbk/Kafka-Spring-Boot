package com.corevalue.spark.model;

import lombok.*;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RSSItemDTO {
    private String url;
    private String title;
    private String type;
    private String description;
    private Long publishedDate;
    private String author;
}
