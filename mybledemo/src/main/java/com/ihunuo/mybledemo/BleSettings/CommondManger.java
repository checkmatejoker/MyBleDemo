package com.ihunuo.mybledemo.BleSettings;

import android.util.Log;
import android.widget.Toast;

import com.eleven.app.bluetoothlehelper.Peripheral;
import com.ihunuo.mybledemo.BaseActivity;
import com.ihunuo.mybledemo.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * 作者:tzy on 2018/5/3.
 * 邮箱:215475174@qq.com
 * 功能介绍:命令管理对象
 */
public class CommondManger {
    private static final String TAG = "CommondManger";
    private static int MTU_SIZE_EXPECT = 300;
    private static int MTU_PAYLOAD_SIZE_LIMIT = 20;
    private boolean isUnpackSending = false;

    private String mServiceIdStr = "";
    private String mChracteristicStr = "";

    private String mNotityStr = "";
    public static List<String>  madress = new ArrayList<>();


    private BaseActivity baseActivity;
    private List<Peripheral>  mPeripheral = new ArrayList<>();
    private static  CommondManger  commondManger;

    private  Peripheral mPre;


    public boolean isWriteCharacteristicError() {
        return writeCharacteristicError;
    }

    public void setWriteCharacteristicError(boolean writeCharacteristicError) {
        this.writeCharacteristicError = writeCharacteristicError;
    }

    public boolean isWriteCharacteristicOk() {
        return isWriteCharacteristicOk;
    }

    public void setWriteCharacteristicOk(boolean writeCharacteristicOk) {
        isWriteCharacteristicOk = writeCharacteristicOk;
    }

    private volatile boolean writeCharacteristicError = false;
    private volatile boolean isWriteCharacteristicOk = true;

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
    public void sendCommond(byte[] msenddata,int index)
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

        mPre = mPeripheral.get(index);

        ThreadUnpackSend  mUnpackThread = new ThreadUnpackSend(msenddata);
        mUnpackThread.start();

    }

    private void SendData(byte[] realData) {
        // for GKI get buffer error exit
        long timeCost = 0;

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

        // initial the status
        writeCharacteristicError = true;

        while(true == writeCharacteristicError) {
            // mBluetoothGatt.getConnectionState(mDevice) can not use in thread, so we use a flag to
            // break the circulate when disconnect the connect
            if(mPre.getState()==Peripheral.STATE_CONNECTED) {
                // for GKI get buffer error exit
                timeCost = System.currentTimeMillis();


                // initial the status
                isWriteCharacteristicOk = false;

                // Set the send type(command)
                boolean missend = mPre.write(getmServiceIdStr(),getmChracteristicStr(),realData,false);
                if (!missend) {
                    Log.e(TAG, "发送失败");
                }

                // wait for characteristic write ok
                while(isWriteCharacteristicOk != true) {
                    if(mPre.getState()!=Peripheral.STATE_CONNECTED) {
                        Log.e(TAG, "break the circulate when disconnect the connect, no callback");
                        break;
                    }

                    // for GKI get buffer error exit
                    // if 10 seconds no callback we think GKI get buffer error
                    if((System.currentTimeMillis() - timeCost)/1000 > 10) {
                        Log.e(TAG, "GKI get buffer error close the BT and exit");
                        // becouse GKI error, so we should close the bt
//                        if (mBluetoothAdapter.isEnabled()) {
//                            Log.d(TAG, "close bluetooth");
//                            mBluetoothAdapter.disable();
//                        }

                        // close the activity
                    }
                };

                Log.e(TAG, "writeCharacteristicError stop Status:" + writeCharacteristicError);
            } else {
                // break the circulate when disconnect the connect
                Log.e(TAG, "break the circulate when disconnect the connect, write error");
                break;
            }
        }

        Log.d(TAG, "send data is: " + Arrays.toString(realData));

        // update tx counter, only write ok then update the counter
        if(false == writeCharacteristicError) {
            Log.e(TAG, "send ok" );

        }

        // send the msg, here may send less times MSG, so we use the StringBuildler

    }

    // unpack and send thread
    public class ThreadUnpackSend extends Thread {
        byte[] sendData;
        ThreadUnpackSend(byte[] data) {
            sendData = data;
        }
        public void run() {
            Log.d(TAG, "ThreadUnpackSend is run");
            // time test
            Log.e("TIME_thread run", String.valueOf(System.currentTimeMillis()));
            // set the unpack sending flag
            isUnpackSending = true;
            // send data to the remote device

            // unpack the send data, because of the MTU size is limit
            int length = sendData.length;
            int unpackCount = 0;
            byte[] realSendData;
            do {

                if(length <= MTU_PAYLOAD_SIZE_LIMIT) {
                    realSendData = new byte[length];
                    System.arraycopy(sendData, unpackCount * MTU_PAYLOAD_SIZE_LIMIT, realSendData, 0, length);
                    // update length value
                    length = 0;
                } else {
                    realSendData = new byte[MTU_PAYLOAD_SIZE_LIMIT];
                    System.arraycopy(sendData, unpackCount * MTU_PAYLOAD_SIZE_LIMIT, realSendData, 0, MTU_PAYLOAD_SIZE_LIMIT);

                    // update length value
                    length = length - MTU_PAYLOAD_SIZE_LIMIT;
                }
                SendData(realSendData);
                // unpack counter increase
                unpackCount++;
            } while(length != 0);

            // set the unpack sending flag

            isUnpackSending = false;
            Log.d(TAG, "ThreadUnpackSend stop");
        }//if(null != mTestCharacter)
    }//run

}
