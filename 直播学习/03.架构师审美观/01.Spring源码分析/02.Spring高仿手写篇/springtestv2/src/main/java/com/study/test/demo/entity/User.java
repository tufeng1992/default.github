package com.study.test.demo.entity;

import com.study.test.core.annotation.Component;
import lombok.Data;
import lombok.ToString;

@Component
@Data
@ToString
public class User {

    private Long id;

    private String name;

    private int age;

    private String phone;

}
