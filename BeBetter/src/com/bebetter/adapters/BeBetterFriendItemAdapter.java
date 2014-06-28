package com.bebetter.adapters;

import java.util.ArrayList;
import java.util.List;

import com.bebetter.R;
import com.bebetter.adapters.BeBetterContactItemAdapter.BeBetterContactItemAdapterListener;
import com.bebetter.domainmodel.Contact;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class BeBetterFriendItemAdapter extends ArrayAdapter<Contact>{

	private List<Contact> mContactOriginalData;
	private List<Contact> mContactFilterData;
	private ItemAdapterFilter mBeBetterFriendFilter;
	private Context mContext;
	private boolean mShowCheckBox = false; 
	
	public BeBetterFriendItemAdapter(Context context, int resource, List<Contact> ContactItems, boolean ShowCheckBox) {
		super(context, resource, ContactItems);
	
		mContactOriginalData = new ArrayList<Contact>();
		mContactOriginalData.addAll(ContactItems);
		mShowCheckBox = ShowCheckBox;
		mContext = context;
		
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		
		final Contact contact = getItem(position);
		
		ContactItemViewHolder viewHolder = null;
		if (convertView == null || convertView.getTag() == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_adapter_contact_be_better_friend, null);
			viewHolder = new ContactItemViewHolder();
			viewHolder.ContactName = (TextView) convertView.findViewById(R.id.item_adapter_contact_be_better_friend_text_view_friend_name);
			viewHolder.CheckBox = (CheckBox) convertView.findViewById(R.id.item_adapter_contact_be_better_friend_check_box);	
		}
		else{
			viewHolder = (ContactItemViewHolder) convertView.getTag();
		}

		viewHolder.ContactName.setText(contact.getDisplayName());
		
		if(mShowCheckBox){
			viewHolder.CheckBox.setChecked(contact.getIsSelected());
			viewHolder.CheckBox.setClickable(false);
			viewHolder.CheckBox.setFocusable(false);
			viewHolder.CheckBox.setVisibility(View.VISIBLE);	
		}
		else{
			viewHolder.CheckBox.setVisibility(View.GONE);
			viewHolder.CheckBox.setClickable(false);
		}
		
		return convertView;
	}
		
	@Override
	public Filter getFilter(){
		if(mBeBetterFriendFilter == null){
			mBeBetterFriendFilter = new ItemAdapterFilter(this, mContactOriginalData);
		}
		return mBeBetterFriendFilter;
	}
}
