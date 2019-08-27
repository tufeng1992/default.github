package com.study.test.config;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author zero
 */
@Configuration
@EnableConfigurationProperties(ZookeeperConfigProperties.class)
@ConditionalOnClass(CuratorFramework.class)
public class ZookeeperAutoConfig  {

    @Autowired
    private ZookeeperConfigProperties zookeeperConfigProperties;

    @Bean
    public CuratorFramework getCuratorFramework() {
        System.out.println("curatorFramework init");
        return CuratorFrameworkFactory.builder()
                .connectString(zookeeperConfigProperties.getConnectString())
                .connectionTimeoutMs(zookeeperConfigProperties.getConnectTimeOutMs())
                .sessionTimeoutMs(zookeeperConfigProperties.getSessionTimeOutMs())
                .retryPolicy(new RetryNTimes(zookeeperConfigProperties.getRetryCount(), zookeeperConfigProperties.getRetryIntervalMs()))
                .namespace(zookeeperConfigProperties.getNamespace()).build();
    }

}
