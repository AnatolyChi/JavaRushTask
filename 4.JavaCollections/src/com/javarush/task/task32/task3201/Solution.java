package com.javarush.task.task32.task3201;

import java.io.IOException;
import java.io.RandomAccessFile;

/* 
Запись в существующий файл
*/

public class Solution {
    public static void main(String... args) throws IOException {
        long number = Integer.parseInt(args[1]);
        String text = args[2];

        try (RandomAccessFile raf = new RandomAccessFile(args[0], "rw");) {
            number = Math.min(number, raf.length());
            raf.seek(number);
            byte[] content = text.getBytes();
            raf.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
