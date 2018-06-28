package com.ihunuo.bluetoothdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.ihunuo.mybledemo.BaseActivity;
import com.ihunuo.mybledemo.BleSettings.CommondManger;

public class BlueToothActivity extends BaseActivity implements DeviceAdapter.Listener{

    ListView mBultoothlistView;
    Button mReScan;
    DeviceAdapter mDeviceAdapter;

    private TextView mtextview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blue_tooth);
        mBultoothlistView = (ListView)findViewById(R.id.blutooth_listview);
        mReScan = (Button)findViewById(R.id.restart_btn);
        mReScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stratScan(true);
            }
        });
        mDeviceAdapter= new DeviceAdapter(this);
        mBultoothlistView.setAdapter(mDeviceAdapter);
        mDeviceAdapter.setListener(this);
        mDeviceAdapter.notifyDataSetChanged();

        mtextview =(TextView)findViewById(R.id.comple);
        mtextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BlueToothActivity.this, MainActivity.class);
                startActivityForResult(intent, 109);
            }
        });
    }

    @Override
    protected void onResume() {
        setmStartBleActivity(true);
        super.onResume();
    }

    @Override
    public void scanCallback() {
        super.scanCallback();

        mDeviceAdapter.notifyDataSetChanged();
    }

    @Override
    public void initServerCallback() {
        super.initServerCallback();
        stratScan(true);
    }

    @Override
    public void onSelected(int positon) {
        connecdevice(positon);
    }

    @Override
    public void connectCallback() {
        super.connectCallback();
        mDeviceAdapter.notifyDataSetChanged();



    }
}
