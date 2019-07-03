package com.example.runningtrial.base;

import android.util.Log;
import android.widget.TextView;

public class DataWarehouse {
    private static final DataWarehouse ourInstance = new DataWarehouse();
    private DataWarehouse() { }
    public static DataWarehouse getRef() {
        return ourInstance;
    }

    public TextView tvDebug = null;

    public void logd(String tag, String msg){
        Log.d(tag, msg);
        if (tvDebug != null)
            tvDebug.setText(msg);
    }
}

