package com.application.haominwu.threadpoolapplication.threadpool;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

public class MyThreadPool {

    private LinkedBlockingQueue<Runnable> toDoTasks = new LinkedBlockingQueue<>();

    private ConcurrentHashMap<MyThread, Boolean> threadInfo = new ConcurrentHashMap<>();

    private int limitNumberOfThread;

    public MyThreadPool(int numbersOfThreads) {
        this.limitNumberOfThread = numbersOfThreads;
    }

    public void addTask(Runnable runnable) {
        if (threadInfo.size() < this.limitNumberOfThread) {
            MyThread myThread = new MyThread(toDoTasks, runnable);
            threadInfo.put(myThread, true);
            myThread.start();
        } else {
            toDoTasks.add(runnable);
        }
    }


    public void shutDown() {
        for (MyThread myThread : threadInfo.keySet()) {
            myThread.shutdown();
        }
        threadInfo.clear();
        toDoTasks.clear();
    }
}
