package com.company.test;

import com.company.ScalableThreadPool;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.*;

public class ScalableThreadPoolTest {

    @Test
    public void test(){
        List<Runnable> workers = new ArrayList<>();

        for(int i=0; i < 100; i++) {
            int finalI = i;
            Runnable runnable = () -> {

                try {
                    Thread.sleep(1000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Runnable running " + finalI + " " + Thread.currentThread());};
            workers.add(runnable);
        }

        ScalableThreadPool threadPool = new ScalableThreadPool(10, 55);
        threadPool.start();
        System.out.println("Done");

        workers.forEach(c -> {
            ThreadLocalRandom.current().nextInt(20, 1000 + 1);
            threadPool.execute(c);
        });
    }

}