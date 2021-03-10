package com.javarush.task.task28.task2802;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/* 
Пишем свою ThreadFactory
*/

/*
Outputs:

secondGroup-pool-2-thread-1
firstGroup-pool-1-thread-1
firstGroup-pool-1-thread-3
secondGroup-pool-2-thread-3
firstGroup-pool-1-thread-2
secondGroup-pool-2-thread-2

 */

public class Solution {

    public static void main(String[] args) {
        class EmulatorThreadFactoryTask implements Runnable {
            @Override
            public void run() {
                emulateThreadFactory();
            }
        }

        ThreadGroup group = new ThreadGroup("firstGroup");
        Thread thread = new Thread(group, new EmulatorThreadFactoryTask());

        ThreadGroup group2 = new ThreadGroup("secondGroup");
        Thread thread2 = new Thread(group2, new EmulatorThreadFactoryTask());

        thread.start();
        thread2.start();
    }

    private static void emulateThreadFactory() {
        AmigoThreadFactory factory = new AmigoThreadFactory();
        Runnable r = new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
            }
        };
        factory.newThread(r).start();
        factory.newThread(r).start();
        factory.newThread(r).start();
    }

    public static class AmigoThreadFactory implements ThreadFactory {
        private final ThreadGroup group;
        private static final AtomicInteger poolNum = new AtomicInteger(1);
        private final AtomicInteger threadNum = new AtomicInteger(1);
        private final int counterPoolNum;

        public AmigoThreadFactory() {
            group = Thread.currentThread().getThreadGroup();
            counterPoolNum = poolNum.getAndIncrement();
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(group, r);
            thread.setName(group.getName() + "-pool-" + counterPoolNum + "-thread-" + threadNum.getAndIncrement());
            thread.setDaemon(false);
            thread.setPriority(Thread.NORM_PRIORITY);
            return thread;
        }
    }
}
