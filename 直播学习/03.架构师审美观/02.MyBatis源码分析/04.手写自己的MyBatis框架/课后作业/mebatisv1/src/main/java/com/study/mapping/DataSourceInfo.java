package com.study.mapping;

import lombok.Data;

@Data
public class DataSourceInfo {

    private final String driver;

    private final String url;

    private final String username;

    private final String password;

    public DataSourceInfo(String driver, String url, String username, String password) {
        this.driver = driver;
        this.url = url;
        this.username = username;
        this.password = password;
    }
}
