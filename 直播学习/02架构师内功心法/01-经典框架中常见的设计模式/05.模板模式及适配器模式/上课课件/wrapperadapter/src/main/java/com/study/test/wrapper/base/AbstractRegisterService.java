package com.study.test.wrapper.base;

import com.study.test.wrapper.model.User;
import com.study.test.wrapper.model.UserWrapper;

public abstract class AbstractRegisterService implements RegisterService{

    public String register(UserWrapper userWrapper) {
        System.out.println("注册前动作");
        return doRegister(userWrapper.getUser());
    }

    protected abstract String doRegister(User user);
}
