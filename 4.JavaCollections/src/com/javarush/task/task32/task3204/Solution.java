package com.javarush.task.task32.task3204;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/* 
Генератор паролей
*/

public class Solution {
    public static void main(String[] args) throws IOException {
        ByteArrayOutputStream password = getPassword();
        System.out.println(password.toString());
    }

    public static ByteArrayOutputStream getPassword() throws IOException {
        StringBuilder builder = new StringBuilder();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        for (int i = 0; i < 3; i++) {
            builder.append((char) ((Math.random() * (('9' - '0') + 1)) + (int) '0'));
            builder.append((char) ((Math.random() * (('z' - 'a') + 1)) + (int) 'a'));
            builder.append((char) ((Math.random() * (('Z' - 'A') + 1)) + (int) 'A'));
        }

        builder.deleteCharAt((int) (Math.random() * 8));
        baos.write(builder.toString().getBytes());

        return baos;
    }
}
