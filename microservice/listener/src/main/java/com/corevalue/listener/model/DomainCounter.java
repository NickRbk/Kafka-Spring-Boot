package com.corevalue.listener.model;

import lombok.*;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DomainCounter {
    private String value;
    private Integer count;
}
