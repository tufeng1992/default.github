package com.study.test.demo.controller;

import com.study.test.demo.entity.User;
import com.study.test.demo.service.UserService;

import java.util.LinkedHashMap;
import java.util.Map;
//
//@Controller
//@RequestMapping(path = "user")
//public class UserController {
//
//    @Autowired
//    private UserService userService;
//
//    @RequestMapping(path = "findUser")
//    public String find(@RequestParam(name = "name") String name, @RequestParam(name = "age") Integer age) throws Exception {
//        User user = new User();
//        user.setName(name);
//        user.setAge(age);
//        System.out.println(name);
//        System.out.println(age);
//        return userService.findUser(user);
//    }
//
//    @RequestMapping(path = "delete")
//    public String delete(@RequestParam(name = "id") Long id) {
//        User user = new User();
//        user.setId(id);
//        System.out.println(id);
//        return userService.delete(id);
//    }
//
//    @RequestMapping(path = "test")
//    public ModelAndView test(@RequestParam(name = "id") Long id) {
//        Map<String, Object> model = new LinkedHashMap<>();
//        model.put("id", id);
//        model.put("msg1", "a one msg");
//        model.put("button1", "a button");
//        return new ModelAndView("view", model);
//    }
//}
