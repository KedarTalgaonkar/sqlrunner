package com.eeft.ren.sqlrunner.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class CustomDbExecutorDao extends ExecutorDao{

    public CustomDbExecutorDao(DataSource customDataSource) {
        this.jdbcTemplate = new JdbcTemplate(customDataSource);
    }
}