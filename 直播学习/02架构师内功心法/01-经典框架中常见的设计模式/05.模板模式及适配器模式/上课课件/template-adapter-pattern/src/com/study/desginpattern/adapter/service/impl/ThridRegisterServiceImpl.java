package com.study.desginpattern.adapter.service.impl;

import com.study.desginpattern.adapter.service.RegisterService;
import com.study.desginpattern.adapter.service.ThridRegisterService;

public class ThridRegisterServiceImpl implements ThridRegisterService {

    private RegisterService registerService = new RegisterServiceImpl();

    public static final String openId_default_password = "opennnn11";

    @Override
    public int registerForQQ(String qqNum, String password) {
        return registerService.register(qqNum, password);
    }

    @Override
    public int registerForWeChat(String openId) {
        return registerService.register(openId, openId_default_password);
    }
}
