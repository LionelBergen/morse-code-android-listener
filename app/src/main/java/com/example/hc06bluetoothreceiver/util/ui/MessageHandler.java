package com.example.hc06bluetoothreceiver.util.ui;

import android.os.Handler;
import android.widget.TextView;

import com.example.hc06bluetoothreceiver.Activity;

public class MessageHandler extends Handler {
    private TextView view;
    private Activity activity;

    public MessageHandler(TextView view, Activity activity) {
        this.view = view;
        this.activity = activity;
    }
    @Override
    public void handleMessage(android.os.Message msg) {
        activity.runOnUiThread(() -> {
            byte[] readBuf = (byte[]) msg.obj;
            String strIncom = new String(readBuf, 0, msg.arg1);
            view.append(strIncom);
        });
    }
}
