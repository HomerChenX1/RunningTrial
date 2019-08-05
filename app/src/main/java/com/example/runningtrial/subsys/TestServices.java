package com.example.runningtrial.subsys;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;

public class TestServices extends Service {
    String TAG = getClass().getSimpleName();
    private PowerManager.WakeLock wakeLock;
    boolean notSleep = true;
    public TestServicesBinder myBinder = new TestServicesBinder();
    boolean isStarted = false;
    boolean isBound = false;
    boolean stopFlag = true;
    boolean isForeground = true;
    // start -service can include bind-service
    // boolean isStartServiceOnly = false;
    // boolean isBindServiceOnly=true;


    // 綁定此 Service 的物件
    public class TestServicesBinder extends Binder {
        public TestServices getService() {
            return TestServices.this;
        }
    }

    public TestServices() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (isForeground) {
            NotificationBase notificationBase = new NotificationBase(this);
            Notification notification = notificationBase.setImportance(NotificationManager.IMPORTANCE_HIGH).createChannel()
                    .sendPrepare().build();

            startForeground(notificationBase.getNOTIFICATION_ID(), notification);
        }
        Log.d(TAG, "onCreate");
        if (notSleep)
            preventSleeping();
    }

    private void preventSleeping(){
        // 避免睡著
        PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, TAG + ":" + "wakeLock");
        wakeLock.acquire();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, " onStartCommand" + " startId: " + startId + " flags: " + flags);
        if (intent!=null) {
            isStarted = intent.getExtras().getBoolean("isStarted");
            isBound = intent.getExtras().getBoolean("isBound");
            stopFlag = intent.getExtras().getBoolean("stopFlag");
        } else {
            Log.d(TAG, "The Service is killed and reborn !");
        }

        if (!stopFlag)
            doTestStart();
        if (notSleep){
            // return START_STICKY or  START_REDELIVER_INTENT
            return START_STICKY;
        }
        return super.onStartCommand(intent, flags, startId);  // the last statement
    }

    private void doTestStart() {
        new Thread(new Runnable() {
            int i=0;
            @Override
            public void run() {
                while(!stopFlag){
                    i++;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.d(TAG, "onStartCommand: " + i);
                }
            }
        }).start();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // if StartService only, return null
        Log.d(TAG, "onBind");
        return myBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        // return super.onUnbind(intent);
        Log.d(TAG, "onUnbind");
        return super.onUnbind(intent);  // or return false
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        Log.d(TAG, "onRebind");
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        stopFlag = true;
        wakeLock.release();
        super.onDestroy();  // has better the last statement
    }

    // Service 測試用的 Method
    public void doTest(){
        Log.d(TAG, "doTest!");
        stopFlag = true;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
    }
}
