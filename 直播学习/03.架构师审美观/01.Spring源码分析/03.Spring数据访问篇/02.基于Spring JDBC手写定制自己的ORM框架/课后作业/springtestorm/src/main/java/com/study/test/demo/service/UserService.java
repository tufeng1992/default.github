package com.study.test.demo.service;

import com.study.test.demo.entity.User;

import java.util.List;

public interface UserService {

    List<User> findUser(User user) throws Exception;

    String delete(Long id);
}
