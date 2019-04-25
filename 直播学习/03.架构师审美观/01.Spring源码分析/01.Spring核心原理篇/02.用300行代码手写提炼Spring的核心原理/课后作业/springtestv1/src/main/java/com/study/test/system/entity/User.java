package com.study.test.system.entity;

import com.study.test.core.annotation.STComponent;
import lombok.Data;
import lombok.ToString;

@STComponent
@Data
@ToString
public class User {

    private Long id;

    private String name;

    private int age;

    private String phone;

}
