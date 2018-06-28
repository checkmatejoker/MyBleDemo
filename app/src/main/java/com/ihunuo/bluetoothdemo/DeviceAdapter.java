package com.ihunuo.bluetoothdemo;

/**
 * 作者:tzy on 2018/5/4.
 * 邮箱:215475174@qq.com
 * 功能介绍:xxx
 */

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ihunuo.mybledemo.BleSettings.AppManger;
import com.ihunuo.mybledemo.BleSettings.CommondManger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tzy on 15/11/17.
 */
public class DeviceAdapter extends BaseAdapter {
    private final static String TAG = "MyDeviceAdapter";
    private Context mContext;
    private int mSelectedIndex;
    private Listener mListener;
    private List<BluetoothDevice> mDevices;
    private ArrayList<Integer> mRssi;

    public DeviceAdapter(Context context) {
        mContext = context;
        mRssi = new ArrayList<Integer>();
        mDevices = new ArrayList<BluetoothDevice>();
    }
    public void add(BluetoothDevice device) {
        if (!mDevices.contains(device)) {
            boolean isTargetDevice = false;
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "debug");
                isTargetDevice = true;
            } else {
                if (device.getName()!=null)
                {
                    if (device.getName().equals("LEDFan")) {
                        isTargetDevice = true;
                    }
                }

            }
            if (isTargetDevice) {
                mDevices.add(device);
                notifyDataSetChanged();
            }
        }
    }


    public List<BluetoothDevice> getBluetoothDeviceList() {
        return mDevices;
    }

    public void setBluetoothDeviceList(List<BluetoothDevice> bluetoothDeviceList) {
        mDevices = bluetoothDeviceList;
    }

    /**
     * 清空
     */
    public void clear() {
        mDevices.clear();

    }
    @Override
    public int getCount() {
        return AppManger.getAppManger().getmDevicelist().size() ;
    }

    @Override
    public Object getItem(int position) {
        if (position < AppManger.getAppManger().getmDevicelist().size()){
            return AppManger.getAppManger().getmDevicelist().get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        convertView = LayoutInflater.from(mContext).inflate(R.layout.bluetooth_item, null, false);
        TextView devicenametext = (TextView) convertView.findViewById(R.id.textdevice);
        BluetoothDevice device = AppManger.getAppManger().getmDevicelist().get(position);
        devicenametext.setText(device.getName());
        RelativeLayout relativeLayout = (RelativeLayout) convertView.findViewById(R.id.item_bg);
        relativeLayout.setBackgroundColor(mContext.getResources().getColor(R.color.deviceunselectcolor));


        for (int i = 0;i<CommondManger.madress.size();i++)
        {
            if (CommondManger.madress.get(i).equals(device.getAddress()))
            {
                relativeLayout.setBackgroundColor(mContext.getResources().getColor(R.color.deviceselectcolor));
            }

        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelectedIndex(position);
                if (mListener != null){
                    mListener.onSelected(position);

                }
            }
        });
        return convertView;

    }
    public int getSelectedIndex() {
        return mSelectedIndex;
    }

    public void setSelectedIndex(int selectedIndex) {
        if (selectedIndex == mSelectedIndex) return;
        mSelectedIndex = selectedIndex;
        notifyDataSetChanged();
    }

    public Listener getListener() {
        return mListener;
    }

    public void setListener(Listener listener) {
        mListener = listener;
    }

    public interface Listener {
        void onSelected(final int positon);


    }
    public void removeDeviceWithAddress(final String address) {
        for (BluetoothDevice device : mDevices) {
            if (device.getAddress().equals(address)) {
                mDevices.remove(device);
                break;
            }
        }
        notifyDataSetChanged();
    }
}
