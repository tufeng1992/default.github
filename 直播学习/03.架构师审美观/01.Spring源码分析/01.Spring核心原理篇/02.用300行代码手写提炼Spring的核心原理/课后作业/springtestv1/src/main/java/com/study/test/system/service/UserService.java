package com.study.test.system.service;

import com.study.test.system.entity.User;

public interface UserService {

    String findUser(User user);

    String delete(Long id);
}
