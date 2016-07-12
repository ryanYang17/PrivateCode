package com.android.valetsafe.valetsafedroid;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Map;

import service.NetworkService;

/**
 * 司机端设置界面
 *
 * author lhy
 */
public class SettingDriverActivity extends AppCompatActivity {
    public final static int RESULT_CODE=1;
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
        setContentView(R.layout.setting_driver_layout);
        backBtn = (ImageView) findViewById(R.id.setting_driver_back_btn);
        ModifyName = (ImageView) findViewById(R.id.setting_driver_name_img);
        ModifyCellphone = (ImageView) findViewById(R.id.setting_driver_phone_img);
        ModifyEmail = (ImageView) findViewById(R.id.setting_driver_email_img);
        NameText = (EditText) findViewById(R.id.setting_driver_name_edit);
        PhoneText = (EditText) findViewById(R.id.setting_driver_phone_edit);
        EmailText = (EditText) findViewById(R.id.setting_driver_email_edit);

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

                Toast.makeText(SettingDriverActivity.this, "abc" + msg.getData().getString("result"), Toast.LENGTH_SHORT).show();
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
                case R.id.setting_driver_name_img:
                case R.id.setting_driver_phone_img:
                case R.id.setting_driver_email_img:
                    new Thread() {
                        @Override
                        public void run() {
                            String name = NameText.getText().toString();
                            String cell_phone = PhoneText.getText().toString();
                            String email = EmailText.getText().toString();
                            PublicFunction valid = new PublicFunction();
                            if (!valid.ValidateUserName(name))
                            {
                                Message message = new Message();
                                message.what = NameEditError;
                                handler.sendMessage(message);
                            }
                            else if (!valid.ValidateCellphone(cell_phone))
                            {
                                Message message = new Message();
                                message.what = CellphoneEditError;
                                handler.sendMessage(message);
                            }
                            else if (!valid.ValidateEmail(email))
                            {
                                Message message = new Message();
                                message.what = EmailEditError;
                                handler.sendMessage(message);
                            }
                            else
                            {
                                //调用网络服务进行注册用户操作
                                NetworkService service = new NetworkService();
                                String ModifyNum;
                                if (v.getId() == R.id.setting_driver_name_img)
                                    ModifyNum = "1";
                                else if (v.getId() == R.id.setting_driver_phone_img)
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

    private void onEnd(){
        Intent intent=new Intent();
        intent.putExtra("back", "Back Data");
        setResult(RESULT_CODE, intent);
        finish();
    }
}
