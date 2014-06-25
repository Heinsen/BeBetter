package com.bebetter.fragments;

import java.util.Date;

import com.bebetter.R;
import com.bebetter.adapters.NotificationCenterItemAdapter;
import com.bebetter.domainmodel.BeBetterNotification;

import android.app.Activity;
import android.app.LauncherActivity.ListItem;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;


public class NotificationCenterFragment extends Fragment{

	private static final int TYPE_INCOMING_CHALLENGE = 0;
	private static final int TYPE_FRIEND_REQUEST = 1;
	private static final int TYPE_FRIEND_COMPLETED_CHALLENGE = 2;
	private static final int TYPE_NOTIFICATION_ACCEPTED = 3;
	private static final int TYPE_FRIEND_ADDED_PHONE_NUMBER = 4;
	
	private View mNotificationCenterFragment;
	private ListView mListView;
	private NotificationCenterItemAdapter mListViewAdapter;
	private OnNotificationFragmentSelectedListener mCallback;
    
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		
		mNotificationCenterFragment = inflater.inflate(R.layout.fragment_notification_center, container, false);
		mListView = (ListView) mNotificationCenterFragment.findViewById(R.id.fragment_notifcation_center_list_view);
		mListViewAdapter = new NotificationCenterItemAdapter(this);
		mListView.setAdapter(mListViewAdapter);
		
		//Dummy notification instantiation:
		BeBetterNotification incChallengeNotification = new BeBetterNotification(getActivity(), TYPE_INCOMING_CHALLENGE);
		incChallengeNotification.setHeadLineText("Samuel is challenging you!");
		incChallengeNotification.setBtnText("Accept Challenge");
		incChallengeNotification.setTimeStamp(new Date());
		
		BeBetterNotification friendsRequest = new BeBetterNotification(getActivity(), TYPE_FRIEND_REQUEST);
		friendsRequest.setHeadLineText("Samuel send a friend request");
		friendsRequest.setBtnText("Accept request");
		friendsRequest.setTimeStamp(new Date());
		
		//Adding dummy notifications
		mListViewAdapter.addIncomingChallengeItem(incChallengeNotification);
		mListViewAdapter.AddChallenge(friendsRequest);
		
//		mListView.setOnItemClickListener(new OnItemClickListener() {
//    		@Override
//			public void onItemClick(AdapterView<?> parent, View view,
//					int position, long id) {
//    			
//    			BeBetterNotification selectedNotification = (BeBetterNotification) parent.getItemAtPosition(position);
//    			mListViewAdapter.RemoveChallenge(position);
//    			
//    			
//    			mCallback.onNotificationSelected(selectedNotification);
//			}
//		});
		
		return mNotificationCenterFragment;
	}
	
	public void BeBetterNotificationSelected(BeBetterNotification selectedNotification){
		mListViewAdapter.RemoveChallenge(selectedNotification);
		mListViewAdapter.AddChallenge(InstanciateNewNotification(selectedNotification));
		mListViewAdapter.notifyDataSetChanged();		
	}
	
	private BeBetterNotification InstanciateNewNotification(BeBetterNotification selectedNotification){
		
		int notificationType = selectedNotification.getBeBetterNotificationType();
		
		if(notificationType == TYPE_FRIEND_REQUEST) {
			BeBetterNotification friendRequestAccepted = new BeBetterNotification(getActivity(), TYPE_NOTIFICATION_ACCEPTED);
			friendRequestAccepted.setHeadLineText("Friend request from Samuel accepted");
			friendRequestAccepted.setBtnText("");
			friendRequestAccepted.setTimeStamp(new Date());
			return friendRequestAccepted;
		}
		else if(notificationType == TYPE_INCOMING_CHALLENGE){
			BeBetterNotification challengeAccepted = new BeBetterNotification(getActivity(), TYPE_NOTIFICATION_ACCEPTED);
			challengeAccepted.setHeadLineText("Challenge accepted from Samuel accepted");
			challengeAccepted.setBtnText("");
			challengeAccepted.setTimeStamp(new Date());
			return challengeAccepted;
		}
		else
			return null;
	}
		
	public void UpdateTimeStamps(){
		mListViewAdapter.notifyDataSetChanged();
	}
	
	public static NotificationCenterFragment newInstance() {
    	NotificationCenterFragment fragment = new NotificationCenterFragment();
        return fragment;
    }
    
	public interface OnNotificationFragmentSelectedListener {
        public void onNotificationSelected(BeBetterNotification BeBetterNotifikation);
    }
	
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnNotificationFragmentSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }
}
