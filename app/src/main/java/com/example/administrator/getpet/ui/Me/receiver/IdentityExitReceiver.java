package com.example.administrator.getpet.ui.Me.receiver;

import android.app.Activity;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;

/**
 * Created by Administrator on 2016/6/6.
 */
public class IdentityExitReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (context instanceof Activity)
        {
            ((Activity) context).finish();
        }
        else if (context instanceof FragmentActivity)
        {
            ((FragmentActivity) context).finish();
        }
        else if (context instanceof Service)
        {
            ((Service) context).stopSelf();
        }
    }
}
