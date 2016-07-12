package com.android.valetsafe.valetsafedroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.valetsafe.valetsafedroid.data.OrderData;

import java.util.ArrayList;

/**
 * 预约订单列表界面
 *
 * author lhy
 *
 */
public class AdvancedOrderActivity extends AppCompatActivity {

    private ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.advanced_order_layout);
        backBtn = (ImageView) findViewById(R.id.advanced_order_back_btn);
        ArrayList<OrderData> list = new ArrayList<OrderData>();
        OrderData data = new OrderData();
        list.add(data);
        list.add(new OrderData());
        OrderListAdapter listViewAdapter = new OrderListAdapter(list, this);
        ListView listView = (ListView) findViewById(R.id.advanced_order_list_view);
        listView.setAdapter(listViewAdapter);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        /**
         * 单击选项响应
         */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(AdvancedOrderActivity.this, OrderLaunchActivity.class);
                //intent.putExtra("str", "Intent Demo");
                startActivity(intent);
            }
        });
    }
}
