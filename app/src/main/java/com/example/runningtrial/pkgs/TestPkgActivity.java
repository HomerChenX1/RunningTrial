package com.example.runningtrial.pkgs;

import android.accounts.Account;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.runningtrial.R;
import com.example.runningtrial.base.DataWarehouse;

public class TestPkgActivity extends AppCompatActivity {
    String TAG = getClass().getSimpleName();
    public static Account mAccount;

    static HandlerThread handlerThread = null;
    public static Handler mHandler = null;

    Button btnTestSinIn, btnTestOAuth2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_pkg);

        handlerThread = new HandlerThread(TAG);
        handlerThread.start();
        mHandler = new Handler(handlerThread.getLooper());
        setupButton(R.id.btnTestSignIn, "btnTestSinIn", SignInActivity.class);
        btnTestOAuth2 = findViewById(R.id.btnTestOAuth2);
    }

    private void setupButton(int resId, final String s, final Class<?> cls) {
        Button btn = findViewById(resId);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), s, Toast.LENGTH_SHORT).show();
                DataWarehouse.getRef().logd(TAG, s + " is clicked");
                Intent intent = new Intent(v.getContext(), cls);
                startActivity(intent);
            }
        });
    }
}
