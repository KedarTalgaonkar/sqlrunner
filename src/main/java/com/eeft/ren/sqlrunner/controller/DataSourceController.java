package com.eeft.ren.sqlrunner.controller;

import com.eeft.ren.sqlrunner.dao.CustomDbExecutorDao;
import com.eeft.ren.sqlrunner.exception.SqlExecutionException;
import com.eeft.ren.sqlrunner.model.DBType;
import com.eeft.ren.sqlrunner.model.DataSourceRequest;
import com.eeft.ren.sqlrunner.service.DynamicSqlExecutionService;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.sql.DataSource;

@RestController
@RequestMapping("/public/api/datasource")
public class DataSourceController {

    private final DynamicSqlExecutionService executionService;

    @Autowired
    public DataSourceController(DynamicSqlExecutionService executionService) {
        this.executionService = executionService;
    }

    @PostMapping("/create")
    public String createDataSource(@RequestBody DataSourceRequest request) {
        try {
            String fullJdbcUrl = buildJdbcUrl(
                    request.getJdbcUrl(),
                    request.getHostIp(),
                    request.getPort(),
                    request.getDbName(),
                    request.getDbType()
            );

            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(fullJdbcUrl);
            config.setUsername(request.getUsername());
            config.setPassword(request.getPassword());

            // Optional: Set other Hikari settings here
            DataSource dataSource = new HikariDataSource(config);

            executionService.setCustomDao(new CustomDbExecutorDao(dataSource));
            // For simplicity, we're just returning a success message.
            return "DataSource created successfully for URL: " + fullJdbcUrl;
        } catch (Exception e) {
            return "Failed to create DataSource: " + e.getMessage();
        }
    }

    private String buildJdbcUrl(String jdbcUrl, String hostIp, String port, String dbName, DBType dbType) {
        if (jdbcUrl != null && !jdbcUrl.isEmpty()) {
            return jdbcUrl;
        }
        if (dbType == DBType.ORACLE) {
            return "jdbc:oracle:thin:@//" + hostIp + ":" + port + "/" + dbName;
        } else if (dbType == DBType.MARIADB) {
            return "jdbc:mariadb://" + hostIp + ":" + port + "/" + dbName;
        } else if (dbType == DBType.MSSQL) {
            return "jdbc:sqlserver://" + hostIp + ":" + port + ";databaseName=" + dbName + ";";
        } else if (dbType == DBType.MySQL) {
            return "jdbc:mysql://" + hostIp + ":" + port + "/" + dbName;
        } else if (dbType == DBType.H2) {
            return "jdbc:h2:mem:"+dbName;
        }else {
            throw new SqlExecutionException("Unsupported DB Type: " + dbType);
        }
    }
}
