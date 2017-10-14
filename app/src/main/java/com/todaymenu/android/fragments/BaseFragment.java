package com.todaymenu.android.fragments;

import android.support.v4.app.Fragment;
import android.widget.Toast;

import butterknife.Unbinder;

/**
 * @author Paul
 * @since 2017.08.30
 */

public class BaseFragment extends Fragment {

    protected Unbinder mUnbinder;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mUnbinder != null) {
            mUnbinder.unbind();
            mUnbinder = null;
        }
    }

    protected void showMessage(int message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
