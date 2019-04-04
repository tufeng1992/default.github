package com.study.desginpattern.strategy;

import com.study.desginpattern.strategy.entity.Noodle;

/**
 * 面馆
 */
public class NoodleRestaurant {

    public Noodle order(String noodlerName) {
        return Menu.getNoodle(noodlerName).make();
    }
}
