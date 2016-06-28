package com.android.valetsafe.valetsafedroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {
    private EditText name;
    private EditText phone;
    private EditText mail;
    private EditText password;
    private EditText repassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);
        name = (EditText)findViewById(R.id.register_edit_name);
        phone = (EditText)findViewById(R.id.register_edit_phone);
        mail = (EditText)findViewById(R.id.register_edit_email);
        password = (EditText)findViewById(R.id.register_edit_password);
        repassword = (EditText)findViewById(R.id.register_edit_repassword);
    }
}
