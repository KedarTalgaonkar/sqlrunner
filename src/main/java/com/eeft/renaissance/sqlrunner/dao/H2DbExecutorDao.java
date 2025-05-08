package com.eeft.renaissance.sqlrunner.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
@ConditionalOnProperty(name = "spring.datasource.h2.enabled", havingValue = "true")
public class H2DbExecutorDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public H2DbExecutorDao(@Qualifier("h2DataSource") DataSource mariaDbDataSource) {
        this.jdbcTemplate = new JdbcTemplate(mariaDbDataSource);
    }

    public void executeStatement(String sql) {
        jdbcTemplate.execute(sql);
    }
}



