package com.corevalue.watcher.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//@AllArgsConstructor
public class TestModel {
    private String name;

    public TestModel(String name) {
        this.name = name;
    }
}
