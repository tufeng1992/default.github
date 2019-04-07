package com.study.desginpattern.adapter.service;

public interface ThridRegisterService{

    int registerForQQ(String qqNum, String password);

    int registerForWeChat(String openId);
}
