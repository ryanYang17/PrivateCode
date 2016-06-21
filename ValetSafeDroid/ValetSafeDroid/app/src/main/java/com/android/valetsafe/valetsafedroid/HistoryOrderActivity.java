package com.android.valetsafe.valetsafedroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.android.valetsafe.valetsafedroid.data.OrderData;

import java.util.ArrayList;

public class HistoryOrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_order_layout);
        ArrayList<OrderData> list = new ArrayList<OrderData>();
        OrderData bookData = new OrderData();
        list.add(bookData);
        list.add(new OrderData());
        OrderListAdapter bookshelfListViewAdapter = new OrderListAdapter(list, this);
        ListView listView = (ListView) findViewById(R.id.history_order_list_view);
        listView.setAdapter(bookshelfListViewAdapter);


    }
}
