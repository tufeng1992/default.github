package com.study.desginpattern.template;

/**
 * 手动挡车需要挂挡
 */
public class GeneralCarService extends CarService {
    @Override
    protected void gears() {
        System.out.println("5 挡走起");
    }

    @Override
    protected void accelerator() {
        System.out.println("踩油门");
    }
}
