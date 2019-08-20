package com.example.runningtrial.subsys;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;

import com.example.runningtrial.R;

public class TestNoti2Activity extends AppCompatActivity {
    TextView tvTitle, tvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_noti2);
        setTitle("TestNoti2Activity");
        findViews();
        showEmail();
    }

    private void findViews() {
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvContent = (TextView) findViewById(R.id.tvContent);
    }

    private void showEmail() {
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        }
        SimpleMsg email = (SimpleMsg) bundle.getSerializable("email");
        if (email != null) {
            tvTitle.setText(email.contentTitle);
            tvContent.setText(email.contentText);
        }
    }
}
