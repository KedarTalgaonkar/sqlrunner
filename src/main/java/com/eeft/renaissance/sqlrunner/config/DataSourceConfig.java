package com.eeft.renaissance.sqlrunner.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Bean(name = "oracleDataSource")
    @ConditionalOnProperty(name = "spring.datasource.oracle.enabled", havingValue = "true")
    @ConfigurationProperties(prefix = "spring.datasource.oracle")
    public DataSource oracleDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "mariadbDataSource")
    @Primary
    @ConditionalOnProperty(name = "spring.datasource.mariadb.enabled", havingValue = "true")
    @ConfigurationProperties(prefix = "spring.datasource.mariadb")
    public DataSource mariadbDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "h2DataSource")
    @ConditionalOnProperty(name = "spring.datasource.h2.enabled", havingValue = "true")
    @ConfigurationProperties(prefix = "spring.datasource.h2")
    public DataSource h2DataSource() {
        return DataSourceBuilder.create().build();
    }

}

