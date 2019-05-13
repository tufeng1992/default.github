package com.study.test;

import com.study.annotations.Select;

import java.util.List;

public interface UserMapper {

    @Select(sql = "select * from tb_user")
    List<User> findList();



}
