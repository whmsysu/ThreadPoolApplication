package com.application.haominwu.threadpoolapplication.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.application.haominwu.threadpoolapplication.R;
import com.application.haominwu.threadpoolapplication.threadpool.MyThreadPool;
import com.orhanobut.logger.Logger;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private MyThreadPool myThreadPool = new MyThreadPool(5);

    private static class MyRunnable implements Runnable {

        private int taskID;

        public MyRunnable(Context context, int taskID) {
            this.taskID = taskID;
        }

        @Override
        public void run() {
            Logger.d(String.format("Task %d", taskID));
        }
    }


    @OnClick(R.id.btn_post_runnable)
    public void onPostRunnableBtnClick() {
        if (myThreadPool != null) {
            for (int i = 0; i < 10; i++) {
                myThreadPool.addTask(new MyRunnable(this, i));
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myThreadPool.shutDown();
    }
}
