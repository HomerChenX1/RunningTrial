package com.example.runningtrial.UI;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.runningtrial.R;

import java.util.ArrayList;

import static com.example.runningtrial.base.Utils.getResId;

public class TestAppBarLayoutActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MainRecyclerViewGridAdapter myAdapter;
    private ArrayList<GridData> gridDataArray = new ArrayList<>();
    boolean isGrid = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_test_app_bar_layout);
        setContentView(R.layout.activity_test_collapsing_toolbar_layout);

        findViews();
    }

    private void findViews() {

        setDataSnapShot();

        recyclerView = findViewById(R.id.recyclerView);
        if (isGrid){
            GridLayoutManager mgr=new GridLayoutManager(this,2);
            recyclerView.setLayoutManager(mgr);
        } else {
            // xml need to add android:scrollbars="vertical"
            LinearLayoutManager mgr = new LinearLayoutManager(this);
            mgr.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(mgr);
        }
        myAdapter = new MainRecyclerViewGridAdapter(gridDataArray, isGrid);
        recyclerView.setAdapter(myAdapter);
    }

    private void setDataSnapShot() {
        String [] resStrings = { "cat0", "cat1", "cow", "dog0", "dog1", "dog2", "dog3", "dove", "fish", "teddy_bear"};
        for (String s : resStrings) {
            gridDataArray.add(new GridData(getResId("ic_"+s, R.drawable.class), s));
        }
    }
}
