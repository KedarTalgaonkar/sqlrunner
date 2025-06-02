package com.eeft.ren.sqlrunner.config;

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
        return createDataSource();
    }

    @Bean(name = "mariadbDataSource")
    @ConditionalOnProperty(name = "spring.datasource.mariadb.enabled", havingValue = "true")
    @ConfigurationProperties(prefix = "spring.datasource.mariadb")
    @Primary  // You can make this conditional if needed
    public DataSource mariadbDataSource() {
        return createDataSource();
    }

    @Bean(name = "h2DataSource")
    @ConditionalOnProperty(name = "spring.datasource.h2.enabled", havingValue = "true")
    @ConfigurationProperties(prefix = "spring.datasource.h2")
    public DataSource h2DataSource() {
        return createDataSource();
    }

    private DataSource createDataSource() {
        return DataSourceBuilder.create().build();
    }
}
