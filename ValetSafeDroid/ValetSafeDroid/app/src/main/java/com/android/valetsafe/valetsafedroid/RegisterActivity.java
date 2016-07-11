package com.android.valetsafe.valetsafedroid;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Map;

import service.NetworkService;

public class RegisterActivity extends AppCompatActivity {

    public final static int RESULT_CODE=1;

    private EditText nameEdit;
    private EditText phoneEdit;
    private EditText mailEdit;
    private EditText passwordEdit;
    private EditText repasswordEdit;
    private Button registerButton;
    private TextView signInText;
    private CheckBox checkBox;
//    private ImageView duihaoFrame;
//    private ImageView duihao;
    private int duihaoCount = 0;

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);
        nameEdit = (EditText)findViewById(R.id.register_edit_name);
        phoneEdit = (EditText)findViewById(R.id.register_edit_phone);
        mailEdit = (EditText)findViewById(R.id.register_edit_email);
        passwordEdit = (EditText)findViewById(R.id.register_edit_password);
        repasswordEdit = (EditText)findViewById(R.id.register_edit_repassword);
        registerButton = (Button)findViewById(R.id.register_btn_reg);
        signInText = (TextView)findViewById(R.id.register_text_signin);
        checkBox = (CheckBox)findViewById(R.id.register_checkbox);
        //duihaoFrame = (ImageView)findViewById(R.id.register_view_set);
       // duihao = (ImageView)findViewById(R.id.register_view_noset);

        registerButton.setOnClickListener(new ButtonClickListener());

        signInText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                //intent.putExtra("str", "Intent Demo");
                startActivity(intent);
            }
        });

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Toast.makeText(RegisterActivity.this, "abc" + msg.getData().getString("result"), Toast.LENGTH_SHORT).show();
                super.handleMessage(msg);
            }
        };
    }

    /**
     * 简单的监听，只简单实现了注册消息传递，所有的逻辑暂未加。
     */
    class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.register_btn_reg:
                    new Thread() {
                        @Override
                        public void run() {
                            String name = nameEdit.getText().toString();
                            String cell_phone = phoneEdit.getText().toString();
                            String email = mailEdit.getText().toString();
                            String password = passwordEdit.getText().toString();

                            //调用网络服务进行注册用户操作
                            NetworkService service = new NetworkService();
                            Map<String, String> data = service.registerUserAction(name,cell_phone,email,password);

                            String result = data.get("result");
                            Message msg = new Message();
                            msg.arg1 = 0;
                            msg.getData().putString("result", result);
                            handler.sendMessage(msg);
                            onEnd();
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
