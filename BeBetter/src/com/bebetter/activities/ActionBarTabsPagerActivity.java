package com.bebetter.activities;

import com.bebetter.R;
import com.bebetter.domainmodel.Challenge;
import com.bebetter.fragments.ChallengeFeedFragment;
import com.bebetter.fragments.ContactsFragment;
import com.bebetter.fragments.MainFragment;
import com.bebetter.fragments.MainFragment.OnCurrentChallengesListener;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.SearchManager;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;

public class ActionBarTabsPagerActivity extends FragmentActivity implements OnCurrentChallengesListener{

	static final int NUM_ITEMS = 3;

	MyAdapter mAdapter;
	
	ViewPager mPager;
	
	static ContactsFragment mContactsFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_action_bar_tabs_pager);


		mAdapter = new MyAdapter(getSupportFragmentManager());
		
		mPager = (ViewPager)findViewById(R.id.pager);
		mPager.setAdapter(mAdapter);
		
	}

	@Override
	protected void onResume() {
		super.onResume();
		
		final ActionBar mActionBar = getActionBar();
		mActionBar.show();
		mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		
		// Create a tab listener that is called when the user changes tabs.
	    ActionBar.TabListener tabListener = new ActionBar.TabListener() {
			@Override
			public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
			}

			@Override
			public void onTabSelected(Tab tab, FragmentTransaction ft) {
				Object tag = tab.getTag();
				
				if(tag == "FeedTab")
					mPager.setCurrentItem(0);
				else if(tag == "ChallengeTab")
					mPager.setCurrentItem(1);
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
    	
        //Setting up OnPageChangeListener
        mPager.setOnPageChangeListener(new OnPageChangeListener() {
    		
    		@Override
    		public void onPageSelected(int arg0) {
    			// TODO Auto-generated method stub
    			
    		}
    		
    		@Override
    		public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    			//If the offset to the page in position is 0
    			if(positionOffset == 0)
    				mActionBar.setSelectedNavigationItem(position);
    		}
    		
    		@Override
    		public void onPageScrollStateChanged(int position) {
    			// TODO Auto-generated method stub
    			
    		}
    	});
	
	};
	
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
			if(position == 2){
				mContactsFragment = ContactsFragment.newInstance();
				return mContactsFragment;
			}
			if(position == 1){
				return MainFragment.newInstance();
			}
			if(position == 0)
				return ChallengeFeedFragment.newInstance();

			else
				return null;
		}
			
	}
	
	/** Check if this device has a camera */
	private boolean checkCameraHardware(Context context) {
	    if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
	        // this device has a camera
	        return true;
	    } else {
	        // no camera on this device
	        return false;
	    }
	}

	@Override
	public void OnCurrentChallengeSelected(
			Challenge selectedCurrentChallengeListItem) {
				if(checkCameraHardware(this)){
					//Start Camera Activity
				}
				//else{} //TODO Make text warning that no camera is detected
				
					
	}


}
