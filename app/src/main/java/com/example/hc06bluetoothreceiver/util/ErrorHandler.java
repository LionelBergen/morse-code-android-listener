package com.example.hc06bluetoothreceiver.util;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

public class ErrorHandler {
    private Context context;
    private Activity activity;

    public ErrorHandler(Activity activity) {
        this.context = activity.getApplicationContext();
        this.activity = activity;
    }

    public static void error(Activity activity, String error) {
        Toast.makeText(activity.getApplicationContext(), error, Toast.LENGTH_LONG).show();
        activity.finish();
    }
}
