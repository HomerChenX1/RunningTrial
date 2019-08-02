package com.example.runningtrial.subsys;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class SnoozeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        final int notifyID = intent.getIntExtra("snooze_notify_id", 0);
        final NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE); // 取得系統的通知服務
        String action = intent.getAction();
        Toast.makeText(context, "Action is: "+action, Toast.LENGTH_LONG);
        notificationManager.cancel(notifyID);  // necessary
    }
}
