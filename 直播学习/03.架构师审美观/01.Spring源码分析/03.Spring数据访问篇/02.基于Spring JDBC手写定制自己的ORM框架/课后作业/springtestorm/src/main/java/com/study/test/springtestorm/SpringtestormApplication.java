package com.study.test.springtestorm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.study.test.demo")
public class SpringtestormApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringtestormApplication.class, args);
    }

}
