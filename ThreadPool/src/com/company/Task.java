package com.company;

import java.util.concurrent.Callable;

public class Task<T> {

    private volatile Boolean finished = false;
    private T result = null;
    private Callable<? extends T> callable;
    private MyTaskException exception = null;

    public Task(Callable<? extends T> callable) {
        this.callable = callable;
    }

    public T get() throws MyTaskException {
        if (!finished) {

            synchronized (this) {

                if (!finished) {

                    try {
                        result = callable.call();
                    } catch (Exception e) {
                        exception = new MyTaskException();
                        finished = true;
                        throw exception;
                    }
                    finished = true;
                    finished.notifyAll();
                }
            }
        }
        if (exception != null) {
            throw exception;
        }
        return result;
    }
}