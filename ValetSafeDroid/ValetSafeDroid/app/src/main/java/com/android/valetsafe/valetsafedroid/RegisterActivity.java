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

import bean.CBCommonResult;
import bean.User;
import service.NetworkService;

/**
 * 注册界面
 *
 * author lhy
 *
 *
 */
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
    private PublicFunction pub;
    private int ErrorNum = -1;
    private String ErrorLog = "";
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
        pub = new PublicFunction(RegisterActivity.this.getApplicationContext());

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
                if(msg.arg1 == 0){
                    CBCommonResult<User> result = (CBCommonResult<User>) msg.getData().get("result");
                    if(result.getCode() == 0){
                        User user = result.getData();
                        pub.writeTxtToFile(user.getCell_phone(), "login.txt");
                        pub.writeTxtToFile(user.getPassword(), "login.txt");
                        pub.writeTxtToFile(String.valueOf(user.getId()), "login.txt");
                        UserAttribute.setId(user.getId());
                        UserAttribute.setName(user.getName());
                        UserAttribute.setCell_phone(user.getCell_phone());
                        UserAttribute.setEmail(user.getEmail());
                        UserAttribute.setPassword(user.getPassword());
                        login();
                    }
                    Toast.makeText(RegisterActivity.this, result.getDescription(), Toast.LENGTH_SHORT).show();
                }else if(msg.arg1 == 1){
                    Toast.makeText(RegisterActivity.this, ErrorLog, Toast.LENGTH_SHORT).show();
                    if (ErrorNum == 0){
                        nameEdit.setText("");
                    }
                    else if(ErrorNum == 1){
                        phoneEdit.setText("");
                    }
                    else if (ErrorNum == 2){
                        mailEdit.setText("");
                    }
                    else if (ErrorNum == 3){
                        passwordEdit.setText("");
                        repasswordEdit.setText("");
                    }
                }
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
                            String repassword = repasswordEdit.getText().toString();
                            boolean ValidSucc = true;
                            if (!pub.ValidateUserName(name)){
                                ErrorLog = "User name can't be null";
                                ErrorNum = 0;
                                ValidSucc = false;
                            }
                            else if (!pub.ValidateCellphone(cell_phone)){
                                ErrorLog = "Wrong phone number";
                                ErrorNum = 1;
                                ValidSucc = false;
                            }
                            else if (!pub.ValidateEmail(email)){
                                ErrorLog = "Wrong Email format";
                                ErrorNum = 2;
                                ValidSucc = false;
                            }
                            else if (!password.equals(repassword)){
                                ErrorLog = "Twice password is not same";
                                ErrorNum = 3;
                                ValidSucc = false;
                            }
                            else if (!checkBox.isChecked()){
                                ErrorLog = "Please agree our terms";
                                ValidSucc = false;
                            }
                            if (ValidSucc){
                                //调用网络服务进行注册用户操作

                                NetworkService service = new NetworkService();
                                CBCommonResult<User> result = service.registerUserAction(name, cell_phone, email, password);
                                // CBCommonResult<User> result = service.loadUser(2, name, cell_phone);
                                // CBCommonResult<String> result= service.createReserveOrderAction("hzy","current_place","destination_place","reserve_time","create");
                                // CBCommonResult<String> result= service.updateReserveOrderAfterReceiveDriver(2,"receive_driver", "receive");

                                Message msg = new Message();
                                msg.arg1 = 0;
                                msg.getData().putSerializable("result", result);
                                handler.sendMessage(msg);
                            }
                            else {
                                Message msg = new Message();
                                msg.arg1 = 1;
                                handler.sendMessage(msg);
                            }
                        }
                    }.start();
                    break;
            }
        }
    }

    private void login(){
        Intent intent = new Intent(RegisterActivity.this, NavMapActivity.class);
        startActivity(intent);
    }
}
