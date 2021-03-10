package com.javarush.task.task30.task3010;

import java.math.BigInteger;

/* 
Минимальное допустимое основание системы счисления
*/

public class Solution {
    public static void main(String[] args) {
        try {
            BigInteger bigInteger;
            for (int i = 2; i <= 36; i++) {
                try {
                    bigInteger = new BigInteger(args[0], i);
                    System.out.println(i);
                    return;
                } catch (Exception e) {

                }
            }
        } catch (Exception e) {
        }
        System.out.println("incorrect");
    }
}