package com.eeft.renaissance.sqlrunner.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
@ConditionalOnProperty(name = "spring.datasource.mariadb.enabled", havingValue = "true")
public class MariaDbExecutorDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MariaDbExecutorDao(@Qualifier("mariadbDataSource") DataSource mariaDbDataSource) {
        this.jdbcTemplate = new JdbcTemplate(mariaDbDataSource);
    }

    public void executeStatement(String sql) {
        jdbcTemplate.execute(sql);
    }
}



