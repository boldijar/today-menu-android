package com.todaymenu.android.fragments;

import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.view.ViewGroup;

import butterknife.Unbinder;

/**
 * @author Paul
 * @since 2017.08.30
 */

public class BaseFullDialogFragment extends DialogFragment {
    protected Unbinder mUnbinder;

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            if (dialog.getWindow() == null) {
                return;
            }
            dialog.getWindow().setLayout(width, height);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
    }
}
