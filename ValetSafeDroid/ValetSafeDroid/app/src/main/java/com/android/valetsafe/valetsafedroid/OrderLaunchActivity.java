package com.android.valetsafe.valetsafedroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.valetsafe.valetsafedroid.data.OrderData;

import java.util.ArrayList;

public class OrderLaunchActivity extends AppCompatActivity {
    private ImageView backBtn;
    private TextView pickUpText;
    private TextView destinationText;
    private TextView timeText;
    private TextView dateText;
    private TextView phoneText;
    private TextView nameText;
    private TextView numText;
    private TextView priceText;
    private ImageView headImg;
    private RatingBar rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_launch_layout);
        backBtn = (ImageView) findViewById(R.id.order_launch_back_btn);
        pickUpText = (TextView) findViewById(R.id.order_launch_start_txt);
        destinationText = (TextView) findViewById(R.id.order_launch_end_txt);
        timeText = (TextView) findViewById(R.id.order_launch_time_txt);
        dateText = (TextView) findViewById(R.id.order_launch_data_txt);
        phoneText = (TextView) findViewById(R.id.order_launch_phone_txt);
        nameText = (TextView) findViewById(R.id.order_launch_name_txt);
        numText = (TextView) findViewById(R.id.order_launch_plate_num_txt);
        priceText = (TextView) findViewById(R.id.order_launch_price_txt);
        headImg = (ImageView) findViewById(R.id.order_launch_head_img);
        rating = (RatingBar) findViewById(R.id.order_launch_rating);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
