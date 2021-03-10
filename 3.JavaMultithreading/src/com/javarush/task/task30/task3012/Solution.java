package com.javarush.task.task30.task3012;

/* 
Получи заданное число
*/

public class Solution {
    public static void main(String[] args) {
        Solution solution = new Solution();
        solution.createExpression(74);
    }

    public void createExpression(int number) {
        int[] array = {1, 3, 9, 27, 81, 243, 729, 2187};
        StringBuilder stringBuilder = new StringBuilder(number + " =");

        for (int i = 0; i < array.length; i++) {
            if (number % 3 == 1) {
                stringBuilder.append(" + ").append(array[i]);
            } else if (number % 3 == 2) {
                stringBuilder.append(" - ").append(array[i]);
                number++;
            }
            number /= 3;
        }

        System.out.println(stringBuilder.toString());
    }
}