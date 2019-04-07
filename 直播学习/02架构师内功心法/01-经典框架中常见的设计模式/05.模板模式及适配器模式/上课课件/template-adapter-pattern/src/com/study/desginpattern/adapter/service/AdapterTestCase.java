package com.study.desginpattern.adapter.service;

import com.study.desginpattern.adapter.service.impl.RegisterServiceImpl;
import com.study.desginpattern.adapter.service.impl.ThridRegisterServiceImpl;

public class AdapterTestCase  {

    public static void main(String[] args) {
        RegisterService registerService = new RegisterServiceImpl();
        registerService.register("l3", "p3");

        ThridRegisterService thridRegisterService = new ThridRegisterServiceImpl();
        thridRegisterService.registerForQQ("280309791", "cccc");
        thridRegisterService.registerForWeChat("djsakjbdaskjdbbj1133dxd");


    }


}
