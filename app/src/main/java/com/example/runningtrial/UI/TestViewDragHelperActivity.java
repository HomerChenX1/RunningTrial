package com.example.runningtrial.UI;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.example.runningtrial.R;

public class TestViewDragHelperActivity extends AppCompatActivity {
    ImageView ivCat, ivDog, ivCow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_view_drag_helper);

        ivCat = findViewById(R.id.ivCat);
        ivDog = findViewById(R.id.ivDog);
        ivCow = findViewById(R.id.ivCow);
    }


}

