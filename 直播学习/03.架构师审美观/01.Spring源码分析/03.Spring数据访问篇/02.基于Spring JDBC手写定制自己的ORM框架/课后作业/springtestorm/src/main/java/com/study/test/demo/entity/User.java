package com.study.test.demo.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@ToString
@Entity
@Table(name = "t_user")
public class User implements Serializable {

    @Id
    private Long id;

    private String name;

    private Long age;

    private String phone;

}
