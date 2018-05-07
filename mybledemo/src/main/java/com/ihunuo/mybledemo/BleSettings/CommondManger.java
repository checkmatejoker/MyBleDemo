package com.ihunuo.mybledemo.BleSettings;

import android.util.Log;
import android.widget.Toast;

import com.eleven.app.bluetoothlehelper.Peripheral;
import com.ihunuo.mybledemo.BaseActivity;
import com.ihunuo.mybledemo.R;

import java.util.UUID;

/**
 * 作者:tzy on 2018/5/3.
 * 邮箱:215475174@qq.com
 * 功能介绍:命令管理对象
 */
public class CommondManger {
    private String mServiceIdStr = "";
    private String mChracteristicStr = "";

    private String mNotityStr = "";
    public static String madress = "";


    private BaseActivity baseActivity;
    private Peripheral mPeripheral;
    private static  CommondManger  commondManger;

    public static void initCommondManger(String service,String chart, String notitfy,BaseActivity mbaseActivity)
    {
        if (commondManger==null)
        {
            commondManger = new CommondManger();
        }
        commondManger.setmServiceIdStr(service);
        commondManger.setmChracteristicStr(chart);
        commondManger.setmNotityStr(notitfy);
        commondManger.setBaseActivity(mbaseActivity);
    }

    public static CommondManger getCommondManger() {
        return commondManger;
    }
    public BaseActivity getBaseActivity() {
        return baseActivity;
    }

    public void setBaseActivity(BaseActivity baseActivity) {
        this.baseActivity = baseActivity;
    }
    public UUID getmServiceIdStr() {
        return UUID.fromString(mServiceIdStr);
    }

    public void setmServiceIdStr(String mServiceIdStr) {
        this.mServiceIdStr = mServiceIdStr;
    }

    public UUID getmChracteristicStr() {
        return UUID.fromString(mChracteristicStr);
    }

    public void setmChracteristicStr(String mChracteristicStr) {
        this.mChracteristicStr = mChracteristicStr;
    }

    public UUID getmNotityStr() {
        return UUID.fromString(mNotityStr);
    }

    public void setmNotityStr(String mNotityStr) {
        this.mNotityStr = mNotityStr;
    }
    public Peripheral getPeripheral() {
        return mPeripheral;
    }

    public void setPeripheral(Peripheral peripheral) {
        mPeripheral = peripheral;
    }

    public void sendCommond(byte[] msenddata)
    {
        if (mPeripheral==null)
        {
            Toast.makeText(getBaseActivity(), getBaseActivity().getResources().getString(R.string.warnning1), Toast.LENGTH_SHORT).show();
            return;
        }
        if (mPeripheral.getState()!=Peripheral.STATE_CONNECTED)
        {
            Toast.makeText(getBaseActivity(), getBaseActivity().getResources().getString(R.string.warnning1), Toast.LENGTH_SHORT).show();
            return;
        }

        boolean iscan = mPeripheral.write(getmServiceIdStr(),getmChracteristicStr(),msenddata,false);
        if (iscan)
        {
            Log.d("CommondManger","发送成功");
        }
        else
        {
            Log.d("CommondManger","发送失败");
        }
    }
}
