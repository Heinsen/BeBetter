package com.bebetter.adapters;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bebetter.R;
import com.bebetter.domainmodel.Challenge;
import com.bebetter.domainmodel.Contact;
import com.bebetter.fragments.AlertDialogFragment.AlertDialogFragmentListener;

public class ContactItemAdapter extends ArrayAdapter<Contact>{

	static final int TYPE_CONTACT = 0;
	static final int TYPE_CONTACT_WITH_BEBETTER = 1;
	static final int TYPE_BEBETTER_FRIEND = 2;
	
	private Filter mContactFilter;
	
	private List<Contact> mContactOriginalData;
	private List<Contact> mContactFilterData;
	private ContactItemAdapterListener mListener;
	private Context mContext;
	
	public ContactItemAdapter(Context context, int resource, List<Contact> ContactItems) {
		super(context, resource, ContactItems);
	
		mContactOriginalData = new ArrayList<Contact>();
		mContactOriginalData.addAll(ContactItems);
		
		mContext = context;
		
		try {
			// Instantiate the NoticeDialogListener so we can send events to the host
			mListener = (ContactItemAdapterListener) context;
		} catch (ClassCastException e) {
			// The activity doesn't implement the interface, throw exception
			throw new ClassCastException(mContext.toString() + " must implement ContactItemAdapterListener");
		}
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		
		final Contact contact = getItem(position);
		int contactType = contact.getContactType();
		
		ContactItemViewHolder viewHolder = null;
		if (convertView == null || convertView.getTag() == null) {
			
			switch (contactType) {
			case TYPE_CONTACT:
				convertView = LayoutInflater.from(mContext).inflate(R.layout.item_adapter_contact, null);
				viewHolder = new ContactItemViewHolder();
				viewHolder.ContactName = (TextView) convertView.findViewById(R.id.item_adapter_phone_contacts_text_view_contact_name);
				viewHolder.ContactBtn = (Button) convertView.findViewById(R.id.item_adapter_phone_contacts_btn_add_contact);
				break;
				
			case TYPE_CONTACT_WITH_BEBETTER:
				convertView = LayoutInflater.from(mContext).inflate(R.layout.item_adapter_contact, null);
				viewHolder = new ContactItemViewHolder();
				viewHolder.ContactName = (TextView) convertView.findViewById(R.id.item_adapter_phone_contacts_text_view_contact_name);
				viewHolder.ContactBtn = (Button) convertView.findViewById(R.id.item_adapter_phone_contacts_btn_add_contact);
				break;
				
			case TYPE_BEBETTER_FRIEND:
				convertView = LayoutInflater.from(mContext).inflate(R.layout.item_adapter_contact_be_better_friend, null);
				viewHolder = new ContactItemViewHolder();
				viewHolder.ContactName = (TextView) convertView.findViewById(R.id.item_adapter_contact_be_better_friend_text_view_friend_name);
				viewHolder.CheckBox = (CheckBox) convertView.findViewById(R.id.item_adapter_contact_be_better_friend_check_box);
				break;
			}
			

		}
		else{
			viewHolder = (ContactItemViewHolder) convertView.getTag();
		}
		
		if(contactType == TYPE_CONTACT || contactType == TYPE_CONTACT_WITH_BEBETTER){
			viewHolder.ContactName.setText(contact.getDisplayName());
			viewHolder.ContactBtn.setText("+");
		}
		
		else if(contactType == TYPE_BEBETTER_FRIEND){
			viewHolder.ContactName.setText(contact.getDisplayName());
			viewHolder.CheckBox.setSelected(false);
			viewHolder.CheckBox.setClickable(true);
			
			viewHolder.CheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					mListener.onBeBetterFriendCheckedChangedListener(contact, isChecked);					
				}
			});
			
		}
		
		return convertView;
	}
	
	public interface ContactItemAdapterListener {
		public void onBeBetterFriendCheckedChangedListener(Contact BeBetterFriendContact, boolean isChecked);
	}
	
	@Override
	public Filter getFilter(){
		if(mContactFilter == null){
			mContactFilter = new ContactsFilter();
		}
		return mContactFilter;
	}
	
	private class ContactsFilter extends Filter{

		@Override
		protected FilterResults performFiltering(CharSequence constraint) {
			FilterResults filterResults = new FilterResults();
			List<Contact> filteredItems = new ArrayList<Contact>();
			
			if(constraint != null && constraint.toString().length() > 0)
		    {
				constraint = constraint.toString().toLowerCase();
				for(Contact data : mContactOriginalData){
					String name = data.getDisplayName().toLowerCase();
					
					if(name.contains(constraint))
					{
						filteredItems.add(data);
					}
				}
				filterResults.values = filteredItems;
				filterResults.count = filteredItems.size();
		    }
			else{
				filterResults.values = mContactOriginalData;
				filterResults.count = mContactOriginalData.size();
			}
			
			return filterResults;
		}

		@Override
		protected void publishResults(CharSequence constraints, FilterResults results) {
			mContactFilterData = (ArrayList<Contact>)results.values;
			notifyDataSetChanged();
			clear();
			
			for(Contact data : mContactFilterData){
				add(data);
			}
			notifyDataSetInvalidated();
		}}
}
