package com.sample.client;

import java.net.URI;
import java.util.Date;
import java.util.List;

public class QueryResults {
    private String id;
    private URI nextUri;
    private List<String> columns;
    private Iterable<List<Object>> data;
    private String error;

    public QueryResults(String id, URI nextUri, List<String> columns, Iterable<List<Object>> data, String error) {
        this.id = id;
        this.nextUri = nextUri;
        this.columns = columns;
        this.data = data;
        this.error = error;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public URI getNextUri() {
        return nextUri;
    }

    public void setNextUri(URI nextUri) {
        this.nextUri = nextUri;
    }

    public List<String> getColumns() {
        return columns;
    }

    public void setColumns(List<String> columns) {
        this.columns = columns;
    }

    public Iterable<List<Object>> getData() {
        return data;
    }

    public void setData(Iterable<List<Object>> data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

}
