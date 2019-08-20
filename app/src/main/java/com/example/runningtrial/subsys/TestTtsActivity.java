package com.example.runningtrial.subsys;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.runningtrial.R;

public class TestTtsActivity extends AppCompatActivity {
    Button btnTestTts, btnTestSpeach, btnTestSpeachAdd;
    Tts tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_tts);

        btnTestTts = findViewById(R.id.btnTestTts);
        btnTestTts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "btnTestTts is clicked!", Toast.LENGTH_SHORT).show();

                tts = new Tts(v.getContext());
            }
        });

        btnTestSpeach = findViewById(R.id.btnTestSpeach);
        btnTestSpeach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "btnTestSpeach is clicked!", Toast.LENGTH_SHORT).show();
                tts.flushSpeak("一定要宣告成");
            }
        });

        btnTestSpeachAdd = findViewById(R.id.btnTestSpeachAdd);
        btnTestSpeachAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "btnTestSpeach is clicked!", Toast.LENGTH_SHORT).show();
                tts.queueSpeak("多執行緒的機制");
                tts.queueSpeak("存在 main UI thread");
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (tts!=null) tts.close();
        super.onDestroy();
    }
}
