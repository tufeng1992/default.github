package com.study.test.biz.controller;

import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestCuratorController {

    @Autowired
    private CuratorFramework curatorFramework;


    @GetMapping("index")
    public String index() {
        try {
            curatorFramework.start();
            curatorFramework.create().withMode(CreateMode.EPHEMERAL).forPath("/testCurator");
            curatorFramework.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "test";
    }

}
