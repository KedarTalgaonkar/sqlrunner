package com.eeft.renaissance.sqlrunner.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
@ConditionalOnProperty(name = "spring.datasource.oracle.enabled", havingValue = "true")
public class OracleExecutorDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public OracleExecutorDao(@Qualifier("oracleDataSource") DataSource oracleDataSource) {
        this.jdbcTemplate = new JdbcTemplate(oracleDataSource);
    }

    public void executeStatement(String sql) {
        jdbcTemplate.execute(sql);
    }
}

