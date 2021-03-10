package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.kitchen.Dish;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

//вспомогательный класс
public class ConsoleHelper {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void writeMessage(String message) {
        System.out.println(message);
    }

    public static String readString() throws IOException {
        return reader.readLine();
    }

    public static List<Dish> getAllDishesForOrder() throws IOException {
        List<Dish> dishes = new ArrayList<>();

        while (true) {
            String dishName = ConsoleHelper.readString().trim();
            if (dishName.equals("exit")) {
                break;
            }

            try {
                Dish dish = Dish.valueOf(dishName);
                dishes.add(dish);
            } catch (Exception e) {
                ConsoleHelper.writeMessage("Повторите ввод");
            }
        }
        return dishes;
    }
}
