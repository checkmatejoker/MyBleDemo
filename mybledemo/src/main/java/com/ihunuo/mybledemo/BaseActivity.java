package com.ihunuo.mybledemo;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.eleven.app.bluetoothlehelper.BluetoothLEService;
import com.eleven.app.bluetoothlehelper.Peripheral;
import com.eleven.app.bluetoothlehelper.PeripheralCallback;
import com.ihunuo.mybledemo.BleSettings.AppManger;
import com.ihunuo.mybledemo.BleSettings.CommondManger;

import java.util.List;

public class BaseActivity extends AppCompatActivity {

    //蓝牙模块
    public BluetoothLEService mBluetoothLEService;
    public BluetoothAdapter mBluetoothAdapter;
    public BluetoothManager mBluetoothManager;
    public Handler mHandler = new Handler();



    //发送监听
    private boolean mStartBleActivity = false;

    public MaterialDialog mloaddiag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            ActivityInfo info=this.getPackageManager()
                    .getActivityInfo(getComponentName(),
                            PackageManager.GET_META_DATA);
            String serviceid =info.metaData.getString("serviceid");
            String characteristicid =info.metaData.getString("characteristicid");
            String notifyid =info.metaData.getString("notifyid");

            CommondManger.initCommondManger(serviceid,characteristicid,notifyid,this);
        }catch (Exception e)
        {
           Log.e("BaseActivity","错误信息，请检查是否配置了蓝牙相关信息");
        }



    }

    @Override
    protected void onResume() {
        super.onResume();
        mBluetoothManager = (BluetoothManager) this.getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = mBluetoothManager.getAdapter();
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "make suer you Buletooth more than v4.0!", Toast.LENGTH_LONG).show();
        }

        Intent intent = new Intent(this,BluetoothLEService.class);

        if (mStartBleActivity)
        {
            startService(intent);
        }
        bindService(intent, mServiceConnection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unbindService(mServiceConnection);
        mHandler.removeCallbacksAndMessages(null);
    }
    //新蓝牙模块
    private final ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            mBluetoothLEService = ((BluetoothLEService.LocalBinder) service).getService();

            List<Peripheral> peripheralList = mBluetoothLEService.getConnectedPeripherals();
            for (Peripheral p : peripheralList) {
                p.setCallback(mPeripheralCallback);
                CommondManger.getCommondManger().setPeripheral(p);
                CommondManger.getCommondManger().getPeripheral().setCallback(mPeripheralCallback);
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    initServerCallback();
                }
            });
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            List<Peripheral> peripheralList = mBluetoothLEService.getConnectedPeripherals();
            for (Peripheral p : peripheralList) {
                p.setCallback(null);
                CommondManger.getCommondManger().getPeripheral().setCallback(null);
            }
        }
    };

    private PeripheralCallback mPeripheralCallback = new PeripheralCallback() {
        private String TAG = PeripheralCallback.class.getSimpleName();
        @Override
        public void onConnected(Peripheral peripheral) {
            super.onConnected(peripheral);
            CommondManger.getCommondManger().setPeripheral(peripheral);
            CommondManger.madress = peripheral.getAddress();
        }

        @Override
        public void onDisconnected(final Peripheral peripheral) {
            super.onDisconnected(peripheral);
            Log.e(TAG, "onDisconnected");
            CommondManger.madress = "";
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    disConnectCallback();
                    showToast("连接断开");
                    peripheral.setCallback(null);
                    CommondManger.getCommondManger().getPeripheral().setCallback(null);
                    //重新搜索

                }
            });

        }

        @Override
        public void onConnecting(final Peripheral peripheral) {
            super.onConnecting(peripheral);

        }

        @Override
        public void onServicesDiscovered(final Peripheral peripheral, final int status) {
            super.onServicesDiscovered(peripheral, status);
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    peripheral.setNotify(CommondManger.getCommondManger().getmServiceIdStr(), CommondManger.getCommondManger().getmNotityStr(), true);

                    mloaddiag.dismiss();


                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    connectCallback();
                                }
                            });

                }
            },200);

        }

        @Override
        public void onCharacteristicChanged(final Peripheral peripheral, BluetoothGattCharacteristic characteristic) {
            super.onCharacteristicChanged(peripheral, characteristic);
            Log.d(TAG, "onCharacteristicChanged current time: " + System.currentTimeMillis());
            final   byte[] result = characteristic.getValue();
            //练习列表

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    dataGetCallback(result);
                }
            });
        }

        @Override
        public void onCharacteristicRead(final Peripheral peripheral,final BluetoothGattCharacteristic characteristic,final int status) {
            super.onCharacteristicRead(peripheral, characteristic, status);
        }
    };
    public void showToast(final String text) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(BaseActivity.this, text, Toast.LENGTH_SHORT).show();
            }
        });
    }
    public boolean checkConnection() {
        if (mBluetoothLEService != null) {
            List<Peripheral> peripherals = mBluetoothLEService.getConnectedPeripherals();
            for (int i =0;i<peripherals.size();i++)
            {
                Peripheral p = peripherals.get(i);
                if (p != null && p.getState() == Peripheral.STATE_CONNECTED)
                {
                    return true;
                }
            }
            return false;
        } else {

            return false;
        }
    }


    public void  disConnectCallback(){};
    public void  connectCallback(){};
    public void  initServerCallback(){};
    public void  scanCallback(){};
    public void  dataGetCallback(byte [] getdata){};

    public void showdiss(Context mconx,String string)
    {
        if (mloaddiag==null)
        {
            mloaddiag =  new MaterialDialog.Builder(mconx)
                    .content(string)
                    .cancelable(false)
                    .progress(true,100)
                    .negativeText(R.string.cancel)
                    .callback(new MaterialDialog.ButtonCallback() {
                        @Override
                        public void onNegative(MaterialDialog dialog) {
                            super.onNegative(dialog);
                            dialog.dismiss();
                        }
                    })
                    .show();
        }
        else
        {
            mloaddiag.show();
        }
    }
    public void loaddismiss(Context mconx)
    {

        if (mconx!=null)
        {
            if (mloaddiag!=null)
            {
                mloaddiag.dismiss();
            }

        }
    }
    //蓝牙搜索连接部分
    //蓝牙搜索模块
    public void stratScan(boolean scan) {
        if (scan) {
            Log.d("BaseActivity", "开始扫描");
            // AppInscan.getmApp().getmDeviceList().clear();

            showdiss(this,getResources().getString(R.string.search));

            mBluetoothLEService.stopScan(mLeScanCallback);
            mBluetoothLEService.startScan(mLeScanCallback);
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mBluetoothLEService.stopScan(mLeScanCallback);
                    loaddismiss(BaseActivity.this);

                }
            }, 3000);
        } else {
            mBluetoothLEService.stopScan(mLeScanCallback);
        }
    }

    public void stopscan() {mBluetoothLEService.stopScan(mLeScanCallback);
    }
    private BluetoothAdapter.LeScanCallback mLeScanCallback =  new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
            Log.d("BaseActivity", "扫描到设备: " + device.getName());
            final BluetoothDevice finalDevice = device;
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (finalDevice.getName()!=null)
                    {

//                        if (finalDevice.getName().length()>3)
//                        {
//                            String string = finalDevice.getName().substring(0,3);
//                                if (string.equals("FYJ"))
//                                {

                                if (!AppManger.getAppManger().getmDevicelist().contains(finalDevice))
                                {
                                    AppManger.getAppManger().getmDevicelist().add(finalDevice);
                                    //添加本地名称


                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            scanCallback();
                                        }
                                    });
                                }


//                            }
//                        }
                    }

                }
            });
        }
    };

    public void connecdevice(int postion)
    {
        if (AppManger.getAppManger().getmDevicelist().size()>0)
        {
            showdiss(this,getResources().getString(R.string.connect));
            Peripheral pre = mBluetoothLEService.findPeripheralWithAddress(AppManger.getAppManger().getmDevicelist().get(postion).getAddress());

            if (checkConnection())
            {
                mloaddiag.dismiss();
            }
            else
            {
                pre = mBluetoothLEService.connect(AppManger.getAppManger().getmDevicelist().get(postion).getAddress());
                pre.setCallback(mPeripheralCallback);

            }
        }

    }
    public boolean ismStartBleActivity() {
        return mStartBleActivity;
    }

    public void setmStartBleActivity(boolean mStartBleActivity) {
        this.mStartBleActivity = mStartBleActivity;
    }
}
