package com.application.haominwu.threadpoolapplication.threadpool;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class MyThread extends Thread {

    private Runnable runnable;

    private AtomicBoolean shouldStop = new AtomicBoolean(false);

    private LinkedBlockingQueue<Runnable> toDoTasks;

    public MyThread(LinkedBlockingQueue<Runnable> toDoTasks, Runnable runnable) {
        this.toDoTasks = toDoTasks;
        this.runnable = runnable;
    }

    @Override
    public void run() {
        super.run();
        while (!shouldStop.get()) {
            if (this.runnable != null) {
                this.runnable.run();
                this.runnable = null;
            } else {
                try {
                    this.runnable = toDoTasks.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void shutdown() {
        shouldStop.compareAndSet(false, true);
    }
}