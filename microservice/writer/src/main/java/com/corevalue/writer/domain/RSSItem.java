package com.corevalue.writer.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "rss_items")
public class RSSItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String url;

    private String title;

    private String type;

    private String description;

    @Column(name = "published_date")
    private long publishedDate;

    private String author;
}
