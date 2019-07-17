package com.example.runningtrial;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.runningtrial.UI.TestRecyclerViewActivity;
import com.example.runningtrial.base.DataWarehouse;

/**
 * NTP
 * GoogleMap
 * TTS
 *  need test
 *    Timer
 *    appbar
 *    drawer
 *    DialogFragment
 */
public class MainActivity extends AppCompatActivity {
    private String TAG = getClass().getSimpleName();
    private TextView tvMain;
    private Button btnTestFragment, btnTestAppBar, btnTestRecyclerGrid, btnRipple;
    private LinearLayout llTestBtnText;
    private Switch switchTxt, switchOrg;

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

        btnRipple = findViewById(R.id.btnRipple);
        btnRipple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "btnRipple is clicked", Toast.LENGTH_SHORT).show();
            }
        });

        switchTxt = findViewById(R.id.switchTxt);
        switchTxt.setOnCheckedChangeListener(swListenser);
        switchOrg = findViewById(R.id.switchOrg);
        switchOrg.setOnCheckedChangeListener(swListenser);
    }

    private void testImgBtnWithText() {
        llTestBtnText = findViewById(R.id.llTestBtnText);
        llTestBtnText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Click llTestBtnText", Toast.LENGTH_SHORT).show();
            }
        });

//        llTestBtnText.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                Toast.makeText(MainActivity.this, "Touch llTestBtnText", Toast.LENGTH_SHORT).show();
//                return false;
//            }
//        });
    }

    CompoundButton.OnCheckedChangeListener swListenser = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            switch (buttonView.getId()) {
                case R.id.switchTxt:
                    if (isChecked)
                        // Toast.makeText(MainActivity.this, "switchTxt is checked", Toast.LENGTH_SHORT).show();
                        tvMain.setText("switchTxt is checked");
                    else tvMain.setText("switchTxt is unchecked");
                    break;
                case R.id.switchOrg:
                    if (isChecked)
                        // Toast.makeText(MainActivity.this, "switchTxt is checked", Toast.LENGTH_SHORT).show();
                        tvMain.setText("switchOrg is checked");
                    else tvMain.setText("switchOrg is unchecked");
                    break;
            }
        }
    };
}
