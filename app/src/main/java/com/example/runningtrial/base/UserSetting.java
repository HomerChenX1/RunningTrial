package com.example.runningtrial.base;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.runningtrial.GlobalApplication;

/**
 *  Access data/setting persistently. Implement by SharedPreferences
 */
public class UserSetting {
    private static final UserSetting ourInstance = new UserSetting();
    private String TAG = this.getClass().getSimpleName();
    private SharedPreferences sharedPref = null;
    private UserSetting() {
        if (sharedPref == null) {
            sharedPref = GlobalApplication.getAppContext()
                    .getSharedPreferences(TAG, Context.MODE_PRIVATE);
        }
    }
    static UserSetting getRef() { return ourInstance; }

    void resetSharedPref() { sharedPref = null; };
    String TAG2 = "run";

}
