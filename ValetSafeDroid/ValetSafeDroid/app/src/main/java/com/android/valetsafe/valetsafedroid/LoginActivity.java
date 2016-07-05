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
import android.widget.Toast;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import me.codeboy.common.base.log.CBPrint;
import me.codeboy.common.base.net.CBHttp;
import me.codeboy.common.base.net.constant.CBMethod;
import me.codeboy.common.base.net.core.CBConnection;

public class LoginActivity extends AppCompatActivity {

    public EditText textPersonNameEdit;
    public EditText textPasswordEdit;
    public Button signInButton;
    public Button signUpButton;
    private Handler handler;
    private final static int REQUEST_CODE=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        textPersonNameEdit = (EditText) findViewById(R.id.login_UsernameEdit);
        textPasswordEdit = (EditText) findViewById(R.id.login_PasswordEdit);
        signInButton = (Button) findViewById(R.id.login_btn_signin);
        signUpButton = (Button) findViewById(R.id.login_btn_signup);

        ButtonClickListener listener = new ButtonClickListener();
        signInButton.setOnClickListener(listener);
        signUpButton.setOnClickListener(listener);

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Toast.makeText(LoginActivity.this, "abc" + msg.getData().getString("result"), Toast.LENGTH_SHORT).show();
                super.handleMessage(msg);
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
                            try {

                                String signUpName = textPersonNameEdit.getText().toString();
                                String signUpPwd = textPasswordEdit.getText().toString();
                                Log.i("tag", signUpName);
                                Log.i("tag", signUpPwd);
                                if(signUpName.equals("")){
                                    Toast.makeText(LoginActivity.this, "userName cannot be null", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                if(signUpPwd.equals("")){
                                    Toast.makeText(LoginActivity.this, "password cannot be null", Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                String baseURL = "http://47.88.192.36:8080/valetsafe/addSignUpUser";
                                //String baseURL = "http://192.168.1.106:8080/test/addSignUpUser";
                                CBConnection connection = CBHttp.getInstance();
                                CBPrint.println(baseURL);
                                String result = null;

                                Map<String,String> signUpData = new HashMap<String,String>();
                                signUpData.put("name", signUpName);
                                signUpData.put("pwd", signUpPwd);

                                result = connection.connect(baseURL).method(CBMethod.POST).timeout(5000).data(signUpData).execute();

                                CBPrint.println("2" + result);
                                Message msg = new Message();
                                msg.arg1 = 0;
                                msg.getData().putString("result", result);
                                handler.sendMessage(msg);
                                signup();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
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
