package com.javarush.task.task35.task3513;

import java.util.*;

public class Model {
    private static final int FIELD_WIDTH = 4;
    private Tile[][] gameTiles;
    public int score;
    public int maxTile;

    private Stack previousStates = new Stack();
    private Stack previousScores = new Stack();
    private boolean isSaveNeeded = true;

    public Model() {
        resetGameTiles();
    }

    public Tile[][] getGameTiles() {
        return gameTiles;
    }

    void resetGameTiles() {
        gameTiles = new Tile[FIELD_WIDTH][FIELD_WIDTH];
        for (int i = 0; i < FIELD_WIDTH; i++) {
            for (int j = 0; j < FIELD_WIDTH; j++) {
                gameTiles[i][j] = new Tile();
            }
        }
        addTile();
        addTile();
    }

    private void addTile() {
        List<Tile> emptyTiles = getEmptyTiles();
        if (!emptyTiles.isEmpty()) {
            int index = (int) (Math.random() * emptyTiles.size()) % emptyTiles.size();
            Tile emptyTile = emptyTiles.get(index);
            emptyTile.value = Math.random() < 0.9 ? 2 : 4;
        }
    }

    private List<Tile> getEmptyTiles() {
        List<Tile> emptyTilesList = new ArrayList<>();
        for (Tile[] gameTile : gameTiles) {
            for (Tile tile : gameTile) {
                if (tile.isEmpty()) {
                    emptyTilesList.add(tile);
                }
            }
        }
        return emptyTilesList;
    }

    private boolean compressTiles(Tile[] tiles) {
        int buf = 0; //запоминает ячейку в которую нужно закинуть правое старшее значение
        boolean isSorted = false;

        for (int x = 0; x < tiles.length; x++) {
            if (tiles[x].value > 0) {
                if (x != buf) {
                    tiles[buf].value = tiles[x].value;
                    tiles[x].value = 0;
                    isSorted = true;
                }
                buf++;
            }
        }

        return isSorted;
    }

    private boolean mergeTiles(Tile[] tiles) {
        boolean isSorted = false;
        int buf = 0;

        for (int x = 0; x < tiles.length - 1; x++) {
            if (tiles[x].value > 0 &&
                    tiles[x].value == tiles[x + 1].value) {
                tiles[x].value += tiles[x + 1].value;
                tiles[x + 1].value = 0;
                score += tiles[x].value;
                isSorted = true;
                if (maxTile < tiles[x].value) {
                    maxTile = tiles[x].value;
                }
            }
        }

        for (int x = 0; x < tiles.length; x++) {
            if (tiles[x].value > 0) {
                if (x != buf) {
                    tiles[buf].value = tiles[x].value;
                    tiles[x].value = 0;
                }
                buf++;
            }
        }

        return isSorted;
    }

    public void left() {
        if (isSaveNeeded) {
            saveState(gameTiles);
        }
        boolean moveFlag = false;
        for (int i = 0; i < FIELD_WIDTH; i++) {
            if (compressTiles(gameTiles[i]) | mergeTiles(gameTiles[i])) {
                moveFlag = true;
            }
        }
        if (moveFlag) {
            addTile();
        }
        isSaveNeeded = true;
    }

    public void rotate() {
        Tile[][] tiles = new Tile[gameTiles.length][gameTiles[0].length];

        for (int i = 0; i < gameTiles.length; i++) {
            for (int j = 0; j < gameTiles[i].length; j++) {
                tiles[j][gameTiles[i].length - i - 1] = gameTiles[i][j];
            }
        }

        gameTiles = tiles;
    }

    public void right() {
        saveState(gameTiles);
        rotate();
        rotate();
        left();
        rotate();
        rotate();
    }

    public void up() {
        saveState(gameTiles);
        rotate();
        rotate();
        rotate();
        left();
        rotate();
    }

    public void down() {
        saveState(gameTiles);
        rotate();
        left();
        rotate();
        rotate();
        rotate();
    }

    public boolean canMove() {
        for (int y = 0; y < FIELD_WIDTH; y++) {
            for (int x = 0; x < FIELD_WIDTH; x++) {
                if (gameTiles[y][x].value == 0) {
                    return true;
                } else if (y < FIELD_WIDTH - 1 &&
                        gameTiles[y][x].value == gameTiles[y + 1][x].value) {
                    return true;
                } else if ((x < FIELD_WIDTH - 1) &&
                        gameTiles[y][x].value == gameTiles[y][x + 1].value) {
                    return true;
                }
            }
        }
        return false;
    }

    private void saveState(Tile[][] tiles) {
        Tile[][] tempTiles = new Tile[FIELD_WIDTH][FIELD_WIDTH];
        for (int i = 0; i < FIELD_WIDTH; i++) {
            for (int j = 0; j < FIELD_WIDTH; j++) {
                tempTiles[i][j] = new Tile(tiles[i][j].value);
            }
        }
        previousStates.push(tempTiles);
        previousScores.push(score);
        isSaveNeeded = false;
    }

    public void rollback() {
        if (!previousScores.isEmpty() && !previousStates.isEmpty()) {
            gameTiles = (Tile[][]) previousStates.pop();
            score = (int) previousScores.pop();
        }
    }

    public void randomMove() {
        int n = (int) (Math.random() * 100 % 4);

        switch (n) {
            case 0:
                left();
                break;
            case 1:
                right();
                break;
            case 2:
                up();
                break;
            case 3:
                down();
                break;
        }
    }

    public boolean hasBoardChanged() {
        Tile[][] tiles = (Tile[][]) previousStates.peek();

        for (int i = 0; i < FIELD_WIDTH; i++) {
            for (int j = 0; j < FIELD_WIDTH; j++) {
                if (tiles[i][j].value != gameTiles[i][j].value) {
                    return true;
                }
            }
        }

        return false;
    }

    public MoveEfficiency getMoveEfficiency(Move move) {
        move.move();
        MoveEfficiency moveEfficiency = new MoveEfficiency(getEmptyTiles().size(), score, move);

        if (!hasBoardChanged()) {
            return new MoveEfficiency(-1, 0, move);
        }
        rollback();

        return moveEfficiency;
    }

    public void autoMove() {
        PriorityQueue<MoveEfficiency> priorityQueue = new PriorityQueue<>(4, Collections.reverseOrder());
        priorityQueue.offer(getMoveEfficiency(this::left));
        priorityQueue.offer(getMoveEfficiency(this::right));
        priorityQueue.offer(getMoveEfficiency(this::up));
        priorityQueue.offer(getMoveEfficiency(this::down));

        Objects.requireNonNull(priorityQueue.peek()).getMove().move();
    }
}
