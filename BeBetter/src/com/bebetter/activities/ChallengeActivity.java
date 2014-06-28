package com.bebetter.activities;

import com.bebetter.R;
import com.bebetter.fragments.ChallengeFragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;


public class ChallengeActivity extends FragmentActivity{
	
	static final int NUM_ITEMS = 40;
	
	ChallengeActivityAdapter mAdapter;
    ViewPager mPager;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge);
        
        //TODO get challenge id from intet ("ChallengeID", mChallenges.get(position).getChallengeID());

        mAdapter = new ChallengeActivityAdapter(getSupportFragmentManager());

        mPager = (ViewPager)findViewById(R.id.activity_challenge_view_pager);
        mPager.setAdapter(mAdapter);

    }
	
    public static class ChallengeActivityAdapter extends FragmentStatePagerAdapter {
        public ChallengeActivityAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        @Override
        public Fragment getItem(int position) {
            return ChallengeFragment.newInstance();
        }
    }

}
