package com.zmy.jar_test.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LogInfo {
    private String clazzName;
    private String methodName;
    private int lineNum;
}
