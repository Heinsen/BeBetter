package com.bebetter.activities;

import com.bebetter.R;
import com.bebetter.fragments.ChallengeFeedFragment;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

public class ActionBarTabsPagerActivity extends FragmentActivity{

	static final int NUM_ITEMS = 2;

	MyAdapter mAdapter;
	
	ViewPager mPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_action_bar_tabs_pager);

		final ActionBar mActionBar = getActionBar();
		mActionBar.show();
		mAdapter = new MyAdapter(getSupportFragmentManager());

		mPager = (ViewPager)findViewById(R.id.pager);
		mPager.setAdapter(mAdapter);
		
		mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		// Create a tab listener that is called when the user changes tabs.
	    ActionBar.TabListener tabListener = new ActionBar.TabListener() {
			@Override
			public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
			}

			@Override
			public void onTabSelected(Tab tab, FragmentTransaction ft) {
				Object tag = tab.getTag();
			    for (int i=0; i<NUM_ITEMS; i++) {
			        if (mActionBar.getTabAt(i).getTag() == tag) {
			        	mPager.setCurrentItem(i);
			        }
			    }
			}

			@Override
			public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
				// TODO Auto-generated method stub
			}
	    };
	 
        mActionBar.addTab(mActionBar.newTab()
	              	.setText("Feed")
	                .setTabListener(tabListener)
	                .setTag("FeedTab"));
        
        mActionBar.addTab(mActionBar.newTab()
        			.setText("Challenge")
        			.setTabListener(tabListener)
        			.setTag("ChallengeTab"));
     
        mActionBar.addTab(mActionBar.newTab()
                	.setText("Contacts")
                	.setTabListener(tabListener)
                	.setTag("ContactsTab"));
        
        mActionBar.addTab(mActionBar.newTab()
        			.setText("Notifications")
        			.setTabListener(tabListener)
        			.setTag("NotificationsTab"));
	    
	}

	public static class MyAdapter extends FragmentPagerAdapter {
		public MyAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public int getCount() {
			return NUM_ITEMS;
		}

		@Override
		public Fragment getItem(int position) {
			return ChallengeFeedFragment.newInstance();
		}
	}
}
