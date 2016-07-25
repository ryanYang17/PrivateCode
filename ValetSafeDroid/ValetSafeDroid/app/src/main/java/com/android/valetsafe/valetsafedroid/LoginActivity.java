package com.android.valetsafe.valetsafedroid;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import com.android.valetsafe.valetsafedroid.PublicFunction;

import bean.CBCommonResult;
import bean.User;
import me.codeboy.common.base.log.CBPrint;
import me.codeboy.common.base.net.CBHttp;
import me.codeboy.common.base.net.constant.CBMethod;
import me.codeboy.common.base.net.core.CBConnection;
import service.NetworkService;

public class LoginActivity extends AppCompatActivity {

    public EditText textPersonNameEdit;//登录用户名
    public EditText textPasswordEdit;//登录用户密码
    public TextView textForgotPassword;//登录界面“忘记密码”
    public Button signInButton;
    public Button signUpButton;
    private Handler handler;
    private final static int REQUEST_CODE=1;
    private PublicFunction pub;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        pub = new PublicFunction();
        textPersonNameEdit = (EditText) findViewById(R.id.login_UsernameEdit);
        textPasswordEdit = (EditText) findViewById(R.id.login_PasswordEdit);
        textForgotPassword = (TextView) findViewById(R.id.login_Forgot_Passwords);
        signInButton = (Button) findViewById(R.id.login_btn_signin);
        signUpButton = (Button) findViewById(R.id.login_btn_signup);

        ButtonClickListener listener = new ButtonClickListener();
        signInButton.setOnClickListener(listener);
        signUpButton.setOnClickListener(listener);

        textForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgotPassword();
            }
        });

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if(msg.arg1 == 1){
                    CBCommonResult<User> result = (CBCommonResult<User>) msg.getData().get("result");
                    if(result.getCode() == 0){
                        User user = result.getData();
                        pub.writeTxtToFile(textPersonNameEdit.getText().toString(), "/valetsafe/", "login.txt");
                        pub.writeTxtToFile(textPasswordEdit.getText().toString(), "/valetsafe/", "login.txt");
                        pub.writeTxtToFile(String.valueOf(user.getId()), "/valetsafe/", "login.txt");
                        signup();
                    }
                    Toast.makeText(LoginActivity.this, result.getDescription(), Toast.LENGTH_SHORT).show();
                }
            }
        };
    }

    class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.login_btn_signup:
                    new Thread() {
                        @Override
                        public void run() {
                            String LoginMode = "";//按照电话校验为0，邮箱为1
                            String signUpName = textPersonNameEdit.getText().toString();
                            String signUpPwd = textPasswordEdit.getText().toString();
                            Log.i("tag", signUpName);
                            Log.i("tag", signUpPwd);
                            if(signUpName.equals("")){
                                Toast.makeText(LoginActivity.this, "userName cannot be null", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if(signUpPwd.equals("")) {
                                Toast.makeText(LoginActivity.this, "password cannot be null", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if (pub.ValidateCellphone(signUpName))
                                LoginMode = "0";
                            else if (pub.ValidateEmail(signUpName))
                                LoginMode = "1";
                            else{
                                Toast.makeText(LoginActivity.this, "Wrong Account Name", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            NetworkService service = new NetworkService();
                            CBCommonResult<User> result= service.LoginUserAction(signUpName, signUpPwd, LoginMode);
                            Message msg = new Message();
                            msg.arg1 = 1;
                            msg.getData().putSerializable("result", result);
                            handler.sendMessage(msg);

                        }
                    }.start();
                    break;
                case R.id.login_btn_signin:
                    Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                    //intent.putExtra("str", "Intent Demo");
                    startActivityForResult(intent, REQUEST_CODE);

                    break;
                default:
                    break;
            }
        }
    }

    private void signup(){
        Intent intent = new Intent(LoginActivity.this, NavMapActivity.class);
        //intent.putExtra("str", "Intent Demo");
        startActivity(intent);
    }

    private void forgotPassword(){}
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode==REQUEST_CODE)
        {
            if (resultCode==RegisterActivity.RESULT_CODE)
            {
                Bundle bundle=data.getExtras();
                String str=bundle.getString("back");
                Toast.makeText(LoginActivity.this, str, Toast.LENGTH_LONG).show();
                signup();
            }
        }
    }

}
