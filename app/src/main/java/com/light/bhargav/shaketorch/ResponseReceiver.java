package com.light.bhargav.shaketorch;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

/**
 * Created by bhargav on 27/12/15.
 */
public class ResponseReceiver extends BroadcastReceiver {
    public static final String ACTION_RESP =
            "com.mamlambo.intent.action.MESSAGE_PROCESSED";

    @Override
    public void onReceive(Context context, Intent intent) {
        //TextView result = (TextView) findViewById(R.id.txt_result);
        //String text = intent.getStringExtra(MyService.PARAM_OUT_MSG);
        //result.setText(text);
    }
}