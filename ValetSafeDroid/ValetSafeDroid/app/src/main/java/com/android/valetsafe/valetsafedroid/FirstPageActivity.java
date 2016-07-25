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

import bean.CBCommonResult;
import bean.User;
import me.codeboy.common.base.log.CBPrint;
import me.codeboy.common.base.net.CBHttp;
import me.codeboy.common.base.net.constant.CBMethod;
import me.codeboy.common.base.net.core.CBConnection;
import service.NetworkService;

/**
 * 首页
 *
 * author lhy
 *
 */
public class FirstPageActivity extends AppCompatActivity {
    private final  static int AUTO_LOGIN = 1;
    private Timer timer;
    private int delayTime = 2000;//延迟跳转时间，ms为单位
    private Handler handler1;
    private String LogText = "/valetsafe/login.txt";
    private PublicFunction pub;
    private boolean CanLoad = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_page_layout);
        pub = new PublicFunction();
        timer = new Timer(true);
        timer.schedule(task, delayTime); //延时1000ms后执行，1000ms执行一次
        ////退出计时器
        handler1 = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if(msg.arg1 == 1){
                    CBCommonResult<User> result = (CBCommonResult<User>) msg.getData().get("result");
                    if(result.getCode() == 0){
                        CanLoad = true;
                    }
                    Toast.makeText(FirstPageActivity.this, result.getDescription(), Toast.LENGTH_SHORT).show();
                }
            }
        };
    }
    /*
        加载登录文件
           若登陆成功，返回true
           失败，false
     */
    private boolean load() {
        if (pub.fileIsExists(LogText)){
            new Thread() {
                @Override
                public void run() {
                    try {
                        String LoginMode = "";//按照电话校验为0，邮箱为1
                        File urlFile = new File(LogText);
                        InputStreamReader isr = new InputStreamReader(new FileInputStream(urlFile), "UTF-8");
                        BufferedReader br = new BufferedReader(isr);

                        String signUpName = br.readLine();
                        String signUpPwd = br.readLine();
                        if (pub.ValidateCellphone(signUpName))
                            LoginMode = "0";
                        else if (pub.ValidateEmail(signUpName))
                            LoginMode = "1";
                        else {
                            CanLoad = false;
                            return;
                        }
                        NetworkService service = new NetworkService();
                        CBCommonResult<User> result= service.LoginUserAction(signUpName, signUpPwd, LoginMode);
                        Message msg = new Message();
                        msg.arg1 = 1;
                        msg.getData().putSerializable("result", result);
                        handler1.sendMessage(msg);
                    } catch (IOException e) {
                        CanLoad = false;
                        e.printStackTrace();
                    }
                }
            }.start();
            return CanLoad;
        }
        else {
            return false;
        }
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
