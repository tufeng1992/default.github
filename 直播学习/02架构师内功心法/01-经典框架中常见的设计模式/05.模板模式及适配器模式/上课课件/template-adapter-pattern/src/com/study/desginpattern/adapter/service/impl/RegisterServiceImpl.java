package com.study.desginpattern.adapter.service.impl;

import com.study.desginpattern.adapter.entity.UserInfo;
import com.study.desginpattern.adapter.service.RegisterService;

import java.util.HashMap;
import java.util.Map;

public class RegisterServiceImpl implements RegisterService {

    private static Map<String, Object> map = new HashMap<String, Object>();

    static {
        UserInfo userInfo1 = new UserInfo("loginName1", "password1");
        UserInfo userInfo2 = new UserInfo("loginName2", "password2");
        map.put("loginName1", userInfo1);
        map.put("loginName2", userInfo2);
    }


    @Override
    public int register(String loginName, String password) {
        if (!map.containsKey(loginName)) {
            UserInfo userInfo = new UserInfo(loginName, password);
            map.put(loginName, userInfo);
            System.out.println(map);
            return 1;
        }
        return 0;
    }
}
