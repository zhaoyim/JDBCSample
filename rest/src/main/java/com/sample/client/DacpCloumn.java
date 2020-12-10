package com.sample.client;

import static java.util.Objects.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DacpCloumn {
    private String column;
    private String type;

    @JsonCreator
    public DacpCloumn(@JsonProperty("column") String column, @JsonProperty("type") String type) {
        this.column = requireNonNull(column, "column is null.");
        this.type = requireNonNull(type, "type is null.");
    }

    @JsonProperty
    public String getColumn() {
        return column;
    }

    @JsonProperty
    public String getType() {
        return type;
    }
}
