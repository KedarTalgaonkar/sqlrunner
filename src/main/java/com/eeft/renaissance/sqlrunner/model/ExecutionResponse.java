package com.eeft.renaissance.sqlrunner.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

public class ExecutionResponse {

    @Setter @Getter
    private int successCount;
    @Setter @Getter
    private int failureCount;
    @Setter @Getter
    private List<String> errorMessages;
    @Setter @Getter
    List<String> successMessages;

    private String executedBy;
    private String requestId;
    private LocalDateTime executedAt;

    public ExecutionResponse() {
    }

    public ExecutionResponse(int successCount, int failureCount, List<String> errorMessages, List<String> successMessages) {
        this.successCount = successCount;
        this.failureCount = failureCount;
        this.errorMessages = errorMessages;
        this.successMessages = successMessages;
    }
}

