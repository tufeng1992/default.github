package com.study.test.service;

public class SayHelloServiceImpl {

    private IUserService userService;

    public SayHelloServiceImpl(IUserService userService) {
        this.userService = userService;
    }

    public void sayHello(String str){
        System.out.println(userService.toUser(str));
    }

    public void sayListen(String str) {
        System.out.println(userService.listenUser(str));
    }
}
