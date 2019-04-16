package com.study.test.wrapper.service;

import com.study.test.wrapper.base.AbstractRegisterService;
import com.study.test.wrapper.base.RegisterService;
import com.study.test.wrapper.model.User;

public class WxRegisterService extends AbstractRegisterService implements RegisterService {

    @Override
    protected String doRegister(User user) {
        System.out.println("微信注册");
        return user.toString();
    }

}
