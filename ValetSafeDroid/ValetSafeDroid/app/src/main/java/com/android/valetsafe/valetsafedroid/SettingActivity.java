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
    public final static int RESULT_CODE=1;
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
        ModifyName = (ImageView) findViewById(R.id.imageView11);
        ModifyCellphone = (ImageView) findViewById(R.id.imageView12);
        ModifyEmail = (ImageView) findViewById(R.id.imageView13);
        NameText = (EditText) findViewById(R.id.editText4);
        PhoneText = (EditText) findViewById(R.id.editText5);
        EmailText = (EditText) findViewById(R.id.editText6);

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
                Toast.makeText(SettingActivity.this, "abc" + msg.getData().getString("result"), Toast.LENGTH_SHORT).show();
                super.handleMessage(msg);
            }
        };
    }
    class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(final View v) {
            switch (v.getId()) {
                case R.id.imageView11:
                case R.id.imageView12:
                case R.id.imageView13:
                    new Thread() {
                        @Override
                        public void run() {
                            String name = NameText.getText().toString();
                            String cell_phone = PhoneText.getText().toString();
                            String email = EmailText.getText().toString();
                            PublicFunction valid = new PublicFunction();
                            if (!valid.ValidateUserName(name))
                            {
                                NameText.setError("user name can't be empty!");
                                NameText.setText("");
                            }
                            else if (!valid.ValidateCellphone(cell_phone))
                            {
                                PhoneText.setError("Invalid cellphone number!");
                                PhoneText.setText("");
                            }
                            else if (!valid.ValidateEmail(email))
                            {
                                EmailText.setError("Invalid Email address!");
                                EmailText.setText("");
                            }
                            else
                            {
                                //调用网络服务进行注册用户操作
                                NetworkService service = new NetworkService();
                                String ModifyNum;
                                if (v.getId() == R.id.imageView11)
                                    ModifyNum = "1";
                                else if (v.getId() == R.id.imageView12)
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
