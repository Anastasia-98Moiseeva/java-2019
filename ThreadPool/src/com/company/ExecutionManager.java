package com.company;

public interface ExecutionManager {
    Contex execute(Runnable callback, Runnable... tasks);
}