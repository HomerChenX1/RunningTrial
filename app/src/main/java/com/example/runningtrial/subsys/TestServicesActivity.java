package com.example.runningtrial.subsys;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.runningtrial.R;

public class TestServicesActivity extends AppCompatActivity {
    String TAG = getClass().getSimpleName();
    boolean isStarted = false;
    boolean isBound = false;
    boolean stopFlag = false; // used for StartService test;
    boolean isForeground = true;
    private TestServices testServices;
    Button btnTestServiceStartService, btnTestServiceBindService, btnTestServiceBindTest, btnTestServiceUnbind;
    Button btnTestServiceStopService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_services);

        btnTestServiceStartService = findViewById(R.id.btnTestServiceStartService);
        btnTestServiceStartService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doStartService();
            }
        });

        btnTestServiceBindService = findViewById(R.id.btnTestServiceBindService);
        btnTestServiceBindService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doBindService();
            }
        });

        btnTestServiceBindTest = findViewById(R.id.btnTestServiceBindTest);
        btnTestServiceBindTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doTests();
            }
        });
        btnTestServiceUnbind = findViewById(R.id.btnTestServiceUnbind);
        btnTestServiceUnbind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doUnbindService();
            }
        });
        btnTestServiceStopService = findViewById(R.id.btnTestServiceStopService);
        btnTestServiceStopService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doStopService();
            }
        });
    }

    private void doStopService() {
        // if (!isStarted)
            // return;
        if (isBound) {
            // need unbound first
            doUnbindService();
        }
        Log.d(TAG, "doStopService");
        isStarted = false;
        isBound = false;
        stopFlag = true;
        // start stop
        Intent intent = new Intent(this, TestServices.class);
        // intent.putExtras(setParemeters());
        // startService(intent);
        stopService(intent);  // will call TestServices: onDestroy

    }

    private void doStartService() {
        if (isStarted)
            return;
        if (isBound) {
            // can not start
            Toast.makeText(getApplicationContext(), "Service is bound, can not start!", Toast.LENGTH_SHORT)
                    .show();
        }
        // need to try 2 App use the same startActivity issue
        Log.d(TAG, "doStartService");
        isStarted = true;
        isBound = false;
        stopFlag = false;
        Intent intent = new Intent(this, TestServices.class);
        intent.putExtras(setParemeters());
        if (! isForeground) {
            startService(intent);
            return;
        }
        // it is foreground service
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent);
        } else {
            startService(intent);
        }
    }

    void doBindService() {
        if (!isBound) {
            Log.d(TAG, "doBindService");
            Intent intent = new Intent(this, TestServices.class);
            intent.putExtras(setParemeters());
            bindService(intent, serviceCon, Context.BIND_AUTO_CREATE);
            isBound = true;
        }
    }

    void doUnbindService() {
        if (isBound) {
            Log.d(TAG, "doUnbindService");
            unbindService(serviceCon);
            isBound = false;
        }
    }

    private ServiceConnection serviceCon = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder binder) {
            testServices = ((TestServices.TestServicesBinder) binder).getService();
            Log.d(TAG, "onServiceConnected: " + className);
            // doTests();
        }

        @Override
        public void onServiceDisconnected(ComponentName className) {
            testServices = null;
            Log.d(TAG, "onServiceDisconnected: " + className);
        }
    };

    void doTests(){
        // how to make sure service is ready??
        // need an async call back
        testServices.doTest();
    }

    private Bundle setParemeters () {
        Bundle bundle = new Bundle();
        bundle.putBoolean("isStarted", isStarted);
        bundle.putBoolean("isBound", isBound);
        bundle.putBoolean("stopFlag", stopFlag);
        bundle.putBoolean("isForeground", isForeground);
        return bundle;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        doUnbindService();  //necessry because service is async behavior.
    }
}
