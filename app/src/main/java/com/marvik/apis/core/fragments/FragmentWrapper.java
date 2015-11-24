package com.marvik.apis.core.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.marvik.apps.smsblocker.R;
import com.marvik.apps.smsblocker.utils.Utils;


/**
 * Created by victor on 11/7/2015.
 */
public abstract class FragmentWrapper extends Fragment {

    private View wrapper;
    private RelativeLayout rlParentContainer;

    private OnCreateFragment onCreateFragment;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLibraries();
        onCreateFragment(savedInstanceState);
        receiveBundle();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        wrapper = getActivity().getLayoutInflater().inflate(R.layout.fragment_wrapper, container, false);
        initViews(wrapper);
        onCreateFragmentView(inflater, container, savedInstanceState);
        consumeBundle();
        return wrapper;
    }


    @Override
    public void onResume() {
        super.onResume();
        onResumeFragment();
        onCreateFragment.setActivityTitle(getActivityTitle());
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        onCreateFragment = (OnCreateFragment) getActivity();
        onAttachFragment();
    }

    @Override
    public void onPause() {
        super.onPause();
        onPauseFragment();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        onDestroyFragment();
    }

    private void initLibraries() {
        initAll();
    }

    private void initViews(@NonNull View view) {
        rlParentContainer = (RelativeLayout) view.findViewById(R.id.fragment_wrapper_relativeLayout_wrapper);
    }

    public RelativeLayout getContainer() {
        return rlParentContainer;
    }


    public abstract void onCreateFragment(@Nullable Bundle savedInstanceState);

    @Nullable
    public abstract String getActivityTitle();

    public abstract void receiveBundle();

    @Nullable
    public abstract void onCreateFragmentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);

    public abstract void consumeBundle();

    public abstract void onAttachFragment();

    public abstract void onResumeFragment();

    public abstract void onPauseFragment();

    public abstract void onDestroyFragment();

    public abstract int getParentLayout();


    private Utils utils;

    public Utils getUtils() {
        return utils;
    }


    private void initAll() {
        utils = new Utils(getActivity());
    }

    public interface OnCreateFragment {
        void setActivityTitle(String activityTitle);
    }
}
