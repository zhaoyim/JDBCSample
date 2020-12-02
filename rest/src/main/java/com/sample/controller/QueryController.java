package com.sample.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sample.client.QueryResults;

@RestController
@RequestMapping("v1/statement")
public class QueryController {

    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
    public QueryResults query(@RequestParam String statement, HttpServletRequest request) {
        if (statement.isEmpty()) {
            return new QueryResults(null, null, null, null, "parameter statement is required.");
        }

        return getResults(0, request);
    }

    @RequestMapping(value = "/{queryId}/{pageNo}", method = RequestMethod.GET)
    public QueryResults iterateResults(@PathVariable String queryId, @PathVariable int pageNo,
            HttpServletRequest request) {
        return getResults(pageNo, request);
    }

    private QueryResults getResults(int pageNo, HttpServletRequest request) {
        String id = "1234567890";
        List<String> columns = new ArrayList<>();
        columns.add("col1");
        columns.add("col2");
        columns.add("col3");

        List<List<Object>> datas = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            List<Object> list = new ArrayList<Object>();
            list.add(pageNo * i);
            list.add(pageNo * i);
            list.add(pageNo * i);
            datas.add(list);
        }

        URI nextUri = null;
        if (pageNo < 5) {
            String rootPath = String.format("%s://%s:%s%s", request.getScheme(), request.getServerName(),
                    request.getServerPort(), request.getContextPath());
            nextUri = URI.create(String.format("%s/v1/statement/%s/%d", rootPath, id, ++pageNo));
        }

        return new QueryResults(id, nextUri, columns, datas, null);
    }

}
