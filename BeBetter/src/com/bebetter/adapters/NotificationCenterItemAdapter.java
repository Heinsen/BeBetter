package com.bebetter.adapters;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.opengl.Visibility;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.bebetter.R;
import com.bebetter.domainmodel.BeBetterNotification;
import com.bebetter.domainmodel.Contact;
import com.bebetter.fragments.NotificationCenterFragment;

public class NotificationCenterItemAdapter extends BaseAdapter {

	private static final int TYPE_INCOMING_CHALLENGE = 0;
	private static final int TYPE_FRIEND_REQUEST = 1;
	private static final int TYPE_FRIEND_COMPLETED_CHALLENGE = 2;
	private static final int TYPE_NOTIFICATION_ACCEPTED = 3;
	private static final int TYPE_FRIEND_ADDED_PHONE_NUMBER = 4;
	
	private static final int TYPE_MAX_COUNT = 4;
	
	private List<BeBetterNotification> mBeBetterNotificationItems = new ArrayList<BeBetterNotification>();
	private NotificationCenterFragment mContext;
	private LayoutInflater mLayoutInflater;
		
	public NotificationCenterItemAdapter(NotificationCenterFragment Context){
		mLayoutInflater = (LayoutInflater) Context.getActivity().getSystemService(Context.getActivity().LAYOUT_INFLATER_SERVICE);
		mContext = Context;
	}
	
	public void addIncomingChallengeItem(BeBetterNotification BeBetterNotification){
		mBeBetterNotificationItems.add(0, BeBetterNotification);
		notifyDataSetChanged();
	}
	
	public void RemoveChallenge(BeBetterNotification BeBetterNotfication){
		mBeBetterNotificationItems.remove(BeBetterNotfication);
		notifyDataSetChanged();
	}
	
	public void AddChallenge(BeBetterNotification IncomingChallengeItem){
		mBeBetterNotificationItems.add(0, IncomingChallengeItem);
		notifyDataSetChanged();
	}
	
	@Override
	public int getViewTypeCount() {
		return TYPE_MAX_COUNT;
	};
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent){
		
		NotificationCenterItemViewHolder viewHolder = new NotificationCenterItemViewHolder();
		int tBeBetterNotificationType = mBeBetterNotificationItems.get(position).getBeBetterNotificationType();
		
		if (convertView == null || convertView.getTag() == null) {	
			
			switch (tBeBetterNotificationType) {
			case TYPE_INCOMING_CHALLENGE:
				convertView = mLayoutInflater.inflate(R.layout.item_adapter_notification_center, null);
				viewHolder.HeadLineTextView = (TextView) convertView.findViewById(R.id.item_adapter_notification_center_text_view_head_line);
				viewHolder.Button = (Button) convertView.findViewById(R.id.item_adapter_notification_center_btn);
				viewHolder.TimeStapmTextView = (TextView) convertView.findViewById(R.id.item_adapter_notification_center_text_view_time_stamp);
				break;
	
			case TYPE_FRIEND_REQUEST:
				convertView = mLayoutInflater.inflate(R.layout.item_adapter_notification_center, null);
				viewHolder.HeadLineTextView = (TextView) convertView.findViewById(R.id.item_adapter_notification_center_text_view_head_line);
				viewHolder.Button = (Button) convertView.findViewById(R.id.item_adapter_notification_center_btn);
				viewHolder.TimeStapmTextView = (TextView) convertView.findViewById(R.id.item_adapter_notification_center_text_view_time_stamp);
				break;
				
			case TYPE_NOTIFICATION_ACCEPTED:
				convertView = mLayoutInflater.inflate(R.layout.item_adapter_notification_center_notification_accepted, null);
				viewHolder.HeadLineTextView = (TextView) convertView.findViewById(R.id.item_adapter_notification_center_notification_accepted_text_view_head_line);
				viewHolder.TimeStapmTextView = (TextView) convertView.findViewById(R.id.item_adapter_notification_center_notification_accepted_text_view_time_stamp);
			}
			convertView.setTag(viewHolder);
		}
		else{
			viewHolder = (NotificationCenterItemViewHolder) convertView.getTag();
		}
		
		final BeBetterNotification notification = mBeBetterNotificationItems.get(position);
		
		viewHolder.HeadLineTextView.setText(notification.getHeadLineText());
		viewHolder.TimeStapmTextView.setText(notification.getTimeStampText());
		
		if(tBeBetterNotificationType == TYPE_NOTIFICATION_ACCEPTED){
			if(viewHolder.Button != null)
				viewHolder.Button.setVisibility(View.GONE);
		}
		else if(tBeBetterNotificationType == TYPE_INCOMING_CHALLENGE || tBeBetterNotificationType == TYPE_FRIEND_REQUEST)
		{
			if(viewHolder.Button != null){
				viewHolder.Button.setVisibility(View.VISIBLE);
				viewHolder.Button.setText(notification.getBtnText());

				viewHolder.Button.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						mContext.BeBetterNotificationSelected(notification);
					}
				});
			}
		}
		return convertView;
	}
	
	
	@Override
	public int getCount() {
		return mBeBetterNotificationItems.size();
	}

	@Override
	public BeBetterNotification getItem(int position) {
		return mBeBetterNotificationItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
}
