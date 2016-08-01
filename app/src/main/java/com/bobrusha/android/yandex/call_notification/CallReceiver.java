package com.bobrusha.android.yandex.call_notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;

/**
 * Created by Aleksandra on 31/07/16.
 */
public class CallReceiver extends BroadcastReceiver {
    public static final String DEBUG_TAG = CallReceiver.class.getName();

    public static final String PHONE_STATE_ACTION = "android.intent.action.PHONE_STATE";

    private boolean incomingCall = false;

    private InfoNotification notification = new InfoNotification();

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals(PHONE_STATE_ACTION)) {
            String phoneState = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            if (phoneState.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                String phoneNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
                incomingCall = true;
                notification.showWindow(context, phoneNumber);
            } else if (phoneState.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
                    notification.closeWindow();
                    incomingCall = false;

            } else if (phoneState.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
                    notification.closeWindow();
                    incomingCall = false;
            }
        }
    }
}
