package com.sample.client;

import static java.util.Objects.requireNonNull;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class QueryParams {

    private String taskId;
    private String type;
    private String accessToken;
    private int pageNum;
    private int pageSize;

    @JsonCreator
    public QueryParams(@JsonProperty("taskId") String taskId, @JsonProperty("type") String type,
            @JsonProperty("accessToken") String accessToken, @JsonProperty("pageNum") int pageNum,
            @JsonProperty("pageSize") int pageSize) {
        this.taskId = requireNonNull(taskId, "taskId is null.");
        this.type = requireNonNull(type, "type is null.");
        this.accessToken = requireNonNull(accessToken, "accessToken is null.");
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    @JsonProperty
    public String getTaskId() {
        return taskId;
    }

    @JsonProperty
    public String getType() {
        return type;
    }

    @JsonProperty
    public String getAccessToken() {
        return accessToken;
    }

    @JsonProperty
    public int getPageNum() {
        return pageNum;
    }

    @JsonProperty
    public int getPageSize() {
        return pageSize;
    }
}
