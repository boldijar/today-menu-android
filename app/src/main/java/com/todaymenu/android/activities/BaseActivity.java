package com.todaymenu.android.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.todaymenu.android.fragments.BaseFragment;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * @author Paul
 * @since 2017.08.29
 */

public class BaseActivity extends AppCompatActivity {

    protected Toast showMessage(int message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
        return toast;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    protected BaseFragment setFragment(int id, BaseFragment baseFragment) {
        getSupportFragmentManager().beginTransaction().replace(id, baseFragment).commit();
        return baseFragment;
    }
}
