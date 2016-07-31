package com.bobrusha.android.yandex.call_notification;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import static android.os.Build.VERSION;
import static android.os.Build.VERSION_CODES;

public class MainActivity extends AppCompatActivity {
    public static final String DEBUG_TAG = MainActivity.class.getName();

    private static int OVERLAY_PERMISSION_REQ_CODE = 1234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 0);
        if (VERSION.SDK_INT >= VERSION_CODES.M) {
            Intent myIntent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
            startActivityForResult(myIntent, OVERLAY_PERMISSION_REQ_CODE);
        }
    }

    @TargetApi(VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == OVERLAY_PERMISSION_REQ_CODE) {
            if (!Settings.canDrawOverlays(this)) {
                Log.d(DEBUG_TAG, "no ");
            }
        }
    }
}
