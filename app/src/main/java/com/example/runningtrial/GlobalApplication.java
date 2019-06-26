package com.example.runningtrial;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

// activity.getApplication()
// context.getApplicationContext()
// GlobalApplication getRef()
//many debug tools and Firebase use this
public class GlobalApplication extends Application {
    private final String TAG = this.getClass().getSimpleName();
    private static Context app = null;

    public static Context getAppContext() { return app; }

    @Override
    public void onCreate() {
        super.onCreate();
        if (app == null) app = getApplicationContext();
        Log.d(TAG, "start up!");

        EventBus.builder().addIndex(new MyEventBusIndex()).installDefaultEventBus();
        // Now the default instance uses the given index. Use it like this:
        EventBus eventBus = EventBus.getDefault();
    }

}
