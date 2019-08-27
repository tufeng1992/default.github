package com.study.test.service;

public class UserServiceImpl implements IUserService {


    @Override
    public String listenUser(String str) {
        System.out.println("说啥：" + str);
        return "listenUser:收到！";
    }

    @Override
    public String toUser(String str) {
        System.out.println("来啦老弟：" + str);
        return "toUser:收到！";
    }
}
