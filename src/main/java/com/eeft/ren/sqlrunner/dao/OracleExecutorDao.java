package com.eeft.ren.sqlrunner.dao;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@ConditionalOnProperty(name = "spring.datasource.oracle.enabled", havingValue = "true")
public class OracleExecutorDao extends ExecutorDao{

    @Autowired
    public OracleExecutorDao(@Qualifier("oracleDataSource") DataSource oracleDataSource) {
        this.jdbcTemplate = new JdbcTemplate(oracleDataSource);
    }

}

