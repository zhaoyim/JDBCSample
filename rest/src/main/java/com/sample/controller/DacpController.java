package com.sample.controller;

import static io.airlift.json.JsonCodec.jsonCodec;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sample.client.CursorResults;
import com.sample.client.DacpCloumn;
import com.sample.client.DacpResults;
import com.sample.client.ExecutePlan;
import com.sample.client.ExecuteResults;
import com.sample.client.LoginResults;
import com.sample.client.QueryParams;

import io.airlift.json.JsonCodec;

@RestController
@RequestMapping("sql")
public class DacpController {

    private static final JsonCodec<QueryParams> QUERY_PARAMS_CODEC = jsonCodec(QueryParams.class);
    private static final String CURSOR_TYPE = "cursor"; 
    private static final String RESULT_TYPE = "result"; 

    @RequestMapping(value = "/login", method = { RequestMethod.GET, RequestMethod.POST })
    public LoginResults login(HttpServletRequest request) {
        try {
            StringBuilder builder = getInputJson(request);
            System.out.println("login = " + builder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new LoginResults(3600, "6789978676sdfsdf8978878978", "root", "000", "success");
    }

    @RequestMapping(value = "/execute", method = { RequestMethod.GET, RequestMethod.POST })
    public ExecuteResults execute(HttpServletRequest request) {
        try {
            StringBuilder builder = getInputJson(request);
            System.out.println("execute = " + builder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        // return new ExecuteResults("taskId0000001", RESULT_TYPE, true, "000", "success", null);
        List<ExecutePlan> executePlan = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            ExecutePlan subPlan = new ExecutePlan("stepId", "dependentStep", "comm", "CREATE TABLE IF NOT EXISTS", "cluster", "comment");
            executePlan.add(subPlan);
        }
        
        // 暂时屏蔽ExecutePlan
//        return new ExecuteResults("taskId0000001", CURSOR_TYPE, true, "000", "success", null, executePlan);
        return new ExecuteResults("taskId0000001", CURSOR_TYPE, true, "000", "success", null);
    }

    @RequestMapping(value = "/getResult", method = { RequestMethod.GET, RequestMethod.POST })
    public DacpResults iterateResults(HttpServletRequest request) {
//        // empty dataList
//        return getEmptyResults();
        
        try {
            StringBuilder builder = getInputJson(request);
            String queryParams = builder.toString();
            System.out.println("queryResults = " + queryParams);
            QueryParams params = QUERY_PARAMS_CODEC.fromJson(queryParams);
            if (CURSOR_TYPE.equals(params.getType())) {
                int total = 9;
                if (3 > params.getPageNum()) {
                    return getCursorResults(params, params.getPageSize(), total);
                }

                if (3 == params.getPageNum()) {
                    return getCursorResults(params, 2, total);
                }
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new DacpResults(RESULT_TYPE, "taskId0000001", "000", "success", null);
    }
    
    private StringBuilder getInputJson(HttpServletRequest request) throws IOException {
        try (InputStream in = request.getInputStream()) {
            StringBuilder builder = new StringBuilder();
            byte[] buffer = new byte[4096];
            for (int len; (len = in.read(buffer)) != -1;) {
                builder.append(new String(buffer, 0, len));
            }
            
            return builder;
        }
    }

    private DacpResults getCursorResults(QueryParams params, int rowSize, int total) {
        List<List<Object>> dataList = new ArrayList<>();
        for (int i = 0; i < rowSize; i++) {
            List<Object> row = new ArrayList<>();
            row.add("value1");
            row.add("value2");
            row.add("value3");
            dataList.add(row);
        }

        List<DacpCloumn> schemaList = new ArrayList<>();
        schemaList.add(new DacpCloumn("col1", "varchar"));
        schemaList.add(new DacpCloumn("col2", "varchar"));
        schemaList.add(new DacpCloumn("col3", "varchar"));
        CursorResults result = new CursorResults(dataList, 
                schemaList, 
                params.getPageNum(), 
                params.getPageSize(),
                total);
        
        // construct token expired.
        return new DacpResults("cursor", "taskId0000001", "700", "success", result);
    }
    
    private DacpResults getEmptyResults() {
        QueryParams params = new QueryParams("taskid", "cursor", "token", 1, 3);
        return getCursorResults(params, 0, 0);
    }

}
