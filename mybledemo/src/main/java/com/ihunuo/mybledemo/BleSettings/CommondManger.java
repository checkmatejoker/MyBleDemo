package com.ihunuo.mybledemo.BleSettings;

import android.util.Log;
import android.widget.Toast;

import com.eleven.app.bluetoothlehelper.Peripheral;
import com.ihunuo.mybledemo.BaseActivity;
import com.ihunuo.mybledemo.R;

import java.util.ArrayList;
import java.util.List;
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
    public static List<String>  madress = new ArrayList<>();


    private BaseActivity baseActivity;
    private List<Peripheral>  mPeripheral = new ArrayList<>();
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
    public List<Peripheral> getPeripheral() {
        return mPeripheral;
    }

    public void setPeripheral(List<Peripheral> peripheral) {
        mPeripheral = peripheral;
    }

    public void sendCommond(byte[] msenddata)
    {

        if (mPeripheral==null)
        {
            Toast.makeText(getBaseActivity(), getBaseActivity().getResources().getString(R.string.warnning1), Toast.LENGTH_SHORT).show();
            return;
        }
        if (mPeripheral.size()<=0)
        {
            Toast.makeText(getBaseActivity(), getBaseActivity().getResources().getString(R.string.warnning1), Toast.LENGTH_SHORT).show();
            return;
        }

        for (int i = 0;i<mPeripheral.size();i++)
        {
            boolean iscan = mPeripheral.get(i).write(getmServiceIdStr(),getmChracteristicStr(),msenddata,false);
            if (iscan)
            {
                Log.d("CommondManger","设备"+i+"发送成功");
            }
            else
            {
                Log.d("CommondManger","设备"+i+"发送失败");
            }
        }



    }
}
