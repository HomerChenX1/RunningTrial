package com.example.runningtrial.base;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;

import java.lang.reflect.Field;
import java.util.logging.Logger;

public class Utils {
    public static int getResId(String resName, Class<?> c) {
        try {
            Field idField = c.getDeclaredField(resName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static void getCurrentDisplayMetrics(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        Log.d("DisplayMetrics", "screenWidth:" + dm.widthPixels + "  "
                +"screenHeight:" + dm.heightPixels + "\n"
                + "xdp(寬):" + dm.xdpi + "  "
                + "ydp(高):" + dm.ydpi + "\n"
                + "density:" + dm.density + "  "
                + "scaledDensity:" + dm.scaledDensity + "  "
                + "dpi:" + dm.densityDpi);
    }

    public static interface FragmentBackHandler {
        boolean onBackPressed();
    }

    public static interface CommonCallBack {
        public void callBack(int status, Object msg);
    }

    public static interface AsyncChain {
        // asyncChain.setNext(next).startUp(initMsg), wait Async complete
        // asyncChain.shutDown(status, statusMsg) -> internal call next.setNext(next2).startUp(initMsgUpdated),
        // public void setNext(AsyncChain next);  put into new AsyncChain()
        public void startUp(Object msg);
        public void shutDown(int status, Object statusMsg);
    }
}
