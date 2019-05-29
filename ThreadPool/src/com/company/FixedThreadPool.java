package com.company;

import java.util.concurrent.LinkedBlockingQueue;

public class FixedThreadPool implements ThreadPool {
    private final int numOfThreads;
    private final Worker[] threads;
    private final LinkedBlockingQueue<Runnable> tasks;


    FixedThreadPool(int numOfThreads) {
        this.numOfThreads = numOfThreads;

        threads = new Worker[numOfThreads];
        tasks = new LinkedBlockingQueue<>();
    }

    public void start() {
        for (int i = 0; i < numOfThreads; i++) {
            threads[i] = new Worker();
            threads[i].start();
        }
    }

    public void execute(Runnable runnable) {
        try {
            tasks.put(runnable);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public class Worker extends Thread {
        public void run() {
            Runnable curTask;

            while(true) {
                try {
                    curTask = tasks.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return;
                }

                try {
                    curTask.run();
                } catch (RuntimeException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}