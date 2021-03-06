package com.javarush.task.task35.task3513;

public class MoveEfficiency implements Comparable<MoveEfficiency> {
    private int score;
    private int numberOfEmptyTiles;
    private Move move;

    public MoveEfficiency(int numberOfEmptyTiles, int score,  Move move) {
        this.score = score;
        this.numberOfEmptyTiles = numberOfEmptyTiles;
        this.move = move;
    }

    public Move getMove() {
        return move;
    }

    @Override
    public int compareTo(MoveEfficiency o) {
        if (this.numberOfEmptyTiles < o.numberOfEmptyTiles) {
            return -1;
        } else if (this.numberOfEmptyTiles > o.numberOfEmptyTiles) {
            return 1;
        } else {
            if (this.score < o.score) {
                return -1;
            } else if (this.score > o.score) {
                return 1;
            }
        }

        return 0;
    }
}
