package com.study.test;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Long userId;

    private String username;

    private String mobile;

    private String password;

    private Date createTime;


}
