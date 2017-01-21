package com.bignerdanch.android.criminalintent.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.bignerdanch.android.criminalintent.activity.CrimeActivity;
import com.bignerdanch.android.criminalintent.activity.CrimePagerActivity;
import com.bignerdanch.android.criminalintent.model.Crime;
import com.bignerdanch.android.criminalintent.model.CrimeLab;
import com.bignerdanch.android.criminalintent.R;

import java.util.List;

/**
 * Created by wangyufei on 16/10/16.
 */

public class CrimeListFragment extends Fragment {

    private final int REQUSET_CRIME = 1;

    private RecyclerView mCrimeRecyclerView;
    private CrimeAdapter mAdapter;

    private boolean subTitleAvailable;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true); //告诉fragment响应menu
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);

        mCrimeRecyclerView = (RecyclerView) view.findViewById(R.id.crime_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    @Override
    public void onResume() {

        super.onResume();

        updateUI();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUSET_CRIME) {

        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.fragment_crime_list, menu);

        MenuItem menuItem = menu.findItem(R.id.menu_item_show_subtitle);

        if (subTitleAvailable) {

            menuItem.setTitle(getResources().getString(R.string.hide_subtitle));
        } else {

            menuItem.setTitle(getResources().getString(R.string.show_subtitle));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.menu_item_new_crime:

                Crime crime = new Crime();
                CrimeLab.get(getActivity()).addCrime(crime);

                Intent intent = CrimePagerActivity.newIntent(getActivity(), crime.getId());
                startActivity(intent);
                return true;
            case R.id.menu_item_show_subtitle:

                subTitleAvailable = !subTitleAvailable;
                getActivity().invalidateOptionsMenu();

                updateSubTitle();
                return true;
            default:
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateSubTitle() {

        CrimeLab crimeLab = CrimeLab.get(getActivity());
        int crimeCount = crimeLab.getCrimes().size();
        String subTitle = getResources().getString(R.string.subtitle_format, Integer.toString(crimeCount));

        if (!subTitleAvailable) {

            subTitle = null;
        }

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(subTitle);
    }

    private void updateUI() {

        if (mAdapter == null) {

            CrimeLab crimeLab = CrimeLab.get(getActivity());
            List<Crime> crimes = crimeLab.getCrimes();

            mAdapter = new CrimeAdapter(crimes);
            mCrimeRecyclerView.setAdapter(mAdapter);
        } else {

            mAdapter.notifyDataSetChanged();
        }

        updateSubTitle();
    }

    private class CrimeHolder extends RecyclerView.ViewHolder {

        private Crime crime;

        private TextView mTitleTextView;
        private TextView mDateTextView;
        private CheckBox mSolvedCheckBox;

        public CrimeHolder(View itemView) {

            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = CrimePagerActivity.newIntent(getActivity(), crime.getId());
                    startActivityForResult(intent, REQUSET_CRIME);
                }
            });

            mTitleTextView = (TextView) itemView.findViewById(R.id.list_item_crime_title_text_view);
            mDateTextView = (TextView) itemView.findViewById(R.id.list_item_crime_date_text_view);
            mSolvedCheckBox = (CheckBox) itemView.findViewById(R.id.list_item_crime_solved_check_box);
        }

        public void bindCrime(Crime crime) {

            this.crime = crime;

            mTitleTextView.setText(this.crime.getTitle());
            mDateTextView.setText(this.crime.getDate().toString());
            mSolvedCheckBox.setChecked(this.crime.isSolved());
        }
    }

    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder> {

        private List<Crime> mCrimes;

        public CrimeAdapter(List<Crime> mCrimes) {

            this.mCrimes = mCrimes;
        }

        @Override
        public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_crime, parent, false);

            return new CrimeHolder(view);
        }

        @Override
        public void onBindViewHolder(CrimeHolder holder, int position) {

            Crime crime = mCrimes.get(position);
            holder.bindCrime(crime);
        }

        @Override
        public int getItemCount() {

            return mCrimes.size();
        }
    }
}
