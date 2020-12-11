package com.sample.client;

import static java.util.Objects.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ExecutePlan {
    private final String stepId;
    private final String dependentStep;
    private final String comm;
    private final String para;
    private final String cluster;
    private final String comment;

    @JsonCreator
    public ExecutePlan(@JsonProperty("stepId") String stepId, @JsonProperty("dependentStep") String dependentStep,
            @JsonProperty("comm") String comm, @JsonProperty("para") String para,
            @JsonProperty("cluster") String cluster, @JsonProperty("comment") String comment) {
        this.stepId = requireNonNull(stepId, "stepId is null");
        this.dependentStep = dependentStep;
        this.comm = comm;
        this.para = requireNonNull(para, "para is null");
        this.cluster = requireNonNull(cluster, "cluster is null");
        this.comment = comment;
    }

    @JsonProperty
    public String getStepId() {
        return stepId;
    }

    @JsonProperty
    public String getDependentStep() {
        return dependentStep;
    }

    @JsonProperty
    public String getComm() {
        return comm;
    }

    @JsonProperty
    public String getPara() {
        return para;
    }

    @JsonProperty
    public String getCluster() {
        return cluster;
    }

    @JsonProperty
    public String getComment() {
        return comment;
    }
}
