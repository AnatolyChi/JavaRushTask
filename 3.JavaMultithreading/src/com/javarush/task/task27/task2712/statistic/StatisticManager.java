package com.javarush.task.task27.task2712.statistic;

import com.javarush.task.task27.task2712.kitchen.Cook;
import com.javarush.task.task27.task2712.statistic.event.EventDataRow;
import com.javarush.task.task27.task2712.statistic.event.EventType;

import java.util.*;

//регистрация событий в хранилище
public class StatisticManager {
    private StatisticStorage statisticStorage = new StatisticStorage();
    private Set<Cook> cooks = new HashSet<>();

    private static class InstanceHolder {
        private static final StatisticManager ourInstance = new StatisticManager();


    }

    public static StatisticManager getInstance() {
        return InstanceHolder.ourInstance;
    }

    private StatisticManager() {

    }

    private class StatisticStorage {
        public Map<EventType, List<EventDataRow>> storage = new HashMap<>();

        public StatisticStorage() {
            for (EventType type : EventType.values()) {
                storage.put(type, new ArrayList<>());
            }
        }

        private void put(EventDataRow data) {
            storage.get(data.getType()).add(data);
        }
    }

    public void register(EventDataRow data) {
        statisticStorage.put(data);
    }

    public void register(Cook cook) {
        cooks.add(cook);
    }
}
