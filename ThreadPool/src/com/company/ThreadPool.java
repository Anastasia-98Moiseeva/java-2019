package com.company;

public interface ThreadPool {
    void start();
    void execute(Runnable runnable);
}