package com.study.test.wrapper.utils;

import com.study.test.wrapper.base.RegisterService;
import com.study.test.wrapper.model.UserWrapper;
import com.study.test.wrapper.service.QQRegisterService;
import com.study.test.wrapper.service.WxRegisterService;

public class RegisterHelper {

    public static RegisterService chooseRegister(UserWrapper userWrapper) {
        //dosomething
        if ("WX".equals(userWrapper.getChannel())) {
            return new WxRegisterService();
        } else if ("QQ".equals(userWrapper.getChannel())) {
            return new QQRegisterService();
        }
        return null;
    }
}
