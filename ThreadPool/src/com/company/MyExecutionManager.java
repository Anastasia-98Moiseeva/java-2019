package com.company;

public class MyExecutionManager implements ExecutionManager {

    @Override
    public Contex execute(Runnable callback, Runnable... tasks){
        return new MyContext(callback, tasks);
    }
}