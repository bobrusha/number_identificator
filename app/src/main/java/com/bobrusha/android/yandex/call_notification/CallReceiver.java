package com.bobrusha.android.yandex.call_notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;

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
}
