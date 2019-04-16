package com.study.test.observer.event;

import com.google.common.eventbus.Subscribe;

public class GuavaEvent {

    @Subscribe
    public void sub(String str) {
        System.out.println("GuavaEvent:" + str);
    }
}
