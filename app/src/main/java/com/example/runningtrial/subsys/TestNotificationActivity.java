package com.example.runningtrial.subsys;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.appcompat.app.AppCompatActivity;

import com.example.runningtrial.R;

import java.io.Serializable;

import static androidx.core.app.NotificationCompat.DEFAULT_LIGHTS;
import static androidx.core.app.NotificationCompat.DEFAULT_SOUND;

public class TestNotificationActivity extends AppCompatActivity {
    private String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_notification);

        setTitle("TestNotificationActivity");

        new NotificationBase(this).createChannel()
                .sendPrepare().setStyle()
                // .sendBundle("email", TestNoti2Activity.class,
                //         new SimpleMsg("Hello Android", "Welcome to the Android world!"))
                .setButton()
                .send();

    }
}

class NotificationBase {
    String TAG = getClass().getSimpleName();

    // needed for Notification
    Context context;
    NotificationManagerCompat notificationManager;
    NotificationCompat.Builder builder;
    int iconId = android.R.drawable.ic_dialog_email; // R.drawable.notification_icon
    int largeIconResId = R.drawable.icons8_puffin_bird_48;
    Bitmap largeIcon;
    String title = "Hello";
    String content = "Work Hard!";
    int NOTIFICATION_ID = 1;  // 使用參數指定的編號發出通知，如果這個編號的通知已經存在，就使用通知物件更新原來通知的內容。
    // NOTIFICATION_ID can not be 0
    public final static String ACTION_SNOOZE = "SNOOZE";

    // needed for Notification Channel
    CharSequence name = "Love channel";
    String description = "最重要的人";
    String CHANNEL_ID = "idLove";
    int importance = NotificationManager.IMPORTANCE_DEFAULT;

    public NotificationBase setImportance(int importance) {
        this.importance = importance;
        return this;
    }

    public int getNOTIFICATION_ID() { return NOTIFICATION_ID; }

    public NotificationBase(Context context) {
        this.context = context;
        notificationManager = NotificationManagerCompat.from(context);
        largeIcon = BitmapFactory.decodeResource(context.getResources(), largeIconResId);
    }

    protected NotificationBase sendPrepare() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            builder = new NotificationCompat.Builder(context, CHANNEL_ID);
        else
            //noinspection deprecation
            builder = new NotificationCompat.Builder(context);

        builder.setContentTitle(title).setContentText(content).setContentInfo("ContentInfo")
                .setSmallIcon(iconId)
                .setTicker("content")
                // .setOngoing(true)
                // .setLargeIcon(largeIcon)
                .setWhen(System.currentTimeMillis())
                // .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);
        return this;
    }

    public NotificationBase setStyle(){
        // setDefault() = setSound(), setVibrate, setLight
        builder.setDefaults(DEFAULT_SOUND | DEFAULT_LIGHTS)
                .setStyle(new NotificationCompat.BigTextStyle()
                .bigText("Much longer text that cannot fit one line..."));
        return this;
    }

    public NotificationBase sendBundle(String tag, Class<?> cls, Serializable obj) {
        Intent intent = new Intent(context, cls);

        Bundle bundle = new Bundle();
        bundle.putSerializable(tag, obj);
        intent.putExtras(bundle);

        PendingIntent pendingIntent =
                PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        // to add button
        // builder.addAction(R.drawable.ic_snooze, getString(R.string.snooze), snoozePendingIntent);
        return this;
    }

    public NotificationBase setButton(){
        Intent snoozeIntent = new Intent(context, SnoozeReceiver.class);
        snoozeIntent.putExtra("snooze_notify_id", NOTIFICATION_ID);

        snoozeIntent.setAction(ACTION_SNOOZE);

        PendingIntent snoozePendingIntent =
                PendingIntent.getBroadcast(context, 0, snoozeIntent, 0);
        // ic_dove not show in emulator but show in Nexus 7
        builder.addAction(R.drawable.ic_dove, context.getString(R.string.snooze),
                snoozePendingIntent);
        return this;
    }

    public void send(){
        // for old style, use Notification notification
        // for new style, use NotificationCompat notification
        Notification notification = builder.build();
        notificationManager.notify(NOTIFICATION_ID, notification);
    }

    public Notification build(){
        // for old style, use Notification notification
        // for new style, use NotificationCompat notification
        Notification notification = builder.build();
        return notification;
    }

    public void cancel(){
        notificationManager.cancelAll();
    }

    public void cancel(int NOTIFICATION_ID){
        notificationManager.cancel(NOTIFICATION_ID);
    }

    public NotificationBase createChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // this.context = context;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            channel.enableLights(true);
            channel.enableVibration(true);

            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this, and not chnage NotificationManager to NotificationManagerCompat
            NotificationManager notificationManager = context.getApplicationContext()
                    .getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
        return this;
    }
}

class SimpleMsg implements Serializable {
    public String contentTitle, contentText;

    public SimpleMsg(String contentTitle, String contentText) {
        this.contentTitle = contentTitle;
        this.contentText = contentText;
    }
}
