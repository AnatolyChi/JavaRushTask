package com.javarush.task.task33.task3303;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

/* 
Десериализация JSON объекта
*/

public class Solution {
    public static <T> T convertFromJsonToNormal(String fileName, Class<T> clazz) throws IOException {
        ObjectMapper obj = new ObjectMapper();
        File file = new File(fileName);
        return obj.readValue(file, clazz);
    }

    public static void main(String[] args) {

    }
}
