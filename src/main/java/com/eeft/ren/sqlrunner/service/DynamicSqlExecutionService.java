package com.eeft.ren.sqlrunner.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.eeft.ren.sqlrunner.dao.CustomDbExecutorDao;
import com.eeft.ren.sqlrunner.dao.ExecutorDao;
import com.eeft.ren.sqlrunner.dao.H2DbExecutorDao;
import com.eeft.ren.sqlrunner.dao.MariaDbExecutorDao;
import com.eeft.ren.sqlrunner.dao.OracleExecutorDao;
import com.eeft.ren.sqlrunner.exception.SqlExecutionException;
import com.eeft.ren.sqlrunner.model.DBType;
import com.eeft.ren.sqlrunner.model.ExecutionResponse;
import com.eeft.ren.sqlrunner.parser.SqlScriptParser;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class DynamicSqlExecutionService {

    private final Optional<OracleExecutorDao> oracleExecutorDao;
    private final Optional<MariaDbExecutorDao> mariaDbExecutorDao;
    private final Optional<H2DbExecutorDao> h2DbExecutorDao;
    private CustomDbExecutorDao customDbExecutorDao;

    public DynamicSqlExecutionService(
            Optional<OracleExecutorDao> oracleExecutorDao,
            Optional<MariaDbExecutorDao> mariaDbExecutorDao,
            Optional<H2DbExecutorDao> h2DbExecutorDao
    ) {
        this.oracleExecutorDao = oracleExecutorDao;
        this.mariaDbExecutorDao = mariaDbExecutorDao;
        this.h2DbExecutorDao = h2DbExecutorDao;
    }

    public void setCustomDao(CustomDbExecutorDao customDbExecutorDao) {
        this.customDbExecutorDao = customDbExecutorDao;
    }

    public ExecutionResponse executeSqlFile(MultipartFile file, DBType dbType) {
        try {
            String scriptContent = new String(file.getBytes(), StandardCharsets.UTF_8);
            List<String> statements = SqlScriptParser.splitSqlScript(scriptContent);

            int successCount = 0;
            List<String> errors = new ArrayList<>();
            Map<String, Object> success = new HashMap<>();

            for (String stmt : statements) {
                try {
                	 Object response = getExecutor(dbType).executeStatement(stmt);
                    successCount++;
                    success.put("Success executing: " + stmt, response);
                } catch (Exception e) {
                    errors.add("Error executing: " + stmt + " -> " + e.getMessage());
                }
            }

            return new ExecutionResponse(successCount, errors.size(), errors, success);

        } catch (IOException e) {
            throw new SqlExecutionException("Failed to read uploaded file", e);
        }
    }

    private ExecutorDao getExecutor(DBType dbType) {
        switch (dbType) {
            case ORACLE:
                return oracleExecutorDao.orElseThrow(() ->
                        new SqlExecutionException("Oracle DB is not configured or available."));
            case MARIADB:
                return mariaDbExecutorDao.orElseThrow(() ->
                        new SqlExecutionException("MariaDB is not configured or available."));
            case H2:
                return h2DbExecutorDao.orElseThrow(() ->
                        new SqlExecutionException("H2 DB is not configured or available."));
            case Custom:
                if (customDbExecutorDao == null) {
                    throw new SqlExecutionException("Custom DB is not configured or available.");
                }
                return customDbExecutorDao;
            default:
                throw new SqlExecutionException("Unsupported DB Type: " + dbType);
        }
    }
}
