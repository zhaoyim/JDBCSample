package com.sample.client;

import static java.util.Objects.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DacpResults {

    private String type;
    private String taskId;
    private String code;
    private String msg;
    private CursorResults result;

    @JsonCreator
    public DacpResults(@JsonProperty("type") String type, @JsonProperty("taskId") String taskId,
            @JsonProperty("code") String code, @JsonProperty("msg") String msg, @JsonProperty("result") CursorResults result) {
        this.type = requireNonNull(type, "type is null.");
        this.taskId = requireNonNull(taskId, "taskId is null.");
        this.code = requireNonNull(code, "code is null.");
        this.msg = requireNonNull(msg, "msg is null.");
        this.result = result;
    }

    @JsonProperty
    public String getType() {
        return type;
    }

    @JsonProperty
    public String getTaskId() {
        return taskId;
    }

    @JsonProperty
    public String getCode() {
        return code;
    }

    @JsonProperty
    public String getMsg() {
        return msg;
    }

    @JsonProperty
    public CursorResults getResult() {
        return result;
    }
}
