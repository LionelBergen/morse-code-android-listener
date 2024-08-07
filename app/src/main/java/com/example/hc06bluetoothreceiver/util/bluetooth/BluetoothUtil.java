package com.example.hc06bluetoothreceiver.util.bluetooth;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;
import android.widget.ArrayAdapter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@SuppressLint("MissingPermission")
public class BluetoothUtil {
    private static BluetoothSocket activeConnection;
    private static BluetoothDevice connectedDevice;
    private static final UUID connectionUUID = UUID.randomUUID();
    protected static final String TAG = "BluetoothUtil";

    public static Map<String, BluetoothDevice> GetPairedDevices() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();

        Map<String, BluetoothDevice> devicesList = pairedDevices.stream()
                .collect(Collectors.toMap(BluetoothUtil::getDisplayKey, e -> e));

        return devicesList;
    }

    public static boolean createActiveConnection(BluetoothDevice device) {
        Log.v(TAG, "Attempting to connect to: " + device.getAddress());
        try {
            activeConnection = device.createRfcommSocketToServiceRecord(connectionUUID);
            activeConnection.connect();
            connectedDevice = device;
            return true;
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }

        return false;
    }

    public static BluetoothDevice getConnectedDevice() {
        return connectedDevice;
    }

    private static String getDisplayKey(BluetoothDevice device) {
        return device.getName() + "\n" + device.getAddress();
    }
}
