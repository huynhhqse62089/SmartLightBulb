package com.example.huynhhq.smartlightbulb.Adapter;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.huynhhq.smartlightbulb.Model.DeviceItem;
import com.example.huynhhq.smartlightbulb.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HuynhHQ on 9/19/2017.
 */

public class DeviceListAdapter extends ArrayAdapter<DeviceItem> {
    private LayoutInflater mLayoutInflater;
    private ArrayList<DeviceItem> mDevices;
    private int  mViewResourceId;

    public DeviceListAdapter(Context context, int tvResourceId, ArrayList<DeviceItem> devices){
        super(context, tvResourceId,devices);
        this.mDevices = devices;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mViewResourceId = tvResourceId;
    }

    private class ViewHolder {
        TextView titleText;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = mLayoutInflater.inflate(mViewResourceId, null);

        DeviceItem device = mDevices.get(position);

        if (device != null) {
            TextView deviceName = (TextView) convertView.findViewById(R.id.titleTextView);
            TextView deviceAdress = (TextView) convertView.findViewById(R.id.macAddress);

            if (deviceName != null) {
                deviceName.setText(device.getDeviceName());
            }
            if (deviceAdress != null) {
                deviceAdress.setText(device.getAddress());
            }
        }
        return convertView;

    }
}
