package com.eeft.ren.sqlrunner.parser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.eeft.ren.sqlrunner.model.DBType;
import com.eeft.ren.sqlrunner.model.ExecutionResponse;
import com.eeft.ren.sqlrunner.service.DynamicSqlExecutionService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class SqlBatchProcessor {

    private final DynamicSqlExecutionService executionService;
    private static final Logger logger = LoggerFactory.getLogger(SqlBatchProcessor.class);

    @Autowired
    public SqlBatchProcessor(DynamicSqlExecutionService executionService) {
        this.executionService = executionService;
    }

    public List<ExecutionResponse> processSqlFiles(String directoryPath, DBType dbType ) {
        File dir = new File(directoryPath);
        if (!dir.isDirectory()) {
            logger.error("Provided path '{}' is not a directory.", directoryPath);
            return Collections.emptyList();
        }

        File[] sqlFiles = dir.listFiles((d, name) -> name.toLowerCase().endsWith(".sql"));

        if (sqlFiles == null || sqlFiles.length == 0) {
            logger.info("No SQL files found in the directory '{}'.", directoryPath);
            return Collections.emptyList();
        }

        // Sort files alphabetically
        Arrays.sort(sqlFiles, (f1, f2) -> f1.getName().compareToIgnoreCase(f2.getName()));

        List<ExecutionResponse> responses = new ArrayList<>();
        for (File file : sqlFiles) {
            try {
                logger.info("Processing file: {}", file.getName());

                // Create MultipartFile
                MultipartFile multipartFile = createMultipartFile(file);

                // Simulate processing (e.g., upload, execute SQL)
                responses.add(processSqlMultipartFile(multipartFile, dbType));

                logger.info("Successfully processed: {}", file.getName());
            } catch (Exception e) {
                logger.error("Failed to process file: {}", file.getName(), e);
            }
        }
        return responses;
    }

    private static MultipartFile createMultipartFile(File file) throws IOException {
        try (FileInputStream input = new FileInputStream(file)) {
            return new MockMultipartFile(
                    file.getName(),
                    file.getName(),
                    "application/sql",
                    input
            );
        }
    }

    private ExecutionResponse processSqlMultipartFile(MultipartFile multipartFile, DBType dbType) throws Exception {
        String content = new String(multipartFile.getBytes());
        ExecutionResponse response = executionService.executeSqlFile(multipartFile, dbType);
        if (content.contains("ERROR_SIMULATE")) {
            throw new Exception("Simulated SQL processing error.");
        }
        return response;
    }

    public List<ExecutionResponse> executeSqlFile(DBType dbType) {
        String directoryPath = "C:\\Users\\ktalgaonkar\\Downloads\\resp";
        return processSqlFiles(directoryPath, dbType);
    }
}
