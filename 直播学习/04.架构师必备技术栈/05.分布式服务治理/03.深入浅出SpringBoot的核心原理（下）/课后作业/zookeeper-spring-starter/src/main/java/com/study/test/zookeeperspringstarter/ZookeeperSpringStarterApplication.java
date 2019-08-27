package com.study.test.zookeeperspringstarter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.study.test")
public class ZookeeperSpringStarterApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZookeeperSpringStarterApplication.class, args);
    }

}
