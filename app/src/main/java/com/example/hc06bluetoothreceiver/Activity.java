package com.example.hc06bluetoothreceiver;

import android.widget.Toast;

public class Activity extends android.app.Activity {
    public void showToast(String text, int length) {
        runOnUiThread(() -> {
            Toast.makeText(getApplicationContext(), text, length).show();
        });
    }

    public void showToastLong(String text) {
        showToast(text, Toast.LENGTH_LONG);
    }

    public void showToast(String text) {
        showToast(text, Toast.LENGTH_SHORT);
    }
}
