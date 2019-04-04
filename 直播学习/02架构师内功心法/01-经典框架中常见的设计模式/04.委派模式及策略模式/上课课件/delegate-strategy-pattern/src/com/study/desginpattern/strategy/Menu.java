package com.study.desginpattern.strategy;

import com.study.desginpattern.strategy.entity.ChongQingNoodle;
import com.study.desginpattern.strategy.entity.LanZhouNoodle;
import com.study.desginpattern.strategy.entity.Noodle;
import com.study.desginpattern.strategy.entity.WuHanNoodle;

import java.util.HashMap;
import java.util.Map;

/**
 * 面馆菜单
 */
public enum Menu {

    CQ("CQ", new ChongQingNoodle()),
    WH("WH", new WuHanNoodle()),
    LZ("LZ", new LanZhouNoodle()),
    ;

    private static Map<String, Noodle> menuMap = new HashMap<String, Noodle>();

    static  {
        for (Menu menu : values()) {
            menuMap.put(menu.key, menu.noodle);
        }
    }

    private String key;

    private Noodle noodle;

    Menu(String key, Noodle noodle) {
        this.key = key;
        this.noodle = noodle;
    }

    public static Noodle getNoodle(String key) {
        return menuMap.get(key);
    }
}
