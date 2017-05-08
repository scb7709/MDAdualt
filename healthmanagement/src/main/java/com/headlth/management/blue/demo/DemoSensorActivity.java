package com.headlth.management.blue.demo;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.MenuItem;

import com.headlth.management.activity.BaseActivity;
import com.headlth.management.blue.BleService;
import com.headlth.management.blue.sensor.BleSensor;
import com.headlth.management.blue.sensor.BleSensors;


/**
 * Created by steven on 9/5/13.
 * Modified by olli on 3/28/2014.
 */
public abstract class DemoSensorActivity extends BaseActivity {
    private final static String TAG = DemoSensorActivity.class.getSimpleName();

    public static final String EXTRAS_DEVICE_ADDRESS = "DEVICE_ADDRESS";
    public static final String EXTRAS_SENSOR_UUID = "SERVICE_UUID";

    private BleService bleService;
    private String serviceUuid;
    private String deviceAddress;

    // Handles various events fired by the Service.
    // ACTION_GATT_CONNECTED: connected to a GATT server.
    // ACTION_GATT_DISCONNECTED: disconnected from a GATT server.
    // ACTION_GATT_SERVICES_DISCOVERED: discovered GATT services.
    // ACTION_DATA_AVAILABLE: received data from the device.  This can be a result of read
    //                        or notification operations.
    private final BroadcastReceiver gattUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (BleService.ACTION_GATT_DISCONNECTED.equals(action)) {
                //TODO: show toast
                finish();
            } else if (BleService.ACTION_GATT_SERVICES_DISCOVERED.equals(action)) {
                final BleSensor<?> sensor = BleSensors.getSensor(serviceUuid);
                bleService.enableSensor(sensor, true);
            } else if (BleService.ACTION_DATA_AVAILABLE.equals(action)) {
                final BleSensor<?> sensor = BleSensors.getSensor(serviceUuid);
                final String text = intent.getStringExtra(BleService.EXTRA_TEXT);
                onDataRecieved(sensor, text);
            }
        }
    };

    // Code to manage Service lifecycle.
    private final ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            bleService = ((BleService.LocalBinder) service).getService();
            if (!bleService.initialize()) {
                Log.e(TAG, "Unable to initialize Bluetooth");
                finish();
            }
            // Automatically connects to the device upon successful start-up initialization.
            bleService.connect(deviceAddress);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            bleService = null;
            //TODO: show toast
            finish();
        }
    };

    public abstract void onDataRecieved(BleSensor<?> sensor, String text);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Intent intent = getIntent();
        deviceAddress = intent.getStringExtra(EXTRAS_DEVICE_ADDRESS);
        serviceUuid = intent.getStringExtra(EXTRAS_SENSOR_UUID);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        final Intent gattServiceIntent = new Intent(this, BleService.class);
        bindService(gattServiceIntent, serviceConnection, BIND_AUTO_CREATE);
    }

    @Override
    public void onResume() {
        super.onResume();

        registerReceiver(gattUpdateReceiver, makeGattUpdateIntentFilter());
        if (bleService != null) {
            final boolean result = bleService.connect(deviceAddress);
            Log.d(TAG, "Connect request result=" + result);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(gattUpdateReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(serviceConnection);
        bleService = null;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private static IntentFilter makeGattUpdateIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BleService.ACTION_GATT_SERVICES_DISCOVERED);
        intentFilter.addAction(BleService.ACTION_GATT_DISCONNECTED);
        intentFilter.addAction(BleService.ACTION_DATA_AVAILABLE);
        return intentFilter;
    }
}
