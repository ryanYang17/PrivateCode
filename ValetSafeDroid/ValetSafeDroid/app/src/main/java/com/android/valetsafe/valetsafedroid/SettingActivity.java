package com.android.valetsafe.valetsafedroid;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewDebug;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

import com.android.valetsafe.valetsafedroid.PublicFunction;

import bean.CBCommonResult;
import bean.User;
import service.NetworkService;

/**
 * 乘客端设置界面
 *
 * author lhy
 *
 *
 */
public class SettingActivity extends AppCompatActivity {
    public final static int RESULT_CODE = 1;
    private final static int NameEditError = 1000;
    private final static int CellphoneEditError = 1001;
    private final static int EmailEditError = 1002;
    private ImageView backBtn;//返回按钮
    private ImageView ModifyName;//修改姓名
    private ImageView ModifyCellphone;//修改电话
    private ImageView ModifyEmail;//修改邮箱
    private EditText NameText;//姓名
    private EditText PhoneText;//电话
    private EditText EmailText;//邮箱
    private Handler handler;
    private PublicFunction p;
    private String LogText = "/valetsafe/login.txt";
    private String UserID = "";
    private NetworkService service;
    private String ModifyText = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_layout);
        backBtn = (ImageView) findViewById(R.id.setting_back_btn);
        ModifyName = (ImageView) findViewById(R.id.setting_name_change);
        ModifyCellphone = (ImageView) findViewById(R.id.setting_cellphone_change);
        ModifyEmail = (ImageView) findViewById(R.id.setting_email_change);
        NameText = (EditText) findViewById(R.id.setting_name_edit);
        PhoneText = (EditText) findViewById(R.id.setting_cellphone_edit);
        EmailText = (EditText) findViewById(R.id.setting_email_edit);
        service = new NetworkService();
        try {
            p = new PublicFunction();
            File urlFile = new File(LogText);
            InputStreamReader isr = new InputStreamReader(new FileInputStream(urlFile), "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            br.readLine();
            br.readLine();
            UserID = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        new Thread() {
            @Override
            public void run() {
                CBCommonResult<User> result = service.loadUser(Integer.parseInt(UserID));
                Message msg = new Message();
                msg.arg1 = 0;
                msg.getData().putSerializable("result", result);
                handler.sendMessage(msg);
            }
        }.start();
        ModifyName.setOnClickListener(new ButtonClickListener());
        ModifyCellphone.setOnClickListener(new ButtonClickListener());
        ModifyEmail.setOnClickListener(new ButtonClickListener());

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.arg1) {
                    case 0: {
                        CBCommonResult<User> result = (CBCommonResult<User>) msg.getData().get("result");
                        if (result.getCode() == 0) {
                            User user = result.getData();
                            NameText.setText(user.getName());
                            PhoneText.setText(user.getCell_phone());
                            EmailText.setText(user.getEmail());
                            Toast.makeText(SettingActivity.this, String.valueOf(user.getId()), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(SettingActivity.this, result.getDescription(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    case 1: {
                        CBCommonResult<String> result = (CBCommonResult<String>) msg.getData().get("result");
                        if(result.getCode() == 0){
                            Toast.makeText(SettingActivity.this, result.getDescription(), Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(SettingActivity.this, result.getDescription(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    case NameEditError:
                        showNameEditError();
                        break;
                    case CellphoneEditError:
                        showCellphoneEditError();
                        break;
                    case EmailEditError:
                        showEmailEditError();
                        break;
                }
                super.handleMessage(msg);
            }
        };
    }


    private void showNameEditError() {
        NameText.setError("user name can't be empty!");
        NameText.setText("");
    }

    private void showCellphoneEditError() {
        PhoneText.setError("Invalid cellphone number!");
        PhoneText.setText("");
    }

    private void showEmailEditError() {
        EmailText.setError("Invalid Email address!");
        EmailText.setText("");
    }

    class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(final View v) {
            switch (v.getId()) {
                case R.id.setting_name_change:
                {
                    new Thread() {
                        @Override
                        public void run() {
                            ModifyText = NameText.getText().toString();
                            if (!p.ValidateUserName(ModifyText)) {
                                Message message = new Message();
                                message.arg1 = NameEditError;
                                handler.sendMessage(message);
                            } else {
                                //调用网络服务进行注册用户操作
                                String ModifyNum = "1";
                                CBCommonResult<String> result = service.SetPassengerSetting(ModifyText, UserID, ModifyNum);
                                Message msg = new Message();
                                msg.arg1 = 1;
                                msg.getData().putSerializable("result", result);
                                handler.sendMessage(msg);
                                onEnd();
                            }
                        }
                    }.start();
                    break;
                }
                case R.id.setting_cellphone_change:
                {
                    new Thread() {
                        @Override
                        public void run() {
                            ModifyText = PhoneText.getText().toString();
                            if (!p.ValidateCellphone(ModifyText)) {
                                Message message = new Message();
                                message.arg1 = CellphoneEditError;
                                handler.sendMessage(message);
                            } else {
                                //调用网络服务进行注册用户操作
                                String ModifyNum = "2";
                                CBCommonResult<String> result = service.SetPassengerSetting(ModifyText, UserID, ModifyNum);
                                Message msg = new Message();
                                msg.arg1 = 1;
                                msg.getData().putSerializable("result", result);
                                handler.sendMessage(msg);
                                onEnd();
                            }
                        }
                    }.start();
                    break;
                }
                case R.id.setting_email_change:
                {
                    new Thread() {
                        @Override
                        public void run() {
                            ModifyText = EmailText.getText().toString();
                            if (!p.ValidateEmail(ModifyText)) {
                                Message message = new Message();
                                message.arg1 = EmailEditError;
                                handler.sendMessage(message);
                            } else {
                                //调用网络服务进行注册用户操作
                                String ModifyNum = "3";
                                CBCommonResult<String> result = service.SetPassengerSetting(ModifyText, UserID, ModifyNum);
                                Message msg = new Message();
                                msg.arg1 = 1;
                                msg.getData().putSerializable("result", result);
                                handler.sendMessage(msg);
                                onEnd();
                            }
                        }
                    }.start();
                    break;
                }
                case R.id.setting_signout_btn:
                {
                    Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
                    startActivity(intent);
                    p.DeleteFile(LogText);
                }
            }
        }
    }

    private void onEnd() {
        Intent intent = new Intent();
        intent.putExtra("back", "Back Data");
        setResult(RESULT_CODE, intent);
        finish();
    }
}
