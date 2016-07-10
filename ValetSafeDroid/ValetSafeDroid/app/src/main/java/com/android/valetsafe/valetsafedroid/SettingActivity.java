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

import java.util.Map;

import com.android.valetsafe.valetsafedroid.PublicFunction;

import service.NetworkService;

public class SettingActivity extends AppCompatActivity {
    public final static int RESULT_CODE = 1;
    private final static int NameEditError = 1000;
    private final static int CellphoneEditError = 1001;
    private final static int EmailEditError = 1002;
    private ImageView backBtn;
    private ImageView ModifyName;
    private ImageView ModifyCellphone;
    private ImageView ModifyEmail;
    private EditText NameText;
    private EditText PhoneText;
    private EditText EmailText;
    private Handler handler;

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

                switch (msg.what) {
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


                Toast.makeText(SettingActivity.this, "abc" + msg.getData().getString("result"), Toast.LENGTH_SHORT).show();
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
                case R.id.setting_cellphone_change:
                case R.id.setting_email_change:
                    new Thread() {
                        @Override
                        public void run() {
                            String name = NameText.getText().toString();
                            String cell_phone = PhoneText.getText().toString();
                            String email = EmailText.getText().toString();
                            PublicFunction valid = new PublicFunction();
                            if (!valid.ValidateUserName(name)) {
                                Message message = new Message();
                                message.what = NameEditError;
                                handler.sendMessage(message);
                            } else if (!valid.ValidateCellphone(cell_phone)) {
                                Message message = new Message();
                                message.what = CellphoneEditError;
                                handler.sendMessage(message);
                            } else if (!valid.ValidateEmail(email)) {
                                Message message = new Message();
                                message.what = EmailEditError;
                                handler.sendMessage(message);
                            } else {
                                //调用网络服务进行注册用户操作
                                NetworkService service = new NetworkService();
                                String ModifyNum;
                                if (v.getId() == R.id.setting_name_change)
                                    ModifyNum = "1";
                                else if (v.getId() == R.id.setting_cellphone_change)
                                    ModifyNum = "2";
                                else
                                    ModifyNum = "3";
                                Map<String, String> data = service.SetPassengerSetting(name, cell_phone, email, ModifyNum);
                                String result = data.get("result");
                                Message msg = new Message();
                                msg.arg1 = 0;
                                msg.getData().putString("result", result);
                                handler.sendMessage(msg);
                                onEnd();
                            }
                        }
                    }.start();
                    break;
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
