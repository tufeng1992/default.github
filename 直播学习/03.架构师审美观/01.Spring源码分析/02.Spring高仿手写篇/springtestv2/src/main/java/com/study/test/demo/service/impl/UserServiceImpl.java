package com.study.test.demo.service.impl;

import com.study.test.core.annotation.Autowired;
import com.study.test.core.annotation.Service;
import com.study.test.demo.entity.User;
import com.study.test.demo.service.UserService;

import java.util.HashMap;
import java.util.Map;


@Service(name = "userService")
public class UserServiceImpl implements UserService {

    private static Map<Long, User> userMap = new HashMap<>();

    static  {
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setId((long) i);
            user.setName("name" + i);
            user.setAge((i + 18));
            user.setPhone("1810111112" + i);
            userMap.put(user.getId(), user);
        }
    }

//    @Autowired
    private User user;

    @Override
    public String findUser(User user)throws Exception {
        User u = getUser(user);
        System.out.println(this.user.getName());
        if (null != u) {
            return u.toString();
        }

        return null;
    }

    @Override
    public String delete(Long id) {
        User user = new User();
        user.setId(id);
        User u = getUser(user);
        if (null != u) {
            return u.toString();
        }
        return null;
    }


    private User getUser(User user) {
        for (Long id : userMap.keySet()) {
            User u = userMap.get(id);
            if (id.equals(user.getId())) {
                return u;
            }
            if (null != user.getName() || !"".equals(user.getName())) {
                if (u.getName().equals(user.getName())) {
                    return u;
                }
            }
        }
        return null;
    }
}
