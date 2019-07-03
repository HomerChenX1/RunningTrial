package com.example.runningtrial.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class FragmentBasic extends Fragment {
    protected String TAG = getClass().getSimpleName();
    private Bundle savedBundle;  // google suggest to user sharedPreferences not bundle
    protected boolean isVisible = false;  // for BackStack, only press BackPressed if visible
    protected View rootView;

    public void setTAG(String id) { TAG = id; }
    public String getTAG() { return TAG; }

    public boolean checkVisible() { return isVisible; }

    // default constructor is necessary
    public FragmentBasic() { }

    // Notice: will not pass here when returned from BackStack
    // Do not override to prevent NullException
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBundle(savedInstanceState);
        initData(savedBundle);
        Log.d(TAG, "onCreate");
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        if (this.savedBundle == null)
            outState.putBundle("savedBundle", this.savedBundle);
        super.onSaveInstanceState(outState);
    }

    // Notice: pass here first when returned from BackStack, must be oveerided
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // rootView = inflater.inflate(R.layout.fragment_1, container, false);
        // return rootView;
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        isVisible = true;
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
        isVisible = false;
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach");
    }

    // add fragment with UI and hide it. Prevent overlay side effect of FrameLayout
    public void addFragment(FragmentManager fm, int resId){
        // available only the commit is success
        Fragment fragment = fm.findFragmentByTag(TAG);
        if (fragment == null) {
            Log.d(TAG, "fragment " + TAG + "is added");
            fm.beginTransaction().add(resId, this, TAG).hide(this).commit();
            // make sure commits are available immediately. slow but dependent.
            fm.executePendingTransactions();
        }
        else {
            Toast.makeText(getContext(), "normally add again", Toast.LENGTH_SHORT).show();
        }
    }

    private void initBundle(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            // normal process
            savedBundle = getArguments();
        } else {
            // activity be killed and restore
            savedBundle = savedInstanceState.getBundle("savedBundle");
        }

        //如果没有任何参数，则初始化 savedBundle，避免调用时 null pointer
        if (savedBundle == null) {
            savedBundle = new Bundle();
        }
    }

    // processing savedBundle == getArguments()
    protected void initData(Bundle savedBundle) { }
}