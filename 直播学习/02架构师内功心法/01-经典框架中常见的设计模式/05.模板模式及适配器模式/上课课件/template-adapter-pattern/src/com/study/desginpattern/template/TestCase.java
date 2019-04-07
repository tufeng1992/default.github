package com.study.desginpattern.template;

public class TestCase {

    public static void main(String[] args) {
        CarService carService = new AutoGearsCarService();

        CarService carService2 = new GeneralCarService();

        carService.run();

        carService2.run();
    }
}
