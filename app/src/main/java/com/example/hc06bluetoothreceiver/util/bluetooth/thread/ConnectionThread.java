package com.example.hc06bluetoothreceiver.util.bluetooth.thread;

import android.bluetooth.BluetoothSocket;
import android.os.Handler;

import java.io.IOException;
import java.io.InputStream;

public class ConnectionThread extends Thread {
    private InputStream inputStream;
    private Handler handler;

    public ConnectionThread(BluetoothSocket socket, Handler handler) {
        this.inputStream = null;
        this.handler = handler;

        // Get the input and output streams, using temp objects because
        // member streams are final
        try {
            inputStream = socket.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        byte[] buffer = new byte[256];
        int bytesRead;

        // Keep listening to the InputStream until an exception occurs
        while (true) {
            try {
                bytesRead = inputStream.read(buffer);
                handler.obtainMessage(1, bytesRead, -1, buffer).sendToTarget();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
