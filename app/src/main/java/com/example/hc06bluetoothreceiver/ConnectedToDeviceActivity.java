package com.example.hc06bluetoothreceiver;

import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.example.hc06bluetoothreceiver.util.bluetooth.BluetoothUtil;
import com.example.hc06bluetoothreceiver.util.bluetooth.thread.ConnectionThread;
import com.example.hc06bluetoothreceiver.util.ui.MessageHandler;


public class ConnectedToDeviceActivity extends Activity {
    private ConnectionThread ct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connected_to_device);
    }

    @Override
    protected void onStart() {
        super.onStart();

        TextView view = findViewById(R.id.deviceView);
        Handler handler = new MessageHandler(view, this);
        ct = new ConnectionThread(BluetoothUtil.getActiveConnection(), handler);
        ct.start();
    }
}