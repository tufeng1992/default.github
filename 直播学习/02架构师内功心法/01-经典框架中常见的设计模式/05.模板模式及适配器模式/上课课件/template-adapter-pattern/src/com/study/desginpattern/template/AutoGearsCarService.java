package com.study.desginpattern.template;

/**
 * 自动挡车不用挂挡直接走
 */
public class AutoGearsCarService extends CarService {

    @Override
    protected void gears() {

    }

    @Override
    protected void accelerator() {
        System.out.println("踩油门");
    }
}
