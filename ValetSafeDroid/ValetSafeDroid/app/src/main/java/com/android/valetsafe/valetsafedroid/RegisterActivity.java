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
                if(msg.arg1 == 0){
                    CBCommonResult<String> result = (CBCommonResult<String>) msg.getData().get("result");
                    if(result.getCode() == 0){
                        Toast.makeText(RegisterActivity.this, result.getDescription(), Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(RegisterActivity.this, result.getDescription(), Toast.LENGTH_SHORT).show();
                    }
                }else if(msg.arg1 == 1){
                    CBCommonResult<User> result = (CBCommonResult<User>) msg.getData().get("result");
                    if(result.getCode() == 0){
                        User user = result.getData();
                    }
                    Toast.makeText(RegisterActivity.this, result.getDescription(), Toast.LENGTH_SHORT).show();
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
                            PublicFunction pub = new PublicFunction(RegisterActivity.this.getApplicationContext());
                            String name = nameEdit.getText().toString();
                            String cell_phone = phoneEdit.getText().toString();
                            String email = mailEdit.getText().toString();
                            String password = passwordEdit.getText().toString();
                            String repassword = repasswordEdit.getText().toString();
                            if (pub.ValidateUserName(name)){
                                Toast.makeText(RegisterActivity.this, "User name can't be null", Toast.LENGTH_SHORT).show();
                                nameEdit.setText("");
                                return;
                            }
                            else if (pub.ValidateCellphone(cell_phone)){
                                Toast.makeText(RegisterActivity.this, "Wrong phone number", Toast.LENGTH_SHORT).show();
                                phoneEdit.setText("");
                                return;
                            }
                            else if (pub.ValidateEmail(email)){
                                Toast.makeText(RegisterActivity.this, "Wrong Email format", Toast.LENGTH_SHORT).show();
                                mailEdit.setText("");
                                return;
                            }
                            else if (password.equals(repassword)){
                                Toast.makeText(RegisterActivity.this, "Wrong Email format", Toast.LENGTH_SHORT).show();
                                passwordEdit.setText("");
                                repasswordEdit.setText("");
                                return;
                            }
                            else if (!checkBox.isChecked()){
                                Toast.makeText(RegisterActivity.this, "Please agree our terms", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            //调用网络服务进行注册用户操作

                            NetworkService service = new NetworkService();
                            CBCommonResult<User> result= service.registerUserAction(name,cell_phone,email,password);
                            // CBCommonResult<User> result = service.loadUser(2, name, cell_phone);
                            // CBCommonResult<String> result= service.createReserveOrderAction("hzy","current_place","destination_place","reserve_time","create");
                            // CBCommonResult<String> result= service.updateReserveOrderAfterReceiveDriver(2,"receive_driver", "receive");

                            Message msg = new Message();
                            msg.arg1 = 0;
                            msg.getData().putSerializable("result", result);
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
