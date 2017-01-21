package com.bignerdanch.android.criminalintent.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.bignerdanch.android.criminalintent.R;
import com.bignerdanch.android.criminalintent.fragment.CrimeFragment;
import com.bignerdanch.android.criminalintent.model.Crime;
import com.bignerdanch.android.criminalintent.model.CrimeLab;

import java.util.List;
import java.util.UUID;

/**
 * Created by yufei0213 on 16/10/30.
 */

public class CrimePagerActivity extends AppCompatActivity {

    private static final String EXTRA_CRIME_ID = "com.bignerdanch.android.criminalintent.activity.CrimePagerActivity.crime_id";

    private ViewPager viewPager;
    private List<Crime> crimes;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_pager);

        UUID crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);

        viewPager = (ViewPager) this.findViewById(R.id.activity_crime_pager_view_pager);

        crimes = CrimeLab.get(this).getCrimes();
        FragmentManager fragmentManager = getSupportFragmentManager();
        viewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {

                Crime crime = crimes.get(position);
                return CrimeFragment.newInstance(crime.getId());
            }

            @Override
            public int getCount() {

                return crimes.size();
            }
        });
    }

    public static Intent newIntent(Context context, UUID id) {

        Intent intent = new Intent(context, CrimePagerActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, id);

        return intent;
    }
}
