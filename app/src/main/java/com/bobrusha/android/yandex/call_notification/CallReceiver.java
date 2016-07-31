package com.bobrusha.android.yandex.call_notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by Aleksandra on 31/07/16.
 */
public class CallReceiver extends BroadcastReceiver {
    public static final String DEBUG_TAG = CallReceiver.class.getName();

    public static final String PHONE_STATE_ACTION = "android.intent.action.PHONE_STATE";

    private boolean incomingCall = false;


    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals(PHONE_STATE_ACTION)) {
            String phoneState = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            if (phoneState.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                String phoneNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
                incomingCall = true;
                showWindow(context, phoneNumber);
                Log.d(DEBUG_TAG, "Show window:" + phoneNumber);

            } else if (phoneState.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
                if (incomingCall) {
                    Log.d(DEBUG_TAG, "Close window.");
                    incomingCall = false;
                }
            } else if (phoneState.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
                if (incomingCall) {
                    Log.d(DEBUG_TAG, "Close window.");
                    incomingCall = false;
                }
            }
        }
    }

    private static WindowManager windowManager;
    private static ViewGroup windowLayout;

    private void showWindow(Context context, String phone) {
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED,
                PixelFormat.TRANSLUCENT);
        params.gravity = Gravity.TOP;

        windowLayout = (ViewGroup) layoutInflater.inflate(R.layout.info_view, null);

        TextView textViewNumber = (TextView) windowLayout.findViewById(R.id.textViewNumber);
        ImageButton buttonClose = (ImageButton) windowLayout.findViewById(R.id.buttonClose);
        textViewNumber.setText(phone);
        buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeWindow();
            }
        });

        windowManager.addView(windowLayout, params);
    }

    private void closeWindow() {
        if (windowLayout != null) {
            windowManager.removeView(windowLayout);
            windowLayout = null;
        }
    }
}
