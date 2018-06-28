package com.ihunuo.bluetoothdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ihunuo.mybledemo.BaseActivity;
import com.ihunuo.mybledemo.BleSettings.CommondManger;

public class MainActivity extends BaseActivity {


    ImageView mNum1;
    ImageView mNum2;
    ImageView mNum3;
    ImageView mNum4;
    ImageView mNum5;
    ImageView mNum6;
    ImageView mNum7;
    ImageView mNum8;
    ImageView mNum9;
    ImageView mNum10;

    ImageView mHeart1;
    ImageView mHeart2;
    ImageView mHeart3;
    ImageView mHeart4;
    ImageView mHeart5;
    ImageView mHeart6;
    ImageView mHeart7;
    ImageView mHeart8;
    ImageView mHeart9;
    ImageView mHeart10;
    TextView mtextback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mtextback = (TextView)findViewById(R.id.mbanck);

        mtextback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mNum1 = (ImageView)findViewById(R.id.name_1);
        mNum2 = (ImageView)findViewById(R.id.name_2);
        mNum3 = (ImageView)findViewById(R.id.name_3);
        mNum4 = (ImageView)findViewById(R.id.name_4);
        mNum5 = (ImageView)findViewById(R.id.name_5);
        mNum6 = (ImageView)findViewById(R.id.name_6);
        mNum7 = (ImageView)findViewById(R.id.name_7);
        mNum8 = (ImageView)findViewById(R.id.name_8);
        mNum9 = (ImageView)findViewById(R.id.name_9);
        mNum10 = (ImageView)findViewById(R.id.name_10);

        mHeart1 = (ImageView)findViewById(R.id.herat_1);
        mHeart2 = (ImageView)findViewById(R.id.herat_2);
        mHeart3 = (ImageView)findViewById(R.id.herat_3);
        mHeart4 = (ImageView)findViewById(R.id.herat_4);
        mHeart5 = (ImageView)findViewById(R.id.herat_5);
        mHeart6 = (ImageView)findViewById(R.id.herat_6);
        mHeart7 = (ImageView)findViewById(R.id.herat_7);
        mHeart8 = (ImageView)findViewById(R.id.herat_8);
        mHeart9 = (ImageView)findViewById(R.id.herat_9);
        mHeart10 = (ImageView)findViewById(R.id.herat_10);


    }

    @Override
    public void dataGetCallback(byte[] getdata) {
        super.dataGetCallback(getdata);

        int head = getdata[0]&0xff;

        if (head==0XA5)
        {
            refreshView(getdata[1]);
        }
        if (head==0X0F)
        {
            refreshViewHeart(getdata[1]);
        }

    }

    private void refreshView(int type)
    {
        mNum1.setVisibility(View.GONE);
        mNum2.setVisibility(View.GONE);
        mNum3.setVisibility(View.GONE);
        mNum4.setVisibility(View.GONE);
        mNum5.setVisibility(View.GONE);
        mNum6.setVisibility(View.GONE);
        mNum7.setVisibility(View.GONE);
        mNum8.setVisibility(View.GONE);
        mNum9.setVisibility(View.GONE);
        mNum10.setVisibility(View.GONE);
        if (type==1)
        {
            mNum1.setVisibility(View.VISIBLE);
        }
        if (type==2)
        {
            mNum1.setVisibility(View.VISIBLE);
            mNum2.setVisibility(View.VISIBLE);
        }
        if (type==3)
        {
            mNum1.setVisibility(View.VISIBLE);
            mNum2.setVisibility(View.VISIBLE);
            mNum3.setVisibility(View.VISIBLE);
        }
        if (type==4)
        {
            mNum1.setVisibility(View.VISIBLE);
            mNum2.setVisibility(View.VISIBLE);
            mNum3.setVisibility(View.VISIBLE);
            mNum4.setVisibility(View.VISIBLE);
        }
        if (type==5)
        {
            mNum1.setVisibility(View.VISIBLE);
            mNum2.setVisibility(View.VISIBLE);
            mNum3.setVisibility(View.VISIBLE);
            mNum4.setVisibility(View.VISIBLE);
            mNum5.setVisibility(View.VISIBLE);
        }
        if (type==6)
        {
            mNum1.setVisibility(View.VISIBLE);
            mNum2.setVisibility(View.VISIBLE);
            mNum3.setVisibility(View.VISIBLE);
            mNum4.setVisibility(View.VISIBLE);
            mNum5.setVisibility(View.VISIBLE);
            mNum6.setVisibility(View.VISIBLE);
        }
        if (type==7)
        {
            mNum1.setVisibility(View.VISIBLE);
            mNum2.setVisibility(View.VISIBLE);
            mNum3.setVisibility(View.VISIBLE);
            mNum4.setVisibility(View.VISIBLE);
            mNum5.setVisibility(View.VISIBLE);
            mNum6.setVisibility(View.VISIBLE);
            mNum7.setVisibility(View.VISIBLE);
        }
        if (type==8)
        {
            mNum1.setVisibility(View.VISIBLE);
            mNum2.setVisibility(View.VISIBLE);
            mNum3.setVisibility(View.VISIBLE);
            mNum4.setVisibility(View.VISIBLE);
            mNum5.setVisibility(View.VISIBLE);
            mNum6.setVisibility(View.VISIBLE);
            mNum7.setVisibility(View.VISIBLE);
            mNum8.setVisibility(View.VISIBLE);
        }
        if (type==9)
        {
            mNum1.setVisibility(View.VISIBLE);
            mNum2.setVisibility(View.VISIBLE);
            mNum3.setVisibility(View.VISIBLE);
            mNum4.setVisibility(View.VISIBLE);
            mNum5.setVisibility(View.VISIBLE);
            mNum6.setVisibility(View.VISIBLE);
            mNum7.setVisibility(View.VISIBLE);
            mNum8.setVisibility(View.VISIBLE);
            mNum9.setVisibility(View.VISIBLE);
        }
        if (type==10)
        {
            mNum1.setVisibility(View.VISIBLE);
            mNum2.setVisibility(View.VISIBLE);
            mNum3.setVisibility(View.VISIBLE);
            mNum4.setVisibility(View.VISIBLE);
            mNum5.setVisibility(View.VISIBLE);
            mNum6.setVisibility(View.VISIBLE);
            mNum7.setVisibility(View.VISIBLE);
            mNum8.setVisibility(View.VISIBLE);
            mNum9.setVisibility(View.VISIBLE);
            mNum10.setVisibility(View.VISIBLE);
        }

    }

    private void refreshViewHeart(int type)
    {
        mHeart1.setVisibility(View.GONE);
        mHeart2.setVisibility(View.GONE);
        mHeart3.setVisibility(View.GONE);
        mHeart4.setVisibility(View.GONE);
        mHeart5.setVisibility(View.GONE);
        mHeart6.setVisibility(View.GONE);
        mHeart7.setVisibility(View.GONE);
        mHeart8.setVisibility(View.GONE);
        mHeart9.setVisibility(View.GONE);
        mHeart10.setVisibility(View.GONE);
        if (type==1)
        {
            mHeart1.setVisibility(View.VISIBLE);
        }
        if (type==2)
        {
            mHeart1.setVisibility(View.VISIBLE);
            mHeart2.setVisibility(View.VISIBLE);
        }
        if (type==3)
        {
            mHeart1.setVisibility(View.VISIBLE);
            mHeart2.setVisibility(View.VISIBLE);
            mHeart3.setVisibility(View.VISIBLE);
        }
        if (type==4)
        {
            mHeart1.setVisibility(View.VISIBLE);
            mHeart2.setVisibility(View.VISIBLE);
            mHeart3.setVisibility(View.VISIBLE);
            mHeart4.setVisibility(View.VISIBLE);
        }
        if (type==5)
        {
            mHeart1.setVisibility(View.VISIBLE);
            mHeart2.setVisibility(View.VISIBLE);
            mHeart3.setVisibility(View.VISIBLE);
            mHeart4.setVisibility(View.VISIBLE);
            mHeart5.setVisibility(View.VISIBLE);
        }
        if (type==6)
        {
            mHeart1.setVisibility(View.VISIBLE);
            mHeart2.setVisibility(View.VISIBLE);
            mHeart3.setVisibility(View.VISIBLE);
            mHeart4.setVisibility(View.VISIBLE);
            mHeart5.setVisibility(View.VISIBLE);
            mHeart6.setVisibility(View.VISIBLE);
        }
        if (type==7)
        {
            mHeart1.setVisibility(View.VISIBLE);
            mHeart2.setVisibility(View.VISIBLE);
            mHeart3.setVisibility(View.VISIBLE);
            mHeart4.setVisibility(View.VISIBLE);
            mHeart5.setVisibility(View.VISIBLE);
            mHeart6.setVisibility(View.VISIBLE);
            mHeart7.setVisibility(View.VISIBLE);
        }
        if (type==8)
        {
            mHeart1.setVisibility(View.VISIBLE);
            mHeart2.setVisibility(View.VISIBLE);
            mHeart3.setVisibility(View.VISIBLE);
            mHeart4.setVisibility(View.VISIBLE);
            mHeart5.setVisibility(View.VISIBLE);
            mHeart6.setVisibility(View.VISIBLE);
            mHeart7.setVisibility(View.VISIBLE);
            mHeart8.setVisibility(View.VISIBLE);
        }
        if (type==9)
        {
            mHeart1.setVisibility(View.VISIBLE);
            mHeart2.setVisibility(View.VISIBLE);
            mHeart3.setVisibility(View.VISIBLE);
            mHeart4.setVisibility(View.VISIBLE);
            mHeart5.setVisibility(View.VISIBLE);
            mHeart6.setVisibility(View.VISIBLE);
            mHeart7.setVisibility(View.VISIBLE);
            mHeart8.setVisibility(View.VISIBLE);
            mHeart9.setVisibility(View.VISIBLE);
        }
        if (type==10)
        {
            mHeart1.setVisibility(View.VISIBLE);
            mHeart2.setVisibility(View.VISIBLE);
            mHeart3.setVisibility(View.VISIBLE);
            mHeart4.setVisibility(View.VISIBLE);
            mHeart5.setVisibility(View.VISIBLE);
            mHeart6.setVisibility(View.VISIBLE);
            mHeart7.setVisibility(View.VISIBLE);
            mHeart8.setVisibility(View.VISIBLE);
            mHeart9.setVisibility(View.VISIBLE);
            mHeart10.setVisibility(View.VISIBLE);
        }

    }
}
