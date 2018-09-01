package com.corevalue.producer.service;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RSSErrorCode {
    ACCESS_ERROR(1), PARSE_ERROR(2);

    private int code;
}
