package com.sample.controller;

import static io.airlift.json.JsonCodec.jsonCodec;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
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
    private static final JsonCodec<DacpResults> DACP_RESULT_CODEC = jsonCodec(DacpResults.class);
    private static final String CURSOR_TYPE = "cursor"; 
    private static final String RESULT_TYPE = "result"; 
    private static final String OLK_TYPE = "olk"; 
    
    private static int counter = 0;

    @RequestMapping(value = "/login", method = { RequestMethod.GET, RequestMethod.POST })
    public LoginResults login(HttpServletRequest request) {
        try {
            StringBuilder builder = getInputJson(request);
            System.out.println("login = " + builder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new LoginResults(3600, "6789978676sdfsdf8978878978", "root", "000", "success", "http://10.1.236.69:8285");
    }

    @RequestMapping(value = "/execute", method = { RequestMethod.GET, RequestMethod.POST })
    public ExecuteResults execute(HttpServletRequest request) {
        String olkSQL = "select a.*,b.* from mysql197.prestotest.course a left join mysql197.prestotest.score b on a.cno=b.cno";
//        String olkSQL = "select * from hive3.prestotest.student2";
        try {
            StringBuilder builder = getInputJson(request);
            String params = builder.toString();
            System.out.println("execute = " + builder.toString());
            
            if(params.contains("show catalogs")) {
                return new ExecuteResults("taskId_catalog_001", CURSOR_TYPE, true, "000", "success", null, olkSQL);
            } else if (params.contains("show databases")) {
                return new ExecuteResults("taskId_schema_001", CURSOR_TYPE, true, "000", "success", null, olkSQL);
            } else if (params.contains("show tables")) {
                return new ExecuteResults("taskId_table_001", CURSOR_TYPE, true, "000", "success", null, olkSQL);
            } else if (params.contains("get tabletypes")) {
                return new ExecuteResults("taskId_tbl_type_001", CURSOR_TYPE, true, "000", "success", null, olkSQL);
            } else if (params.contains("get primarykeys")) {
                return new ExecuteResults("taskId_primary_keys_001", CURSOR_TYPE, true, "000", "success", null, olkSQL);
            } else if (params.contains("get importedkeys")) {
                return new ExecuteResults("taskId_imported_keys_001", CURSOR_TYPE, true, "000", "success", null, olkSQL);
            } else if (params.contains("get indexinfo")) {
                return new ExecuteResults("taskId_index_info_001", CURSOR_TYPE, true, "000", "success", null, olkSQL);
//            } else if (params.contains("system.jdbc.columns")) {
            } else if (params.contains("desc ")) {
                return new ExecuteResults("taskId_columns_001", CURSOR_TYPE, true, "000", "success", null, olkSQL);
            } else if (params.contains("dual1")) {
                return new ExecuteResults("taskId_dual1_001", CURSOR_TYPE, true, "000", "success", null, olkSQL);
            } else if (params.contains("get udts")) {
                return new ExecuteResults("taskId_udts_001", CURSOR_TYPE, true, "000", "success", null, olkSQL);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

//        return new ExecuteResults("taskId0000001", RESULT_TYPE, true, "000", "success", null);
        
//        List<ExecutePlan> executePlan = new ArrayList<>();
//        for (int i = 0; i < 3; i++) {
//            ExecutePlan subPlan = new ExecutePlan("stepId", "dependentStep", "comm", "CREATE TABLE IF NOT EXISTS", "cluster", "comment");
//            executePlan.add(subPlan);
//        }
//        
        // 暂时屏蔽ExecutePlan
//        return new ExecuteResults("taskId0000001", CURSOR_TYPE, true, "000", "success", null, executePlan);
        
        
        
        return new ExecuteResults("taskId0000001", OLK_TYPE, true, "000", "success", null, olkSQL);
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
            
            String taskId = params.getTaskId();
            
            if(taskId.equals("taskId_catalog_001")) {
                return this.getCatalogs(params);
            } else if (taskId.equals("taskId_schema_001")) {
                return this.getSchemas(params);
            } else if (taskId.equals("taskId_tbl_type_001")) {
                return this.getTableTypes(params);
            } else if (taskId.equals("taskId_table_001")) {
                return this.getTables(params);
            } else if (taskId.equals("taskId_primary_keys_001")) {
                return this.getPrimaryKeys(params);
            } else if (taskId.equals("taskId_imported_keys_001")) {
                return this.getImportedkeys(params);
            } else if (taskId.equals("taskId_index_info_001")) {
                return this.getIndexInfo(params);
            } else if (taskId.equals("taskId_columns_001")) {
                return this.getColumns(params);
            } else if (taskId.equals("taskId_dual1_001")) {
                return this.getPrimaryKeys(params);
            } else if (taskId.equals("taskId_udts_001")) {
                return this.getUDTs(params);
            }
            
            if (CURSOR_TYPE.equals(params.getType())) {
                
                if (counter < 3 && 1 == params.getPageNum()) {
                    counter += 1;
//                    return new DacpResults("cursor", "taskId0000001", "001", "running", null);
                    return getEmptyResults();
                }
                
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
            row.add("db" + i);
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
        return new DacpResults("cursor", "taskId0000001", "000", "success", result);
    }
    
    private DacpResults getEmptyResults() {
//        QueryParams params = new QueryParams("taskid", "cursor", "token", 1, 3);
//        return getCursorResults(params, 0, 0);
        
        List<DacpCloumn> schemaList = new ArrayList<>();
        schemaList.add(new DacpCloumn("col1", "varchar"));
        schemaList.add(new DacpCloumn("col2", "varchar"));
        schemaList.add(new DacpCloumn("col3", "varchar"));
//        schemaList.add(new DacpCloumn("col41", "varchar"));
//        schemaList.add(new DacpCloumn("col51", "varchar"));
        CursorResults result = new CursorResults(Collections.emptyList(), 
                schemaList, 
                0, 
                0,
                0);
        
        // construct token expired.
        return new DacpResults("cursor", "taskId0000001", "001", "running", result);
    }
    
    private DacpResults getCatalogs(QueryParams params) {
        List<List<Object>> dataList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            List<Object> row = new ArrayList<>();
            row.add("dacp_db" + i);
            dataList.add(row);
        }
        
        List<DacpCloumn> schemaList = new ArrayList<>();
        schemaList.add(new DacpCloumn("TABLE_CAT", "varchar"));
        CursorResults result = new CursorResults(dataList, 
                schemaList, 
                params.getPageNum(), 
                params.getPageSize(),
                3);
        
        DacpResults instance = new DacpResults("cursor", "taskId0000001", "000", "success", result);
//        System.out.println("getCatalogs: \n" + DACP_RESULT_CODEC.toJson(instance));
        return instance;
    }
    
    private DacpResults getSchemas(QueryParams params) {
        List<List<Object>> dataList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            List<Object> row = new ArrayList<>();
//            row.add("dacp_db" + i);
            row.add("dacp_schem" + i);
            dataList.add(row);
        }
        
        List<DacpCloumn> schemaList = new ArrayList<>();
//        schemaList.add(new DacpCloumn("TABLE_CATALOG", "varchar"));
        schemaList.add(new DacpCloumn("TABLE_SCHEM", "varchar"));
        CursorResults result = new CursorResults(dataList, 
                schemaList, 
                params.getPageNum(), 
                params.getPageSize(),
                3);
        
        DacpResults instance = new DacpResults("cursor", "taskId0000001", "000", "success", result);
//        System.out.println("getSchemas: \n" + DACP_RESULT_CODEC.toJson(instance));
        return instance;
    }
    
    private DacpResults getTableTypes(QueryParams params) {
        List<List<Object>> dataList = new ArrayList<>();
        List<Object> tmp = new ArrayList<>();
        tmp.add("LOCAL TEMPORARY");
        dataList.add(tmp);
        
        List<Object> stbl = new ArrayList<>();
        stbl.add("SYSTEM TABLE");
        dataList.add(stbl);
        
        List<Object> sview = new ArrayList<>();
        sview.add("SYSTEM VIEW");
        dataList.add(sview);
        
        List<Object> tbl = new ArrayList<>();
        tbl.add("TABLE");
        dataList.add(tbl);
        
        List<Object> view = new ArrayList<>();
        view.add("VIEW");
        dataList.add(view);
        
        List<Object> row = new ArrayList<>();
        row.add("UNKNOWN");
        dataList.add(row);
        
        List<DacpCloumn> schemaList = new ArrayList<>();
        schemaList.add(new DacpCloumn("TABLE_TYPE", "varchar"));
        CursorResults result = new CursorResults(dataList, 
                schemaList, 
                params.getPageNum(), 
                params.getPageSize(),
                3);
        
        DacpResults instance = new DacpResults("cursor", "taskId0000001", "000", "success", result);
//        System.out.println("getTableTypes: \n" + DACP_RESULT_CODEC.toJson(instance));
        return instance;
    }
    
    private DacpResults getTables(QueryParams params) {
        List<List<Object>> dataList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            List<Object> row = new ArrayList<>();
            row.add("dacp_db" + i);
            row.add("dacp_schem" + i);
            row.add("dacp_tbl" + i);
            row.add("TABLE");
            row.add("remarks");
            row.add("type_cat");
            row.add("type_schem");
            row.add("type_name");
            row.add("self_ref_col_name");
            row.add("ref_generation");
            dataList.add(row);
        }
        
        List<DacpCloumn> schemaList = new ArrayList<>();
        schemaList.add(new DacpCloumn("TABLE_CAT", "varchar"));
        schemaList.add(new DacpCloumn("TABLE_SCHEM", "varchar"));
        schemaList.add(new DacpCloumn("TABLE_NAME", "varchar"));
        schemaList.add(new DacpCloumn("TABLE_TYPE", "varchar"));
        schemaList.add(new DacpCloumn("REMARKS", "varchar"));
        schemaList.add(new DacpCloumn("TYPE_CAT", "varchar"));
        schemaList.add(new DacpCloumn("TYPE_SCHEM", "varchar"));
        schemaList.add(new DacpCloumn("TYPE_NAME", "varchar"));
        schemaList.add(new DacpCloumn("SELF_REFERENCING_COL_NAME", "varchar"));
        schemaList.add(new DacpCloumn("REF_GENERATION", "varchar"));
        
        CursorResults result = new CursorResults(dataList, 
                schemaList, 
                params.getPageNum(), 
                params.getPageSize(),
                1);
        
        DacpResults instance = new DacpResults("cursor", "taskId0000001", "000", "success", result);
//        System.out.println("getTables: \n" + DACP_RESULT_CODEC.toJson(instance));
        return instance;
    }
    
    private DacpResults getPrimaryKeys(QueryParams params) {
        List<List<Object>> dataList = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            List<Object> row = new ArrayList<>();
            row.add("dacp_db" + 0);
            row.add("dacp_schem" + 0);
            row.add("dacp_tbl" + 0);
            row.add("tbl_col_name" + 0);
            row.add("tbl_key_seq");
            row.add("tbl_pk_name");
            dataList.add(row);
        }
        
        List<DacpCloumn> schemaList = new ArrayList<>();
        schemaList.add(new DacpCloumn("TABLE_CAT", "varchar"));
        schemaList.add(new DacpCloumn("TABLE_SCHEM", "varchar"));
        schemaList.add(new DacpCloumn("TABLE_NAME", "varchar"));
        schemaList.add(new DacpCloumn("COLUMN_NAME", "varchar"));
        schemaList.add(new DacpCloumn("KEY_SEQ", "varchar"));
        schemaList.add(new DacpCloumn("PK_NAME", "varchar"));
        
        CursorResults result = new CursorResults(dataList, 
                schemaList, 
                params.getPageNum(), 
                params.getPageSize(),
                1);
        
        DacpResults instance = new DacpResults("cursor", "taskId0000001", "000", "success", result);
//        System.out.println("getPrimaryKeys: \n" + DACP_RESULT_CODEC.toJson(instance));
        return instance;
    }
    
    private DacpResults getImportedkeys(QueryParams params) {
        List<DacpCloumn> schemaList = new ArrayList<>();
        schemaList.add(new DacpCloumn("PKTABLE_CAT", "varchar"));
        schemaList.add(new DacpCloumn("PKTABLE_SCHEM", "varchar"));
        schemaList.add(new DacpCloumn("PKTABLE_NAME", "varchar"));
        schemaList.add(new DacpCloumn("PKCOLUMN_NAME", "varchar"));
        schemaList.add(new DacpCloumn("FKTABLE_CAT", "varchar"));
        schemaList.add(new DacpCloumn("FKTABLE_SCHEM", "varchar"));
        schemaList.add(new DacpCloumn("FKTABLE_NAME", "varchar"));
        schemaList.add(new DacpCloumn("FKCOLUMN_NAME", "varchar"));
        schemaList.add(new DacpCloumn("KEY_SEQ", "varchar"));
        schemaList.add(new DacpCloumn("UPDATE_RULE", "varchar"));
        schemaList.add(new DacpCloumn("DELETE_RULE", "varchar"));
        schemaList.add(new DacpCloumn("FK_NAME", "varchar"));
        schemaList.add(new DacpCloumn("PK_NAME", "varchar"));
        schemaList.add(new DacpCloumn("DEFERRABILITY", "varchar"));
        
        List<List<Object>> dataList = new ArrayList<>();
        
        CursorResults result = new CursorResults(Collections.emptyList(), 
                schemaList, 
                params.getPageNum(), 
                params.getPageSize(),
                1);
        
        DacpResults instance = new DacpResults("cursor", "taskId0000001", "000", "success", result);
//        System.out.println("getImportedkeys: \n" + DACP_RESULT_CODEC.toJson(instance));
        return instance;
    }
    
    private DacpResults getIndexInfo(QueryParams params) {
        List<DacpCloumn> schemaList = new ArrayList<>();
        schemaList.add(new DacpCloumn("TABLE_CAT", "varchar"));
        schemaList.add(new DacpCloumn("TABLE_SCHEM", "varchar"));
        schemaList.add(new DacpCloumn("TABLE_NAME", "varchar"));
        schemaList.add(new DacpCloumn("NON_UNIQUE", "varchar"));
        schemaList.add(new DacpCloumn("INDEX_QUALIFIER", "varchar"));
        schemaList.add(new DacpCloumn("INDEX_NAME", "varchar"));
        schemaList.add(new DacpCloumn("TYPE", "varchar"));
        schemaList.add(new DacpCloumn("ORDINAL_POSITION", "varchar"));
        schemaList.add(new DacpCloumn("COLUMN_NAME", "varchar"));
        schemaList.add(new DacpCloumn("ASC_OR_DESC", "varchar"));
        schemaList.add(new DacpCloumn("CARDINALITY", "varchar"));
        schemaList.add(new DacpCloumn("PAGES", "varchar"));
        schemaList.add(new DacpCloumn("FILTER_CONDITION", "varchar"));
        
        List<List<Object>> dataList = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            List<Object> row = new ArrayList<>();
            row.add("dacp_db" + 0);
            row.add("dacp_schem" + 0);
            row.add("dacp_tbl" + 0);
            row.add("NON_UNIQUE");
            row.add("INDEX_QUALIFIER");
            row.add("idx_name");
            row.add("TYPE");
            row.add("ORDINAL_POSITION");
            row.add("tbl_col_name" + i);
            row.add("ASC_OR_DESC");
            row.add("CARDINALITY");
            row.add("PAGES");
            row.add("FILTER_CONDITION");
            dataList.add(row);
        }
        
        CursorResults result = new CursorResults(dataList, 
                schemaList, 
                params.getPageNum(), 
                params.getPageSize(),
                1);
        
        DacpResults instance = new DacpResults("cursor", "taskId0000001", "000", "success", result);
//        System.out.println("getIndexInfo: \n" + DACP_RESULT_CODEC.toJson(instance));
        return instance;
    }
    
    private DacpResults getColumns(QueryParams params) {
        List<DacpCloumn> schemaList = new ArrayList<>();
        schemaList.add(new DacpCloumn("TABLE_CAT", "varchar"));
        schemaList.add(new DacpCloumn("TABLE_SCHEM", "varchar"));
        schemaList.add(new DacpCloumn("TABLE_NAME", "varchar"));
        schemaList.add(new DacpCloumn("COLUMN_NAME", "varchar"));
        schemaList.add(new DacpCloumn("DATA_TYPE", "varchar"));
        schemaList.add(new DacpCloumn("TYPE_NAME", "varchar"));
        schemaList.add(new DacpCloumn("COLUMN_SIZE", "varchar"));
        schemaList.add(new DacpCloumn("BUFFER_LENGTH", "varchar"));
        schemaList.add(new DacpCloumn("DECIMAL_DIGITS", "varchar"));
        schemaList.add(new DacpCloumn("NUM_PREC_RADIX", "varchar"));
        schemaList.add(new DacpCloumn("NULLABLE", "varchar"));
        schemaList.add(new DacpCloumn("REMARKS", "varchar"));
        schemaList.add(new DacpCloumn("COLUMN_DEF", "varchar"));
        schemaList.add(new DacpCloumn("SQL_DATA_TYPE", "varchar"));
        schemaList.add(new DacpCloumn("SQL_DATETIME_SUB", "varchar"));
        schemaList.add(new DacpCloumn("CHAR_OCTET_LENGTH", "varchar"));
        schemaList.add(new DacpCloumn("ORDINAL_POSITION", "varchar"));
        schemaList.add(new DacpCloumn("IS_NULLABLE", "varchar"));
        schemaList.add(new DacpCloumn("SCOPE_CATALOG", "varchar"));
        schemaList.add(new DacpCloumn("SCOPE_SCHEMA", "varchar"));
        schemaList.add(new DacpCloumn("SCOPE_TABLE", "varchar"));
        schemaList.add(new DacpCloumn("SOURCE_DATA_TYPE", "varchar"));
        schemaList.add(new DacpCloumn("IS_AUTOINCREMENT", "varchar"));
        schemaList.add(new DacpCloumn("IS_GENERATEDCOLUMN", "varchar"));
        
        List<List<Object>> dataList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            List<Object> row = new ArrayList<>();
            row.add("dacp_db" + 0);
            row.add("dacp_schem" + 0);
            row.add("dacp_tbl" + 0);
            row.add("tbl_col_name" + i);
            row.add("varchar");
            row.add("table");
            row.add("1");
            row.add("1");
            row.add("1");
            row.add("1");
            row.add("true");
            row.add("remarks" + i);
            row.add("col_def" + i);
            row.add("sql_data_type" + i);
            row.add("sql_sub" + i);
            row.add("20");
            row.add("position" + i);
            row.add("false");
            row.add("scope_catalog");
            row.add("scope_schema");
            row.add("scope_table");
            row.add("source_data_type");
            row.add("true");
            row.add("false");
            dataList.add(row);
        }
        
        CursorResults result = new CursorResults(dataList, 
                schemaList, 
                params.getPageNum(), 
                params.getPageSize(),
                1);
        
        DacpResults instance = new DacpResults("cursor", "taskId0000001", "000", "success", result);
//        System.out.println("getColumns: \n" + DACP_RESULT_CODEC.toJson(instance));
        return instance;
    }
    
    private DacpResults getUDTs(QueryParams params) {
        List<DacpCloumn> schemaList = new ArrayList<>();
        schemaList.add(new DacpCloumn("TYPE_CAT", "varchar"));
        schemaList.add(new DacpCloumn("TYPE_SCHEM", "varchar"));
        schemaList.add(new DacpCloumn("TYPE_NAME", "varchar"));
        schemaList.add(new DacpCloumn("CLASS_NAME", "varchar"));
        schemaList.add(new DacpCloumn("DATA_TYPE", "varchar"));
        schemaList.add(new DacpCloumn("REMARKS", "varchar"));
        schemaList.add(new DacpCloumn("BASE_TYPE", "varchar"));
        
        CursorResults result = new CursorResults(Collections.emptyList(), 
                schemaList, 
                params.getPageNum(), 
                params.getPageSize(),
                1);
        
        DacpResults instance = new DacpResults("cursor", "taskId0000001", "000", "success", result);
        System.out.println("getUDTs: \n" + DACP_RESULT_CODEC.toJson(instance));
        return instance;
    }
}
