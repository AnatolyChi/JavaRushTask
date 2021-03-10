package com.javarush.task.task27.task2712.ad;

public class Advertisement {
    private Object content;
    private String name;
    private long initialAmount;
    private int hits;
    private int duration;
    private long amountPerOneDisplaying;

    public Advertisement(Object content, String name, long initialAmount, int hits, int duration) {
        this.content = content;
        this.name = name;
        this.initialAmount = initialAmount;
        this.hits = hits;
        this.duration = duration;
        try {
            amountPerOneDisplaying = initialAmount / hits;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void revalidate() {
        if (hits == 0) {
            throw new UnsupportedOperationException();
        }
        hits--;
    }

    public int getDuration() {
        return duration;
    }

    public long getAmountPerOneDisplaying() {
        return amountPerOneDisplaying;
    }

    public String getName() {
        return name;
    }

    public boolean isActive() {
        return hits > 0;
    }
}
