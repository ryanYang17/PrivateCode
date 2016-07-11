package com.android.valetsafe.valetsafedroid;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

public class FirstPageActivity extends AppCompatActivity {
    private final  static int AUTO_LOGIN = 1;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_page_layout);

        timer = new Timer(true);
        timer.schedule(task, 4000); //延时1000ms后执行，1000ms执行一次
        ////退出计时器
    }

    /*
        加载登录文件

           若登陆成功，返回true
           失败，false
     */
    private boolean load() {

        //  添加加载函数

        return true;
    }

    private void work() {
        if (load()) {
            Intent intent = new Intent(FirstPageActivity.this, NavMapActivity.class);
            //intent.putExtra("str", "Intent Demo");
            startActivity(intent);
        } else {
            Intent intent = new Intent(FirstPageActivity.this, LoginActivity.class);
            startActivity(intent);
        }

    }

    TimerTask task = new TimerTask() {
        public void run() {
            Message message = new Message();
            message.what = AUTO_LOGIN;
            handler.sendMessage(message);

        }
    };

    final Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case AUTO_LOGIN:
                    work();
                    break;
            }
            super.handleMessage(msg);
        }
    };
}
