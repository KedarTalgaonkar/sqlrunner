package com.eeft.renaissance.sqlrunner.controller;

import com.eeft.renaissance.sqlrunner.model.DBType;
import com.eeft.renaissance.sqlrunner.model.ExecutionResponse;
import com.eeft.renaissance.sqlrunner.parser.SqlBatchProcessor;
import com.eeft.renaissance.sqlrunner.service.DynamicSqlExecutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/public/api/sql")
public class MultiDbSqlExecutionController {

    private final DynamicSqlExecutionService executionService;
    private final SqlBatchProcessor sqlBatchProcessor;

    @Autowired
    public MultiDbSqlExecutionController(DynamicSqlExecutionService executionService, SqlBatchProcessor sqlBatchProcessor) {
        this.executionService = executionService;
        this.sqlBatchProcessor = sqlBatchProcessor;
    }

    @PostMapping(value = "/execute", consumes = "multipart/form-data")
    public ResponseEntity<ExecutionResponse> executeSqlScript(
            @RequestParam("file") MultipartFile file,
            @RequestParam("dbType") DBType dbType) {

        ExecutionResponse response = executionService.executeSqlFile(file, dbType);
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/executeFromPath")
    public ResponseEntity<List<ExecutionResponse>> executeSqlScriptFromPath(
            @RequestParam("dbType") DBType dbType) {
        System.out.println("executeSqlScriptFromPath");
        List<ExecutionResponse> response = sqlBatchProcessor.executeSqlFile(dbType);
        return ResponseEntity.ok(response);
    }
}

