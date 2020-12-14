package com.sample.client;

import static java.util.Objects.requireNonNull;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ExecuteResults {

    private String taskId;
    private String type;
    private boolean success;
    private String code;
    private String message;
    private String errDetail;
//    private final List<ExecutePlan> executePlan;

    @JsonCreator
    public ExecuteResults(@JsonProperty("taskId") String taskId, 
            @JsonProperty("type") String type, 
            @JsonProperty("success") boolean success,
            @JsonProperty("code") String code, 
            @JsonProperty("message") String message,
            @JsonProperty("errDetail") String errDetail) {
//            @JsonProperty("executePlan") List<ExecutePlan> executePlan) {
        this.taskId = requireNonNull(taskId, "taskId is null");
        this.type = requireNonNull(type, "type is null");
        this.success = requireNonNull(success, "success is null");
        this.code = requireNonNull(code, "code is null");
        this.message = message;
        this.errDetail = errDetail;
//        this.executePlan = executePlan;
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
    public boolean isSuccess() {
        return success;
    }

    @JsonProperty
    public String getCode() {
        return code;
    }

    @JsonProperty
    public String getMessage() {
        return message;
    }

    @JsonProperty
    public String getErrDetail() {
        return errDetail;
    }

//    @JsonProperty
//    public List<ExecutePlan> getExecutePlan() {
//        return executePlan;
//    }
}
