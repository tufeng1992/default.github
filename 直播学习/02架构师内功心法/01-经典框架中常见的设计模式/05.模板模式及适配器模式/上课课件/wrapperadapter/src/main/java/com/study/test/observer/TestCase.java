package com.study.test.observer;

import com.google.common.eventbus.EventBus;
import com.study.test.observer.event.GuavaEvent;

public class TestCase {

    public static void main(String[] args) {
        EventBus eventBus = new EventBus();
        eventBus.register(new GuavaEvent());
        eventBus.post("hello!!!!");

    }
}
