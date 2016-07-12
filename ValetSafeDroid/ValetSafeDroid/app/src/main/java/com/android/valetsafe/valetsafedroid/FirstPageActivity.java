package com.android.valetsafe.valetsafedroid;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.valetsafe.valetsafedroid.PublicFunction;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import me.codeboy.common.base.log.CBPrint;
import me.codeboy.common.base.net.CBHttp;
import me.codeboy.common.base.net.constant.CBMethod;
import me.codeboy.common.base.net.core.CBConnection;

public class FirstPageActivity extends AppCompatActivity {
    private final  static int AUTO_LOGIN = 1;
    private Timer timer;
    private int delayTime = 2000;//延迟跳转时间，ms为单位
    private Handler handler1;
    private String LogText = "/valetsafe/loginagain.txt";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_page_layout);

        timer = new Timer(true);
        timer.schedule(task, delayTime); //延时1000ms后执行，1000ms执行一次
        ////退出计时器
        handler1 = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Toast.makeText(FirstPageActivity.this, "abc" + msg.getData().getString("result"), Toast.LENGTH_SHORT).show();
                super.handleMessage(msg);
            }
        };
    }

    /*
        加载登录文件

           若登陆成功，返回true
           失败，false
     */
    private boolean load() {
        PublicFunction pub = new PublicFunction();
        if (pub.fileIsExists(LogText)){
            new Thread() {
                @Override
                public void run() {
                    try {
                        File urlFile = new File(LogText);
                        InputStreamReader isr = new InputStreamReader(new FileInputStream(urlFile), "UTF-8");
                        BufferedReader br = new BufferedReader(isr);

                        String signUpName = br.readLine();
                        String signUpPwd = br.readLine();
                        String baseURL = "http://47.88.192.36:8080/valetsafe/addSignUpUser";
                        CBConnection connection = CBHttp.getInstance();
                        CBPrint.println(baseURL);
                        String result = null;
                        Map<String,String> signUpData = new HashMap<String,String>();
                        signUpData.put("name", signUpName);
                        signUpData.put("pwd", signUpPwd);

                        result = connection.connect(baseURL).method(CBMethod.POST).timeout(5000).data(signUpData).execute();
                        Message msg = new Message();
                        msg.arg1 = 0;
                        msg.getData().putString("result", result);
                        handler1.sendMessage(msg);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }
        //  添加加载函数

        return false;
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
