package com.ihunuo.bluetoothdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ihunuo.mybledemo.BaseActivity;
import com.ihunuo.mybledemo.BleSettings.CommondManger;

public class MainActivity extends BaseActivity {

    TextView  mBoolrText;

    Button    msHootbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBoolrText = findViewById(R.id.boolr);
        msHootbtn = findViewById(R.id.shootbtn);
        mBoolrText.setText("9999");

        msHootbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                byte mbyte[] = new byte[8];

                mbyte[0]= 0x00;
                mbyte[1]= 0x01;
                mbyte[2]= 0x02;
                mbyte[3]= 0x03;
                mbyte[4]= 0x04;
                mbyte[5]= 0x05;
                mbyte[6]= 0x06;
                mbyte[7]= 0x07;

                CommondManger.getCommondManger().sendCommond(mbyte);
            }
        });
    }

    @Override
    public void dataGetCallback(byte[] getdata) {
        super.dataGetCallback(getdata);

        if (getdata[0]==0x01)
        {
            int boolor = getdata[1]+getdata[2]+getdata[3]+getdata[4]+getdata[5];
            mBoolrText.setText(boolor+"");
        }

    }
}
