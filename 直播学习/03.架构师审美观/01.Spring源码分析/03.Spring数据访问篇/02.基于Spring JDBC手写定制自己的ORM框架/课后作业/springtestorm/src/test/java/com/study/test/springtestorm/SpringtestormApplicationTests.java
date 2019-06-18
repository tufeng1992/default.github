package com.study.test.springtestorm;

import com.study.test.demo.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringtestormApplicationTests {

    @Autowired
    private UserService userService;

    @Test
    public void contextLoads() throws Exception {
        System.out.println(userService.findUser(null));

    }

}
