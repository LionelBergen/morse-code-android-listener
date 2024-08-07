package com.example.hc06bluetoothreceiver;

import java.util.Map;

import android.annotation.SuppressLint;
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
import android.widget.Toast;

import com.example.hc06bluetoothreceiver.util.bluetooth.BluetoothUtil;

@SuppressLint("MissingPermission")
public class DeviceListActivity extends Activity
{
    protected static final String TAG = "TAG";
    private static final String NO_DEVICES_ERROR_MESSAGE = "No bluetooth devices found";
    private static final String DEVICE_NOT_AVAILABLE_ERROR_MESSAGE = "Device Connection Failed!";
    private BluetoothAdapter bluetoothAdapter;
    Map<String, BluetoothDevice> pairedDevices;
    private ArrayAdapter<String> pairedDevicesArrayAdapter;

    @Override
    protected void onCreate(Bundle mSavedInstanceState)
    {
        super.onCreate(mSavedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.device_list_activity);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
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

        pairedDevices = BluetoothUtil.GetPairedDevices();

        if (pairedDevices.size() > 0)
        {
            findViewById(R.id.paird_devices_text_view).setVisibility(View.VISIBLE);
            pairedDevicesArrayAdapter.addAll(pairedDevices.keySet());
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
            Bundle bundle = new Bundle();
            Intent resultIntent = new Intent();
            String deviceInfo = ((TextView) mView).getText().toString();
            BluetoothDevice deviceClicked = pairedDevices.get(deviceInfo);
            String deviceAddress = deviceInfo.substring(deviceInfo.length() - 17);

            Log.v(TAG, "Device_Address " + deviceInfo);
            Log.v(TAG, "Device_Address " + deviceAddress);

            boolean activeConnectionCreated = BluetoothUtil.createActiveConnection(deviceClicked);
            if (!activeConnectionCreated) {
                runOnUiThread(() -> {
                    showToastLong(DEVICE_NOT_AVAILABLE_ERROR_MESSAGE);
                });
            } else {
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }

        }
    };

}