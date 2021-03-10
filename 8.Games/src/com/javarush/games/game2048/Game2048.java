package com.javarush.games.game2048;

import com.javarush.engine.cell.*;

public class Game2048 extends Game {
    private static final int SIDE = 6;
    private int[][] gameField = new int[SIDE][SIDE];
    private boolean isGameStopped = false;
    private int score;

    @Override
    public void initialize() {
        setScreenSize(SIDE, SIDE);
        createGame();
        drawScene();
    }

    private void createGame() {
        setScore(score = 0);
        gameField = new int[SIDE][SIDE];
        createNewNumber();
        createNewNumber();
    }

    private void createNewNumber() {
        boolean isCreated = false;
        do {
            int x = getRandomNumber(SIDE);
            int y = getRandomNumber(SIDE);
            if (gameField[y][x] == 0) {
                gameField[y][x] = getRandomNumber(10) < 9 ? 2 : 4;
                isCreated = true;
            }
        }
        while (!isCreated);

        if (getMaxTileValue() == 2048) {
            win();
        }
    }

    private void drawScene() {
        for (int x = 0; x < SIDE; x++) {
            for (int y = 0; y < SIDE; y++) {
                setCellColoredNumber(x, y, gameField[y][x]);
            }
        }
    }

    private Color getColorByValue(int value) {
        switch (value) {
            case 0: return Color.WHITE;
            case 2: return Color.LIGHTPINK;
            case 4: return Color.BLUEVIOLET;
            case 8: return Color.BLUE;
            case 16: return Color.CYAN;
            case 32: return Color.LIGHTSEAGREEN;
            case 64: return Color.LIMEGREEN;
            case 128: return Color.ORANGE;
            case 256: return Color.INDIANRED;
            case 512: return Color.RED;
            case 1024: return Color.MAGENTA;
            case 2048: return Color.MEDIUMVIOLETRED;
            default: return Color.NONE;
        }
    }

    private void setCellColoredNumber(int x, int y, int value) {
        Color color = getColorByValue(value);
        String str = value > 0 ? "" + value : "";
        setCellValueEx(x, y, color, str);
    }

    private boolean compressRow(int[] row) {
        boolean isSorted = false;
        int buf = 0;

        for (int x = 0; x < row.length; x++) {
            if (row[x] > 0) {
                if (x != buf) {
                    row[buf] = row[x];
                    row[x] = 0;
                    isSorted = true;
                }
                buf++;
            }
        }
        return isSorted;
    }

    private boolean mergeRow(int[] row)
    {
        boolean isSorted = false;

        for (int x = 0; x < row.length - 1; x++)
        {
            if (row[x] > 0 && row[x] == row[x + 1])
            {
                row[x] += row[x + 1];
                score += row[x];
                setScore(score);
                row[x + 1] = 0;
                isSorted = true;
            }
        }
        return isSorted;
    }

    @Override
    public void onKeyPress(Key key)
    {
        if (isGameStopped) {
            if (key == Key.SPACE) {
                isGameStopped = false;
                createGame();
                drawScene();
            } else {
                return;
            }
        }

        if (!canUserMove()) {
            gameOver();
            return;
        }

        if (key == Key.LEFT) {
            moveLeft();
            drawScene();
        }
        else if (key == Key.RIGHT) {
            moveRight();
            drawScene();
        }
        else if (key == Key.UP) {
            moveUp();
            drawScene();
        }
        else if (key == Key.DOWN) {
            moveDown();
            drawScene();
        }
    }

    private void moveLeft() {
        boolean isCreate = false;

        for (int[] ints : gameField) {
            boolean merged = mergeRow(ints);
            boolean compressed = compressRow(ints);

            if (merged) compressRow(ints);
            if (merged || compressed) isCreate = true;
        }

        if (isCreate) createNewNumber();
    }

    private void moveRight() {
        rotateClockwise();
        rotateClockwise();
        moveLeft();
        rotateClockwise();
        rotateClockwise();
    }

    private void moveUp() {
        rotateClockwise();
        rotateClockwise();
        rotateClockwise();
        moveLeft();
        rotateClockwise();
    }

    private void moveDown() {
        rotateClockwise();
        moveLeft();
        rotateClockwise();
        rotateClockwise();
        rotateClockwise();
    }

    private void rotateClockwise() {
        int[][] result = new int[SIDE][SIDE];
        for (int i = 0; i < SIDE; i++) {
            for (int j = 0; j < SIDE; j++) {
                result[j][SIDE - 1 - i] = gameField[i][j];
            }
        }
        gameField = result;
    }

    private int getMaxTileValue() {
        int max = 0;
        for (int x = 0; x < gameField.length; x++) {
            for (int y = 0; y < gameField.length; y++) {
                if (gameField[y][x] > max) {
                    max = gameField[y][x];
                }
            }
        }
        return max;
    }

    private void win() {
        isGameStopped = true;
        showMessageDialog(Color.CORAL, "YOU WIN!", Color.BROWN, 40);
    }

    private boolean canUserMove() {
        for (int y = 0; y < SIDE; y++) {
            for (int x = 0; x < SIDE; x++) {
                if (gameField[y][x] == 0) {
                    return true;
                } else if (y < SIDE - 1 && gameField[y][x] == gameField[y + 1][x]) {
                    return true;
                } else if ((x < SIDE - 1) && gameField[y][x] == gameField[y][x + 1]) {
                    return true;
                }
            }
        }
        return false;
    }

    private void gameOver() {
        isGameStopped = true;
        showMessageDialog(Color.CORAL, "YOU LOSE!", Color.BROWN, 40);
    }
}

