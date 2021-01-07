package com.sample.client;

import static java.util.Objects.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DacpCloumn {
    private String name;
    private String type;

    @JsonCreator
    public DacpCloumn(@JsonProperty("name") String column, @JsonProperty("type") String type) {
        this.name = requireNonNull(column, "name is null.");
        this.type = requireNonNull(type, "type is null.");
    }

    @JsonProperty
    public String getName() {
        return name;
    }

    @JsonProperty
    public String getType() {
        return type;
    }
}
