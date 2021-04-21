package com.javarush.task.task25.task2515;

import java.util.Arrays;

public class Canvas {
    private int width;
    private int height;
    private char[][] matrix;

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public char[][] getMatrix() {
        return matrix;
    }

    public Canvas(int weight, int height) {
        this.width = weight;
        this.height = height;
        matrix = new char[height][weight];
    }

    public void setPoint(double x, double y, char c) {
        int doubleX = (int) Math.round(x);
        int doubleY = (int) Math.round(y);
        if (doubleX >= 0 && doubleX < matrix[0].length && doubleY >= 0 && doubleY < matrix.length) {
            matrix[doubleY][doubleX] = c;
        }
    }

    public void drawMatrix(double x, double y, int[][] matrix, char c) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] != 0) {
                    setPoint(x + j, y + i, c);
                }
            }
        }
    }

    public void clear() {
        for (char[] chars : matrix) {
            Arrays.fill(chars, ' ');
        }
    }

    public void print() {
        for (char[] chars : matrix) {
            for (int j = 0; j < chars.length; j++) {
                if (chars[j] != ' ') {
                    System.out.print(chars[j]);
                }
                System.out.println();
            }
        }
        System.out.println();
        System.out.println();
    }
}
