package com.eeft.renaissance.sqlrunner.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class CustomDbExecutorDao {

    private final JdbcTemplate jdbcTemplate;

    public CustomDbExecutorDao(DataSource customDataSource) {
        this.jdbcTemplate = new JdbcTemplate(customDataSource);
    }

    public void executeStatement(String sql) {
        jdbcTemplate.execute(sql);
    }
}



