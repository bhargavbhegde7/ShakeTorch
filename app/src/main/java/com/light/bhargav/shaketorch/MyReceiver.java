package com.light.bhargav.shaketorch;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by bhargav on 27/12/15.
 */
public class MyReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("ddddssssaaaa");
        Intent i = new Intent();
        i.setClassName(context, "com.light.bhargav.shaketorch.ShakeActivity");
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
}
