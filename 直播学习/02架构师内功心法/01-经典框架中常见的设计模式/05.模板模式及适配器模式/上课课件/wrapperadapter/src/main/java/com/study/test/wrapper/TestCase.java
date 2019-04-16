package com.study.test.wrapper;

import com.study.test.wrapper.base.RegisterService;
import com.study.test.wrapper.model.User;
import com.study.test.wrapper.model.UserWrapper;
import com.study.test.wrapper.utils.RegisterHelper;

public class TestCase {

    public static void main(String[] args) {
        User user = new User("12", "小明");
        UserWrapper wxUserWrapper = new UserWrapper(user, "WX");
        RegisterService registerService = RegisterHelper.chooseRegister(wxUserWrapper);
        System.out.println(registerService.register(wxUserWrapper));



        User qquser = new User("15", "小花");
        UserWrapper qqUserWrapper = new UserWrapper(qquser, "QQ");
        RegisterService qqregisterService = RegisterHelper.chooseRegister(qqUserWrapper);
        System.out.println(qqregisterService.register(qqUserWrapper));
    }
}
