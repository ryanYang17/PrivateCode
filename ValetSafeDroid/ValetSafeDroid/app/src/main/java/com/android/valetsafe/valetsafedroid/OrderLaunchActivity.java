package com.android.valetsafe.valetsafedroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.valetsafe.valetsafedroid.data.OrderData;

import java.util.ArrayList;

public class OrderLaunchActivity extends AppCompatActivity {
    private ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_launch_layout);
        backBtn = (ImageView) findViewById(R.id.order_launch_back_btn);


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
