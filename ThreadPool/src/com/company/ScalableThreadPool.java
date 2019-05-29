package com.company;

import java.util.HashSet;
import java.util.concurrent.LinkedBlockingQueue;

public class ScalableThreadPool implements ThreadPool {
    private final int minNumOfThreads;
    private final int maxNumOfthreads;
    private final HashSet<Worker> threads;
    private final LinkedBlockingQueue<Runnable> tasks;


    ScalableThreadPool(int minNumOfThreads, int maxNumOfthreads) {
        this.minNumOfThreads = minNumOfThreads;
        this.maxNumOfthreads = maxNumOfthreads;

        threads = new HashSet<>(minNumOfThreads);
        tasks = new LinkedBlockingQueue<>();
    }

    public void start() {
        for (int i = 0; i < minNumOfThreads; i++) {
            Worker w = new Worker();
            threads.add(w);
            w.start();
        }
    }

    public void execute(Runnable runnable) {
        synchronized (threads) {
            if (!tasks.isEmpty() &&
                    threads.size() < maxNumOfthreads) {
                Worker extraWorker = new Worker();
                extraWorker.start();
                threads.add(extraWorker);
            }
        }
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
                synchronized (threads) {
                    if (tasks.isEmpty() && threads.size() > minNumOfThreads) {
                        threads.remove(this);
                    }
                }

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