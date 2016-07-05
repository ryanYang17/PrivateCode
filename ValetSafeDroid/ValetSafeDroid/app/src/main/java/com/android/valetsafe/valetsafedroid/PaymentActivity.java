package com.android.valetsafe.valetsafedroid;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class PaymentActivity extends AppCompatActivity {

    private ImageView backBtn;
    private ImageView addBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_layout);
        backBtn = (ImageView) findViewById(R.id.payment_back_btn);
        addBtn = (ImageView) findViewById(R.id.payment_add_card_btn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCard();
            }
        });
    }

    private void addCard(){
        Intent intent = new Intent(PaymentActivity.this, PaymentActivity.class);
        startActivity(intent);
    }


}
