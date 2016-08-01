package com.bobrusha.android.yandex.call_notification;

import android.content.Context;
import android.graphics.PixelFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by Aleksandra on 01/08/16.
 */
public class InfoNotification {
    public static final String DEBUG_TAG = InfoNotification.class.getName();
    private static WindowManager windowManager;
    private static ViewGroup windowLayout;

    public void showWindow(Context context, String phone) {
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_SYSTEM_ERROR,
                0,
                PixelFormat.TRANSLUCENT);

        params.gravity = Gravity.TOP;

        windowLayout = (ViewGroup) layoutInflater.inflate(R.layout.info_view, null);
        ImageButton buttonClose = (ImageButton) windowLayout.findViewById(R.id.buttonClose);

        TextView textViewMainInformation = (TextView) windowLayout.findViewById(R.id.textViewPhoneInformation);

        SeacherManager.getInstance().loadSearchResultAsync(phone, textViewMainInformation);
        buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeWindow();
            }
        });

        windowManager.addView(windowLayout, params);
    }

    public void closeWindow() {
        Log.d(DEBUG_TAG, "in close window");
        if (windowLayout != null) {
            windowManager.removeView(windowLayout);
            windowLayout = null;
        } else {
            Log.d(DEBUG_TAG, "windowLayout is null");
        }
    }
}
