package com.example.runningtrial.base;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

public class FragmentBasic extends Fragment implements Utils.FragmentBackHandler {
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

    /**
     * @return  true: 處理後返回, false :未處理. 由Activity 處理
     */
    @Override
    public boolean onBackPressed() {
        return false;
    }

    public static boolean isFragmentBackHandled(Fragment fragment) {
        return fragment != null
                && fragment.isVisible()
                && fragment.getUserVisibleHint() //for ViewPager
                && fragment instanceof Utils.FragmentBackHandler
                && ((Utils.FragmentBackHandler) fragment).onBackPressed();
    }

    // MainActivity.onBackPressed : handleBackPress(this)
    // if(!handleBackPress(this)) super.onBackPressed.
    public static boolean handleBackPress(AppCompatActivity activity) {
        return handleBackPress(activity.getSupportFragmentManager());
    }

    public static boolean handleBackPress(Fragment fragment) {
        return handleBackPress(fragment.getChildFragmentManager());
    }

    public static boolean handleBackPress(FragmentManager fragmentManager) {
        List<Fragment> fragments = fragmentManager.getFragments();

        if (fragments == null) return false;

        // for (int i = fragments.size() - 1; i >= 0; i--) {
            // Fragment child = fragments.get(i);
        for (Fragment child : fragments) {
            if (isFragmentBackHandled(child)) {
                return true;
            } else {
                // child not proceed, recursive to next floor
                if(handleBackPress(child))
                    return true;
            }
        }
        // none proceed backPressed
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
            return true;
        }
        return false;
    }
}