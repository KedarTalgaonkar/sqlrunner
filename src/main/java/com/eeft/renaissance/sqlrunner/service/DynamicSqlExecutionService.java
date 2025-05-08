package com.eeft.renaissance.sqlrunner.service;

import com.eeft.renaissance.sqlrunner.dao.CustomDbExecutorDao;
import com.eeft.renaissance.sqlrunner.dao.H2DbExecutorDao;
import com.eeft.renaissance.sqlrunner.dao.MariaDbExecutorDao;
import com.eeft.renaissance.sqlrunner.dao.OracleExecutorDao;
import com.eeft.renaissance.sqlrunner.exception.SqlExecutionException;
import com.eeft.renaissance.sqlrunner.model.DBType;
import com.eeft.renaissance.sqlrunner.model.ExecutionResponse;
import com.eeft.renaissance.sqlrunner.parser.SqlScriptParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class DynamicSqlExecutionService {

    @Autowired(required = false) private final OracleExecutorDao oracleExecutorDao;
    @Autowired(required = false) private final MariaDbExecutorDao mariaDbExecutorDao;
    @Autowired(required = false) private final H2DbExecutorDao h2DbExecutorDao;
    private static CustomDbExecutorDao customDbExecutorDao;

    @Autowired
    public DynamicSqlExecutionService(
            @Autowired(required = false) OracleExecutorDao oracleExecutorDao,
            @Autowired(required = false) MariaDbExecutorDao mariaDbExecutorDao,
            @Autowired(required = false) H2DbExecutorDao h2DbExecutorDao
    ) {
        this.oracleExecutorDao = oracleExecutorDao;
        this.mariaDbExecutorDao = mariaDbExecutorDao;
        this.h2DbExecutorDao = h2DbExecutorDao;
    }

    public void setCustomDao( CustomDbExecutorDao customDbExecutorDao
    ) {
        DynamicSqlExecutionService.customDbExecutorDao = customDbExecutorDao;
    }

    public ExecutionResponse executeSqlFile(MultipartFile file, DBType dbType) {
        try {
            String scriptContent = new String(file.getBytes(), StandardCharsets.UTF_8);
            List<String> statements = SqlScriptParser.splitSqlScript(scriptContent);

            int successCount = 0;
            List<String> errors = new ArrayList<>();
            List<String> success = new ArrayList<>();

            for (String stmt : statements) {
                try {
                    if (dbType == DBType.ORACLE) {
                        if (oracleExecutorDao == null) {
                            throw new SqlExecutionException("Oracle DB is not configured or available.");
                        }
                        oracleExecutorDao.executeStatement(stmt);
                    } else if (dbType == DBType.MARIADB) {
                        if (mariaDbExecutorDao == null) {
                            throw new SqlExecutionException("MariaDB is not configured or available.");
                        }
                        mariaDbExecutorDao.executeStatement(stmt);
                    } else if (dbType == DBType.H2) {
                        if (h2DbExecutorDao == null) {
                            throw new SqlExecutionException("H2DB is not configured or available.");
                        }
                        h2DbExecutorDao.executeStatement(stmt);
                    } else if (dbType == DBType.Custom) {
                        if (customDbExecutorDao == null) {
                            throw new SqlExecutionException("Custom is not configured or available.");
                        }
                        customDbExecutorDao.executeStatement(stmt);
                    }else {
                        throw new SqlExecutionException("Unsupported DB Type: " + dbType);
                    }

                    successCount++;
                    success.add("Success executing: " + stmt);
                } catch (Exception e) {
                    errors.add("Error executing: " + stmt + " -> " + e.getMessage());
                }
            }

            return new ExecutionResponse(successCount, errors.size(), errors, success);

        } catch (IOException e) {
            throw new SqlExecutionException("Failed to read uploaded file", e);
        }
    }
}
