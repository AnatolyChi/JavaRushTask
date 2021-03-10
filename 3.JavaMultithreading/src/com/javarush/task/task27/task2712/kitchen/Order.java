package com.javarush.task.task27.task2712.kitchen;

import com.javarush.task.task27.task2712.ConsoleHelper;
import com.javarush.task.task27.task2712.Tablet;

import java.io.IOException;
import java.util.List;

//класс заказов
public class Order {
    private final Tablet tablet;
    protected List<Dish> dishes;

    public Tablet getTablet() {
        return tablet;
    }

    public Order(Tablet tablet) throws IOException {
        this.tablet = tablet;
        this.dishes = ConsoleHelper.getAllDishesForOrder();
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public int getTotalCookingTime() {
        int timer = 0;
        for (Dish dish : dishes) {
            timer += dish.getDuration();
        }
        return timer;
    }

    public boolean isEmpty() {
        return dishes.size() == 0;
    }

    @Override
    public String toString() {
        if (dishes.size() == 0) {
            return "";
        } else {
            return "Your order: " +
                    dishes +
                    " of " + tablet + ", cooking time " +
                    getTotalCookingTime() + "min";
        }
    }
}
