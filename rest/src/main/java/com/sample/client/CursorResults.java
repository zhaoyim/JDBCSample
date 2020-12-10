package com.sample.client;

import static java.util.Objects.requireNonNull;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CursorResults {

    private Iterable<List<Object>> data;
    private List<DacpCloumn> schema;
    private int pageNum;
    private int pageSize;
    private int total;

    @JsonCreator
    public CursorResults(@JsonProperty("data") List<List<Object>> data, @JsonProperty("schema") List<DacpCloumn> schema,
            @JsonProperty("pageNum") int pageNum, @JsonProperty("pageSize") int pageSize,
            @JsonProperty("total") int total) {
        this.data = data;
        this.schema = schema;
        this.pageNum = requireNonNull(pageNum, "pageNum is null.");
        this.pageSize = requireNonNull(pageSize, "pageSize is null.");
        this.total = requireNonNull(total, "total is null.");
    }

    @JsonProperty
    public Iterable<List<Object>> getData() {
        return data;
    }

    @JsonProperty
    public List<DacpCloumn> getSchema() {
        return schema;
    }

    @JsonProperty
    public int getPageNum() {
        return pageNum;
    }

    @JsonProperty
    public int getPageSize() {
        return pageSize;
    }

    @JsonProperty
    public int getTotal() {
        return total;
    }
}
