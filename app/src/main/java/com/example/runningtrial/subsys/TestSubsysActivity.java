package com.example.runningtrial.subsys;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.runningtrial.R;
import com.example.runningtrial.base.DataWarehouse;

public class TestSubsysActivity extends AppCompatActivity {
    String TAG = getClass().getSimpleName();
    Button btnTestGps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_subsys);

        btnTestGps = findViewById(R.id.btnTestGps);
        btnTestGps.setOnClickListener(new View.OnClickListener() {
            String s = "btnTestGps";
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), s, Toast.LENGTH_SHORT).show();
                DataWarehouse.getRef().logd(TAG, s + " is clicked");
                new GpsBase().testEvents();
            }
        });

    }

}
