package com.example.huynhhq.smartlightbulb;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.huynhhq.smartlightbulb.Adapter.CustomAdapter;
import com.example.huynhhq.smartlightbulb.Adapter.DeviceListAdapter;
import com.example.huynhhq.smartlightbulb.Model.DeviceItem;

import java.util.ArrayList;
import java.util.Set;

public class ScanBulbsActivity extends AppCompatActivity {
    private static final String TAG = "ScanBulbsActivity";

    private Button btn_scan;
    final Context context = this;
    BluetoothAdapter mBluetoothAdapter;
    Button btnEnableDisable_Discoverable;
    public ArrayList<DeviceItem> mBTDevices = new ArrayList<>();
    public ArrayList<DeviceItem> mBTPairDevices = new ArrayList<>();
    public DeviceListAdapter mDeviceListAdapter;
    ListView lvNewDevices, lvPairDevices;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_bulbs);
        Toolbar toolbar = (Toolbar) findViewById(R.id.mCustomToolbar);
        toolbar.setTitle(R.string.scan_bulb_title);
        setSupportActionBar(toolbar);

        lvNewDevices = (ListView) findViewById(R.id.lvNewDevices);
        lvPairDevices = (ListView) findViewById(R.id.lvPairDevices);
        mBTDevices = new ArrayList<>();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        enableDisableBT();

        getPairDevices();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            super.onBackPressed();
            overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }

    private final BroadcastReceiver mBroadcastReceiver1 = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            // When discovery finds a device
            if (action.equals(mBluetoothAdapter.ACTION_STATE_CHANGED)) {
                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, mBluetoothAdapter.ERROR);

                switch (state) {
                    case BluetoothAdapter.STATE_OFF:
                        Log.d(TAG, "onReceive: STATE OFF");
                        break;
                    case BluetoothAdapter.STATE_TURNING_OFF:
                        Log.d(TAG, "mBroadcastReceiver1: STATE TURNING OFF");
                        break;
                    case BluetoothAdapter.STATE_ON:
                        Log.d(TAG, "mBroadcastReceiver1: STATE ON");
                        break;
                    case BluetoothAdapter.STATE_TURNING_ON:
                        Log.d(TAG, "mBroadcastReceiver1: STATE TURNING ON");
                        break;
                }
            }
        }
    };

    private BroadcastReceiver mBroadcastReceiver3 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            Log.d(TAG, "onReceive: ACTION FOUND.");
            boolean checkAdd = false;

            if (action.equals(BluetoothDevice.ACTION_FOUND)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                DeviceItem newDevice = new DeviceItem(device.getName(), device.getAddress(), "false");
                if (mBTDevices.size() > 0) {
                    for (DeviceItem item : mBTDevices
                            ) {
                        if (item.getAddress().equals(newDevice.getAddress())) {
                            Log.d(TAG, "onReceive: Co may roi nhe" + item.getAddress() + newDevice.getAddress());
                            checkAdd = true;
                        }
                    }
                    if (!checkAdd) {
                        mBTDevices.add(newDevice);
                        mDeviceListAdapter = new DeviceListAdapter(context, R.layout.device_list_item, mBTDevices);
                        lvNewDevices.setAdapter(mDeviceListAdapter);
                    }
                } else {
                    Log.d(TAG, "onReceive: Vo day dau tien moi dung");
                    mBTDevices.add(newDevice);
                    Log.d(TAG, "onReceive: " + device.getName() + ": " + device.getAddress());
                    mDeviceListAdapter = new DeviceListAdapter(context, R.layout.device_list_item, mBTDevices);
                    lvNewDevices.setAdapter(mDeviceListAdapter);
                }
            }
        }
    };

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy: called.");
        super.onDestroy();
        unregisterReceiver(mBroadcastReceiver1);
        mBluetoothAdapter.cancelDiscovery();
    }

    public void btnScan(View view) {
        Log.d(TAG, "btnDiscover: Looking for unpaired devices.");

        if (mBluetoothAdapter.isDiscovering()) {
            mBluetoothAdapter.cancelDiscovery();
            Log.d(TAG, "btnDiscover: Canceling discovery.");

            //check BT permissions in manifest
            checkBTPermissions();

            mBluetoothAdapter.startDiscovery();
            IntentFilter discoverDevicesIntent = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            registerReceiver(mBroadcastReceiver3, discoverDevicesIntent);
        }
        if (!mBluetoothAdapter.isDiscovering()) {

            //check BT permissions in manifest
            checkBTPermissions();

            mBluetoothAdapter.startDiscovery();
            IntentFilter discoverDevicesIntent = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            registerReceiver(mBroadcastReceiver3, discoverDevicesIntent);
        }
    }

    public void enableDisableBT() {
        if (mBluetoothAdapter == null) {
            Log.d(TAG, "enableDisableBT: Does not have BT capabilities.");
        }
        if (!mBluetoothAdapter.isEnabled()) {
            Log.d(TAG, "enableDisableBT: enabling BT.");
            Intent enableBTIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(enableBTIntent);

            IntentFilter BTIntent = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
            registerReceiver(mBroadcastReceiver1, BTIntent);
        }

        IntentFilter BTIntent = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        registerReceiver(mBroadcastReceiver1, BTIntent);


    }

    private void checkBTPermissions() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            int permissionCheck = this.checkSelfPermission("Manifest.permission.ACCESS_FINE_LOCATION");
            permissionCheck += this.checkSelfPermission("Manifest.permission.ACCESS_COARSE_LOCATION");
            if (permissionCheck != 0) {

                this.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1001); //Any number
            }
        } else {
            Log.d(TAG, "checkBTPermissions: No need to check permissions. SDK version < LOLLIPOP.");
        }
    }

    private void getPairDevices() {
        Log.d(TAG, "First time: Looking for paired devices.");
        mBTPairDevices = new ArrayList<>();
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                DeviceItem newPairDevice= new DeviceItem(device.getName(),device.getAddress(),"false");
                mBTPairDevices.add(newPairDevice);
                mDeviceListAdapter = new DeviceListAdapter(context, R.layout.device_list_item, mBTPairDevices);
                lvPairDevices.setAdapter(mDeviceListAdapter);
            }
        }
    }
}
