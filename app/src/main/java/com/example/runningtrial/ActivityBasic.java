package com.example.runningtrial;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

// https://www.jianshu.com/p/f45b46be671e
public class ActivityBasic extends AppCompatActivity {
    private String TAG = getClass().getSimpleName();
    private Bundle savedBundle;  // google suggest to user sharedPreferences not bundle

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBundle(savedInstanceState);
        initData(savedBundle);
    }

    // override the function and put data into the savedBundle
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (this.savedBundle != null)
            outState.putBundle("savedBundle", this.savedBundle);
        super.onSaveInstanceState(outState);
    }

    private void initBundle(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            // normal process
            savedBundle = getIntent().getExtras();
        } else {
            // activity be killed and restore
            savedBundle = savedInstanceState.getBundle("savedBundle");
        }

        //如果没有任何参数，则初始化 savedBundle，避免调用时 null pointer
        if (savedBundle == null) {
            savedBundle = new Bundle();
        }
    }

    // processing savedBundle, usually contains data from startActivityFromResult
    protected void initData(Bundle savedBundle) { }

}
