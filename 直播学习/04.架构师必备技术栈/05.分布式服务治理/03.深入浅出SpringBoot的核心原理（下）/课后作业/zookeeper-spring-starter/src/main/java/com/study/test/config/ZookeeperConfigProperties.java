package com.study.test.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "spring.curator")
public class ZookeeperConfigProperties {

    private String connectString;

    private String namespace;

    private int connectTimeOutMs;

    private int sessionTimeOutMs;

    private int retryCount;

    private int retryIntervalMs;


}
