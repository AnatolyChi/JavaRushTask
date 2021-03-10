package com.javarush.task.task32.task3210;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;

/* 
Используем RandomAccessFile
*/

public class Solution {
    public static void main(String... args) {
        long number = Long.parseLong(args[1]);
        String text = args[2];

        try (RandomAccessFile raf = new RandomAccessFile(args[0], "rw")) {
            raf.seek(number);

            byte[] bufText = text.getBytes();
            byte[] bufSb = new byte[text.length()];
            raf.read(bufSb, 0, bufSb.length);

            byte[] content;
            raf.seek(raf.length());
            if (Arrays.equals(bufText, bufSb)) {
                content = "true".getBytes();
            } else {
                content = "false".getBytes();
            }
            raf.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
