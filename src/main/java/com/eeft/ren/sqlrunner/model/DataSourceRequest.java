package com.eeft.ren.sqlrunner.model;

import lombok.Getter;
import lombok.Setter;

public class DataSourceRequest {
    @Setter
    @Getter
    private String jdbcUrl;
    @Setter @Getter
    private String username;
    @Setter @Getter
    private String password;
    @Setter @Getter
    private String hostIp;
    @Setter @Getter
    private String port;
    @Setter @Getter
    private String dbName;
    @Setter @Getter
    private DBType dbType;

}

