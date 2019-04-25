package com.study.test.system.controller;

import com.study.test.core.annotation.STAutowired;
import com.study.test.core.annotation.STController;
import com.study.test.core.annotation.STRequestMapping;
import com.study.test.core.annotation.STRequestParam;
import com.study.test.system.entity.User;
import com.study.test.system.service.UserService;

@STController
@STRequestMapping(path = "user")
public class UserController {

    @STAutowired
    private UserService userService;

    @STRequestMapping(path = "findUser")
    public String find(@STRequestParam(name = "name") String name, @STRequestParam(name = "age") Integer age) {
        User user = new User();
        user.setName(name);
        user.setAge(age);
        System.out.println(name);
        System.out.println(age);
        return userService.findUser(user);
    }

    @STRequestMapping(path = "delete")
    public String delete(@STRequestParam(name = "id") Long id) {
        User user = new User();
        user.setId(id);
        System.out.println(id);
        return userService.delete(id);
    }
}
