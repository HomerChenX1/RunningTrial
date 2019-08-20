package com.example.runningtrial;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.runningtrial.base.DataWarehouse;
import com.example.runningtrial.base.FragmentBasic;
import com.example.runningtrial.fragments.ShowHtmlFragment;

import java.util.ArrayList;
import java.util.List;

public class TestFragmentActivity extends AppCompatActivity {
    private String TAG = getClass().getSimpleName();
    private TextView tvTestFragment;
    private boolean tryonce = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_fragment);
        findViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        DataWarehouse.getRef().logd(TAG, "onResume running");
    }

    @Override
    protected void onStart() {
        super.onStart();
        DataWarehouse.getRef().tvDebug = tvTestFragment;
        FragmentManager fm = getSupportFragmentManager();
        if (tryonce) {
            tryonce = false;

            Fragment fragOld = fm.findFragmentByTag("LoginFragment");
            Fragment fragNew = fm.findFragmentByTag("SchoolMainFragment");
//            Log.d(TAG, "replace: " + fragOld.getTag() + " to: " + fragNew.getTag() + " " + fragOld.isVisible());
            // fm.beginTransaction().hide(fragOld).show(fragNew).commit();

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        FragmentManager fm = getSupportFragmentManager();
        for (Fragment frag : fm.getFragments())
            if (((FragmentBasic)frag).checkVisible())
                Log.d(TAG, frag.getTag() + " is visible");
    }

    @Override
    protected void onStop() {
        super.onStop();
        DataWarehouse.getRef().tvDebug = null;
    }

    private void findViews() {
        tvTestFragment = findViewById(R.id.tvTestFragment);

        initFragments();
    }

    private void initFragments() {
        ArrayList<FragmentBasic> frags = new ArrayList<>();
        // start fragments and add them to FragmentManager
        // frags.add(LoginFragment.newInstance());
        // frags.add(ParentMainFragment.newInstance());
        // frags.add(SchoolMainFragment.newInstance());
        frags.add(ShowHtmlFragment.newInstance("privacy.html"));

        FragmentManager fm = getSupportFragmentManager();
        for (FragmentBasic frag: frags)
            frag.addFragment(fm, R.id.flTestFragment);
        fm.beginTransaction().show(frags.get(0)).commit();
    }

    private boolean recursiveFragments(Fragment fragment){
        boolean isProcessing = false;
        List<Fragment> fragList = null;

        if (fragment.isVisible()) {
            // call to processing
//            if (fragment.IOnBackPressed()) {
//                isProcessing = true;
//                return isProcessing;
//            }
            // recursive into child
            fragList = fragment.getChildFragmentManager().getFragments();
            for (Fragment child : fragList) {
                if (recursiveFragments(child)){
                    isProcessing = true;
                    break;
                }
            }
        }
        return isProcessing;
    }

    private boolean recursiveFragments(){
        boolean isProcessing = false;
        List<Fragment> fragList = null;

        fragList = getSupportFragmentManager().getFragments();
        for (Fragment child : fragList) {
            if (recursiveFragments(child)){
                isProcessing = true;
                break;
            }
        }
        return isProcessing;
    }

    @Override
    public void onBackPressed() {

        if (recursiveFragments())
            return;

        if (getSupportFragmentManager().getBackStackEntryCount()>0) {
            getSupportFragmentManager().popBackStack();
            return;
        }

        super.onBackPressed();
    }
}
