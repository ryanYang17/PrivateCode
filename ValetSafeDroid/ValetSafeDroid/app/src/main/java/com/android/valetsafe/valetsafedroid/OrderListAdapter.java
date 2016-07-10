package com.android.valetsafe.valetsafedroid;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.valetsafe.valetsafedroid.data.OrderData;


public class OrderListAdapter extends BaseAdapter {

    private ArrayList<OrderData> orderList = null;
    private Context context = null;


    public OrderListAdapter(ArrayList<OrderData> list, Context context) {
        this.orderList = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return orderList == null ? 0 : orderList.size();
    }

    @Override
    public Object getItem(int position) {
        return orderList == null ? null : orderList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //装载view
        LayoutInflater layoutInflater = LayoutInflater.from(this.context);
        View view = layoutInflater.inflate(R.layout.list_row_layout, null);

        //获取控件
        ImageView bookImageView = (ImageView) view.findViewById(R.id.list_row_map_img);
        TextView bookNameTextView = (TextView) view.findViewById(R.id.list_row_time_txt);
        TextView bookNoReadNumTextView = (TextView) view.findViewById(R.id.list_row_time_txt);
        TextView bookLastTitleView = (TextView) view.findViewById(R.id.list_row_time_txt);

        //对控件赋值
        OrderData bookData = (OrderData) getItem(position);
//        if (bookData != null) {
//            bookImageView.setImageBitmap(HttpUtility.getHttpBitmap(bookData.getImageUrl()));
//            bookNameTextView.setText(bookData.getName());
//            Integer noReadNum = bookData.getTotalNum() - bookData.getCurrentNum();
//            if (noReadNum > 0) {
//                bookNoReadNumTextView.setText(noReadNum + "章节未读");
//                //显示更新小图标
//                bookHasUpdateImageView.setVisibility(View.VISIBLE);
//            } else {
//                bookNoReadNumTextView.setText("无未读章节");
//                //隐藏更新小图标
//                bookHasUpdateImageView.setVisibility(View.GONE);
//            }
//            bookLastTitleView.setText("更新至:" + bookData.getLastTitle());
//        }

        return view;
    }
}