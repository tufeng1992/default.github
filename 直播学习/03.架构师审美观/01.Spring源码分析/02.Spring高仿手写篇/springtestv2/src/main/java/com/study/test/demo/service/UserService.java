package com.study.test.demo.service;

import com.study.test.demo.entity.User;

public interface UserService {

    String findUser(User user) throws Exception;

    String delete(Long id);
}
