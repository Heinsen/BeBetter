package com.bebetter.activities;

import com.bebetter.R;
import com.bebetter.adapters.BeBetterContactItemAdapter.BeBetterContactItemAdapterListener;
import com.bebetter.domainmodel.BeBetterNotification;
import com.bebetter.domainmodel.Challenge;
import com.bebetter.domainmodel.Contact;
import com.bebetter.fragments.ChallengeFeedFragment;
import com.bebetter.fragments.ContactsFragment;
import com.bebetter.fragments.ContactsFragment.ContactsFragmentListener;
import com.bebetter.fragments.MainFragment;
import com.bebetter.fragments.MainFragment.OnCurrentChallengesListener;
import com.bebetter.fragments.NotificationCenterFragment;
import com.bebetter.fragments.NotificationCenterFragment.OnNotificationFragmentSelectedListener;

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

public class ActionBarTabsPagerActivity extends FragmentActivity implements OnCurrentChallengesListener, OnNotificationFragmentSelectedListener, ContactsFragmentListener, BeBetterContactItemAdapterListener{

	static final int NUM_ITEMS = 4;
	static final int ALL_CONTACTS = 0;
	
	MyAdapter mAdapter;
	
	ViewPager mPager;
	
	static ContactsFragment mContactsFragment;
	static NotificationCenterFragment mNotificationCenterFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_action_bar_tabs_pager);


		mAdapter = new MyAdapter(getSupportFragmentManager());
		
		mPager = (ViewPager)findViewById(R.id.pager);
		mPager.setAdapter(mAdapter);
		
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
				else if(tag == "ContactsTab")
					mPager.setCurrentItem(2);
				else if(tag == "NotificationsTab")
					mPager.setCurrentItem(3);
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
    			if(positionOffset == 0 && position == 3)
    				mNotificationCenterFragment.UpdateTimeStamps();
    		}
    		
    		@Override
    		public void onPageScrollStateChanged(int position) {
    			// TODO Auto-generated method stub
    			
    		}
    	});
	}

	@Override
	protected void onResume() {
		super.onResume();
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
			if(position == 3){
				mNotificationCenterFragment = NotificationCenterFragment.newInstance();
				return mNotificationCenterFragment;
			}
			if(position == 2){
				mContactsFragment = ContactsFragment.newInstance(ALL_CONTACTS);
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
					Intent CameraActivityIntent = new Intent(this, CameraActivity.class);
					CameraActivityIntent.putExtra("ChallengeID", selectedCurrentChallengeListItem.getChallengeID());
					startActivity(CameraActivityIntent);
				}
				//else{} //TODO Make text warning that no camera is detected		
	}

	@Override
	public void onNotificationSelected(BeBetterNotification BeBetterNotifikation) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onSendChallengeBtnClick() {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void onContactClick(Contact selectedContact) {
		Intent smsIntent = new Intent(Intent.ACTION_VIEW);
		smsIntent.setType("vnd.android-dir/mms-sms");
		smsIntent.putExtra("address", selectedContact.getPhoneNumber());
		smsIntent.putExtra("sms_body",R.string.start_SMS_body_text + selectedContact.getDisplayName() + R.string.end_SMS_body_text);
		startActivity(smsIntent);
	}

	@Override
	public void onBeBetterFriendSelectedListener(Contact BeBetterFriendContact) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onBeBetterFriendClick(Contact BeBetterFriend) {
		//Switching to ChallengeFeedFragment
		mPager.setCurrentItem(1);
	}

}
