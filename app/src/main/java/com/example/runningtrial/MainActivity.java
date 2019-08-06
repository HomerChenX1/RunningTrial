package com.example.runningtrial;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.runningtrial.UI.TestAppBarLayoutActivity;
import com.example.runningtrial.UI.TestCoordinateLayoutActivity;
import com.example.runningtrial.UI.TestRecyclerViewActivity;
import com.example.runningtrial.UI.TestViewDragHelperActivity;
import com.example.runningtrial.base.DataWarehouse;
import com.example.runningtrial.subsys.TestNotificationActivity;
import com.example.runningtrial.subsys.TestServicesActivity;
import com.example.runningtrial.subsys.TestTtsActivity;

/**
 * NTP : OK
 * GoogleMap
 * TTS : OK
 *    Timer
 *    appbar OK
 *    drawer OK
 *    DialogFragment
 *    scroller
 *    view custom
 *    appbarlayout OK
 *    bottomsheet OK
 *    view pager
 *    vertical viewpager : https://stackoverflow.com/questions/13477820/android-vertical-viewpager
 */
public class MainActivity extends AppCompatActivity {
    private String TAG = getClass().getSimpleName();
    private TextView tvMain;
    private Button btnTestFragment, btnTestAppBar, btnTestRecyclerGrid, btnRipple;
    private Button btnTestDrawer, btnTestDrawerActivity, btnTestCoordinate;
    private Button btnTestDragHelper, btnTestAppBarLayout;

    private Switch switchTxt, switchOrg;
    private ToggleButton tbtnToggle;
    private CheckBox cbox1;
    private RadioButton rbtn1, rbtn2, rbtn3;
    private NumberPicker np;

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
        tbtnToggle = findViewById(R.id.tbtnToggle);
        tbtnToggle.setOnCheckedChangeListener(swListenser);
        cbox1 = findViewById(R.id.cbox1);
        cbox1.setOnCheckedChangeListener(swListenser);

        rbtn1 = findViewById(R.id.rbtn1);
        rbtn2 = findViewById(R.id.rbtn2);
        rbtn3 = findViewById(R.id.rbtn3);
        rbtn1.setOnCheckedChangeListener(swListenser);
        rbtn2.setOnCheckedChangeListener(swListenser);
        rbtn3.setOnCheckedChangeListener(swListenser);

        np = findViewById(R.id.np_picker);
        np.setMinValue(1);
        np.setMaxValue(60);
        np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int numPrev, int numNow) {
                tvMain.setText("NumberPicker numNow:" + numNow);
            }
        });

        btnTestDrawer = findViewById(R.id.btnTestDrawer);
        btnTestDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "btnTestDrawer", Toast.LENGTH_SHORT).show();
                DataWarehouse.getRef().logd(TAG, "btnTestDrawer is clicked");
                Intent intent = new Intent(v.getContext(), TestDrawerActivity.class);
                startActivity(intent);
            }
        });

        btnTestDrawerActivity = findViewById(R.id.btnTestDrawerActivity);
        btnTestDrawerActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "btnTestDrawerActivity", Toast.LENGTH_SHORT).show();
                DataWarehouse.getRef().logd(TAG, "btnTestDrawer is clicked");
                Intent intent = new Intent(v.getContext(), TestDrawerLayoutActivity.class);
                startActivity(intent);
            }
        });

        btnTestCoordinate = findViewById(R.id.btnTestCoordinate);
        btnTestCoordinate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "btnTestCoordinate", Toast.LENGTH_SHORT).show();
                DataWarehouse.getRef().logd(TAG, "btnTestCoordinate is clicked");
                Intent intent = new Intent(v.getContext(), TestCoordinateLayoutActivity.class);
                startActivity(intent);
            }
        });

        btnTestDragHelper = findViewById(R.id.btnTestDragHelper);
        btnTestDragHelper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "btnTestDragHelper", Toast.LENGTH_SHORT).show();
                DataWarehouse.getRef().logd(TAG, "btnTestDragHelper is clicked");
                Intent intent = new Intent(v.getContext(), TestViewDragHelperActivity.class);
                startActivity(intent);
            }
        });

//        btnTestAppBarLayout = findViewById(R.id.btnTestAppBarLayout);
//        btnTestAppBarLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(v.getContext(), "btnTestAppBarLayout", Toast.LENGTH_SHORT).show();
//                DataWarehouse.getRef().logd(TAG, "btnTestAppBarLayout is clicked");
//                Intent intent = new Intent(v.getContext(), TestAppBarLayoutActivity.class);
//                startActivity(intent);
//            }
//        });
        setupButton(R.id.btnTestAppBarLayout, "btnTestAppBarLayout", TestAppBarLayoutActivity.class);
        setupButton(R.id.btnTestNotication, "btnTestNotication", TestNotificationActivity.class);
        setupButton(R.id.btnTestServiceBind, "btnTestServiceBind", TestServicesActivity.class);
        setupButton(R.id.btnTestTtsAll, "btnTestTtsAll", TestTtsActivity.class);
    }

    CompoundButton.OnCheckedChangeListener swListenser = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            switch (buttonView.getId()) {
                case R.id.switchTxt:
                    showChecked("switchTxt", isChecked);
                    break;
                case R.id.switchOrg:
                    showChecked("switchOrg", isChecked);
                    break;
                case R.id.tbtnToggle:
                    showChecked("tbtnToggle", isChecked);
                    break;
                case R.id.cbox1:
                    showChecked("cbox1", isChecked);
                    break;

                case R.id.rbtn1:
                    showChecked("RadioButton1", isChecked);
                    break;
                case R.id.rbtn2:
                    showChecked("RadioButton2", isChecked);
                    break;
                case R.id.rbtn3:
                    showChecked("RadioButton3", isChecked);
                    break;
            }
        }
    };

    private void showChecked(String btnName, boolean isChecked){
        if (isChecked) tvMain.setText(btnName + " is checked");
        else tvMain.setText(btnName + " is unchecked");
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
