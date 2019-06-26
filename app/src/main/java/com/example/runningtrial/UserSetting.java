package com.example.runningtrial;

import android.content.Context;
import android.content.SharedPreferences;

/**
 *  Access data/setting persistently. Implement by SharedPreferences
 */
class UserSetting {
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
