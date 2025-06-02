package com.eeft.ren.sqlrunner.model;

import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

public class ExecutionResponse {

    @Setter @Getter
    private int successCount;
    @Setter @Getter
    private int failureCount;
    @Setter @Getter
    private List<String> errorMessages;
    @Setter @Getter
    Map<String, Object> successMessages;

    public ExecutionResponse() {
    }

    public ExecutionResponse(int successCount, int failureCount, List<String> errorMessages, Map<String, Object> successMessages) {
        this.successCount = successCount;
        this.failureCount = failureCount;
        this.errorMessages = errorMessages;
        this.successMessages = successMessages;
    }
}

