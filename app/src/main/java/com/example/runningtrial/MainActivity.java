package com.example.runningtrial;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.runningtrial.UI.TestRecyclerViewActivity;
import com.example.runningtrial.base.DataWarehouse;

public class MainActivity extends AppCompatActivity {
    private String TAG = getClass().getSimpleName();
    private TextView tvMain;
    private Button btnTestFragment, btnTestAppBar, btnTestRecyclerGrid;
    private LinearLayout llTestBtnText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        testImgBtnWithText();
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

        btnTestFragment = findViewById(R.id.btnTestFragment);
        btnTestFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "btnTestFragment", Toast.LENGTH_SHORT).show();
                DataWarehouse.getRef().logd(TAG, "btnTestFragment is clicked");
                Intent intent = new Intent(v.getContext(), TestFragmentActivity.class);
                startActivity(intent);
                // Utils.getCurrentDisplayMetrics(MainActivity.this);
                // Intent intent = new Intent(v.getContext(), TestAppBarActivity.class);
                // startActivity(intent);
            }
        });

        btnTestAppBar = findViewById(R.id.btnTestAppBar);
        btnTestAppBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "btnTestAppBar", Toast.LENGTH_SHORT).show();
                DataWarehouse.getRef().logd(TAG, "btnTestAppBar is clicked");
                Intent intent = new Intent(v.getContext(), TestAppBarActivity.class);
                startActivity(intent);
            }
        });

        btnTestRecyclerGrid = findViewById(R.id.btnTestRecyclerGrid);
        btnTestRecyclerGrid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "btnTestRecyclerGrid", Toast.LENGTH_SHORT).show();
                DataWarehouse.getRef().logd(TAG, "btnTestRecyclerGrid is clicked");
                Intent intent = new Intent(v.getContext(), TestRecyclerViewActivity.class);
                startActivity(intent);
            }
        });



    }

    private void testImgBtnWithText() {
        llTestBtnText = findViewById(R.id.llTestBtnText);
        llTestBtnText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Click llTestBtnText", Toast.LENGTH_SHORT).show();
            }
        });

        llTestBtnText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Toast.makeText(MainActivity.this, "Touch llTestBtnText", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }
}
