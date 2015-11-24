package com.marvik.apis.core.activities;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.marvik.apis.core.fragments.FragmentWrapper;
import com.marvik.apps.smsblocker.R;
import com.marvik.apps.smsblocker.fragments.blockedsms.BlockedSmsListFragment;
import com.marvik.apps.smsblocker.utils.Utils;

/**
 * Created by victor on 11/7/2015.
 */
public abstract class ActivityWrapper extends AppCompatActivity implements FragmentWrapper.OnCreateFragment {

    private Utils utilities;


    public Utils getUtils() {
        return utilities;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initAll();
        onCreateActivity(savedInstanceState);

        setContentView(R.layout.activity_main);
        initChildViews();
        attachFragment(new BlockedSmsListFragment(), true);
    }


    @Override
    protected void onPause() {
        super.onPause();
        onPauseActivity();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        onDestroyActivity();
    }


    protected abstract void onCreateActivity(Bundle savedInstanceState);

    protected abstract void onResumeActivity();

    protected abstract void onPauseActivity();

    protected abstract void onDestroyActivity();

    private void initAll() {
        utilities = new Utils(ActivityWrapper.this);
    }

    private FrameLayout mContainer;

    private void initChildViews() {
        mContainer = (FrameLayout) findViewById(R.id.activity_main_frameLayout_container);
    }

    @Override
    public void onBackPressed() {
        detachFragment();
    }

    private Fragment currentFragment;

    public Fragment getCurrentFragment() {
        return currentFragment;
    }

    public void setCurrentFragment(Fragment currentFragment) {
        this.currentFragment = currentFragment;
    }

    public void attachFragment(@NonNull Fragment fragment, boolean addToBackStack) {
        if (addToBackStack)
            getFragmentManager().beginTransaction().replace(mContainer.getId(), fragment).addToBackStack(getPackageName()).commit();
        else
            getFragmentManager().beginTransaction().replace(mContainer.getId(), fragment).commit();

        setCurrentFragment(fragment);
    }

    public void clearBackStack() {
        if (getFragmentManager().getBackStackEntryCount() > 1) {
            getFragmentManager().popBackStackImmediate();
        }

    }

    public void clearBackStackAll() {
        int backStacks = getFragmentManager().getBackStackEntryCount();
        for (int i = backStacks; i < 0; i--) {
            getFragmentManager().popBackStack();
        }
    }

    public void detachFragment() {
        if (getFragmentManager().getBackStackEntryCount() > 1)
            getFragmentManager().popBackStack();
        else finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        onResumeActivity();
    }


}
