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

/**
 *
 * 订车列表展开界面
 *
 * author lhy
 *
 *
 *
 *
 */

public class OrderLaunchActivity extends AppCompatActivity {
    private ImageView backBtn;//返回按钮
    private TextView pickUpText;//乘车地点
    private TextView destinationText;//目的地点
    private TextView timeText;//时间（小时分钟）
    private TextView dateText;//时间（月份日期）
    private TextView phoneText;//司机电话号码
    private TextView nameText;//司机姓名
    private TextView numText;//司机车牌号
    private TextView priceText;//订单价格
    private ImageView headImg;//司机头像
    private RatingBar rating;//司机评分

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
                OrderLaunchActivity.this.finish();
            }
        });
    }
}
