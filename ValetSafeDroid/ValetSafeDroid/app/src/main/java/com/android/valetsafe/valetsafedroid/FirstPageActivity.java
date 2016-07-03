package com.android.valetsafe.valetsafedroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class FirstPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_page_layout);

        work();
    }

    /*
        加载登录文件

     */
    private boolean load(){

        //  添加加载函数

        return true;
    }

    private void work(){
        if(load()){
            Intent intent = new Intent(FirstPageActivity.this, NavMapActivity.class);
            //intent.putExtra("str", "Intent Demo");
            startActivity(intent);
        }else{
            Intent intent = new Intent(FirstPageActivity.this, LoginActivity.class);
            startActivity(intent);
        }

    }
}
