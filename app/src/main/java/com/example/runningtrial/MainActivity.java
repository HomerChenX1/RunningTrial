package com.example.runningtrial;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private String TAG = getClass().getSimpleName();
    private TextView tvMain;
    private Button btnMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Log.d(TAG, "onStart");
        DataWarehouse.getRef().tvDebug = tvMain;
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Log.d(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Log.d(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Log.d(TAG, "onStop");
        DataWarehouse.getRef().tvDebug = null;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        // Log.d(TAG, "onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Log.d(TAG, "onDestroy");
    }

    private void findViews() {
        tvMain = findViewById(R.id.tvMain);

        btnMain = findViewById(R.id.btnMain);
        btnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "btnMain", Toast.LENGTH_SHORT).show();
                DataWarehouse.getRef().logd(TAG, "btnMain is clicked");
                // Intent intent = new Intent(v.getContext(), TestFragmentActivity.class);
                // startActivity(intent);

                Utils.getCurrentDisplayMetrics(MainActivity.this);
                Intent intent = new Intent(v.getContext(), TestAppBarActivity.class);
                startActivity(intent);
            }
        });
    }
}
