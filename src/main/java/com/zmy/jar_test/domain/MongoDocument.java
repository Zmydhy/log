package com.zmy.jar_test.domain;

import lombok.Data;

@Data
public class MongoDocument {
    private String title;
    private String source;
    private String level;
    private String logger;
    private String thread;
    private String message;
    private String mdc;
}
