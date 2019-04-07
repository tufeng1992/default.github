package com.study.desginpattern.template;

public abstract class CarService implements Car {

    @Override
    public void run() {
        System.out.println("open the door");
        gears();
        accelerator();
    }

    /**
     * 档位
     */
    protected abstract void gears();

    /**
     * 油门
     */
    protected abstract void accelerator();
}
