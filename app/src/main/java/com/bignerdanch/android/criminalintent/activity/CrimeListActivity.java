package com.bignerdanch.android.criminalintent.activity;

import android.support.v4.app.Fragment;

import com.bignerdanch.android.criminalintent.fragment.CrimeListFragment;

/**
 * Created by wangyufei on 16/10/16.
 */

public class CrimeListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {

        return new CrimeListFragment();
    }
}
