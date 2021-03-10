package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.kitchen.Cook;
import com.javarush.task.task27.task2712.kitchen.Waiter;

import java.io.IOException;

//основной клвсс
public class Restaurant {
    public static void main(String[] args) throws IOException {
        Cook cook = new Cook("Amigo");
        Waiter waiter = new Waiter();
        Tablet tablet = new Tablet(1);
        cook.addObserver(waiter);
        tablet.addObserver(cook);

        tablet.createOrder();
        tablet.createOrder();
        tablet.createOrder();
        tablet.createOrder();

        DirectorTablet directorTablet = new DirectorTablet();
        directorTablet.printCookWorkloading();
        directorTablet.printActiveVideoSet();
        directorTablet.printAdvertisementProfit();
        directorTablet.printArchivedVideoSet();
    }
}
