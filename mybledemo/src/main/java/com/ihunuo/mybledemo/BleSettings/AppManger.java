package com.ihunuo.mybledemo.BleSettings;

import android.bluetooth.BluetoothDevice;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者:tzy on 2018/5/3.
 * 邮箱:215475174@qq.com
 * 功能介绍:app管理对象
 */
public class AppManger {
    private  static  AppManger appManger;//app单例管理对象
    private           List<BluetoothDevice> mDevicelist = new ArrayList<>();//蓝牙设备列表
    /**
     * 方法名：getmDevicelist
     * 功   能：获取蓝牙设备列表
     * 参   数：五
     * 返回值：mDevicelist
     * 参  考：@see xxx
     **/
    public  List<BluetoothDevice> getmDevicelist() {
        return mDevicelist;
    }
   /**
    * 方法名：setmDevicelist(List<BluetoothDevice> mDevicelist)
    * 功   能：设置蓝牙列表
    * 参   数：@para List<BluetoothDevice> mDevicelist)
    * 返回值：无
    * 参  考：@see xxx
    **/
    public void setmDevicelist(List<BluetoothDevice> mDevicelist) {
        this.mDevicelist = mDevicelist;
    }

    /**
     * 方法名：getAppManger()
     * 功   能：获取appMan管理单例对象
     * 参   数：无
     * 返回值：appManger
     * 参  考：@see xxx
     **/

    public static AppManger getAppManger()
    {
        if (appManger==null)
        {
            appManger = new AppManger();
        }
        return  appManger;
    }

}
