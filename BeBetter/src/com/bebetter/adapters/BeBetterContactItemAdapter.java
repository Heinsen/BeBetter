package com.bebetter.adapters;

import java.util.ArrayList;
import java.util.List;

import com.bebetter.R;
import com.bebetter.domainmodel.Contact;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.TextView;


public class BeBetterContactItemAdapter extends ArrayAdapter<Contact> {

	private List<Contact> mContactOriginalData;
	private List<Contact> mContactFilterData;
	private ItemAdapterFilter mBeBetterContactFilter;
	private BeBetterContactItemAdapterListener mListener;
	private Context mContext;
	
	public BeBetterContactItemAdapter(Context context, int resource, List<Contact> ContactItems) {
		super(context, resource, ContactItems);
	
		mContactOriginalData = new ArrayList<Contact>();
		mContactOriginalData.addAll(ContactItems);
		
		mContext = context;
		
		try {
			// Instantiate the NoticeDialogListener so we can send events to the host
			mListener = (BeBetterContactItemAdapterListener) context;
		} catch (ClassCastException e) {
			// The activity doesn't implement the interface, throw exception
			throw new ClassCastException(mContext.toString() + " must implement BeBetterContactItemAdapterListener");
		}
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		
		final Contact contact = getItem(position);
		
		ContactItemViewHolder viewHolder = null;
		if (convertView == null || convertView.getTag() == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_adapter_contact, null);
			viewHolder = new ContactItemViewHolder();
			viewHolder.ContactName = (TextView) convertView.findViewById(R.id.item_adapter_phone_contacts_text_view_contact_name);
			viewHolder.ContactBtn = (Button) convertView.findViewById(R.id.item_adapter_phone_contacts_btn_add_contact);	
		}
		else{
			viewHolder = (ContactItemViewHolder) convertView.getTag();
		}

		viewHolder.ContactName.setText(contact.getDisplayName());
		viewHolder.ContactBtn.setText("Add Contact");
		
		viewHolder.ContactBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mListener.onBeBetterFriendSelectedListener(contact);
			}
		});

		return convertView;
	}
	
	public interface BeBetterContactItemAdapterListener {
		public void onBeBetterFriendSelectedListener(Contact BeBetterFriendContact);
	}
	
	@Override
	public Filter getFilter(){
		if(mBeBetterContactFilter == null){
			mBeBetterContactFilter = new ItemAdapterFilter(this, mContactOriginalData);
		}
		return mBeBetterContactFilter;
	}	
}
