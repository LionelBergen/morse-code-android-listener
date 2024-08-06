package com.example.hc06bluetoothreceiver;

import java.util.Set;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

@SuppressLint("MissingPermission")
public class DeviceListActivity extends Activity
{
    protected static final String TAG = "TAG";
    private static final String NO_DEVICES_ERROR_MESSAGE = "No bluetooth devices found";
    private BluetoothAdapter bluetoothAdapter;
    private ArrayAdapter<String> pairedDevicesArrayAdapter;

    @Override
    protected void onCreate(Bundle mSavedInstanceState)
    {
        super.onCreate(mSavedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.device_list_activity);
    }

    @Override
    public void onStart()
    {
        super.onStart();

        setResult(Activity.RESULT_CANCELED);
        pairedDevicesArrayAdapter = new ArrayAdapter<String>(this, R.layout.device_name);

        ListView pairedDevicesListView = (ListView) findViewById(R.id.paired_devices_list_view);
        pairedDevicesListView.setAdapter(pairedDevicesArrayAdapter);
        pairedDevicesListView.setOnItemClickListener(deviceClickListener);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();

        if (pairedDevices.size() > 0)
        {
            findViewById(R.id.paird_devices_text_view).setVisibility(View.VISIBLE);
            for (BluetoothDevice device : pairedDevices)
            {
                pairedDevicesArrayAdapter.add(device.getName() + "\n" + device.getAddress());
            }
        }
        else
        {
            pairedDevicesArrayAdapter.add(NO_DEVICES_ERROR_MESSAGE);
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        if (bluetoothAdapter != null)
        {
            bluetoothAdapter.cancelDiscovery();
        }
    }

    private OnItemClickListener deviceClickListener = new OnItemClickListener()
    {
        public void onItemClick(AdapterView<?> mAdapterView, View mView, int mPosition, long mLong)
        {
            bluetoothAdapter.cancelDiscovery();
            String deviceInfo = ((TextView) mView).getText().toString();
            Log.v(TAG, "Device_Address " + deviceInfo);
            String deviceAddress = deviceInfo.substring(deviceInfo.length() - 17);
            Log.v(TAG, "Device_Address " + deviceAddress);

            Bundle bundle = new Bundle();
            bundle.putString("DeviceAddress", deviceAddress);
            Intent backIntent = new Intent();
            backIntent.putExtras(bundle);
            setResult(Activity.RESULT_OK, backIntent);
            finish();
        }
    };

}