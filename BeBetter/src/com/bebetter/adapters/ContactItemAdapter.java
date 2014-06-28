package com.bebetter.adapters;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.TextView;
import com.bebetter.R;
import com.bebetter.domainmodel.Contact;

public class ContactItemAdapter extends ArrayAdapter<Contact>{

	private Filter mContactFilter;
	
	private List<Contact> mContactOriginalData;
	private List<Contact> mContactFilterData;
	//private ContactItemAdapterListener mListener;
	private Context mContext;
	
	public ContactItemAdapter(Context context, int resource, List<Contact> ContactItems) {
		super(context, resource, ContactItems);
	
		mContactOriginalData = new ArrayList<Contact>();
		mContactOriginalData.addAll(ContactItems);
		
		mContext = context;
		
//		try {
//			// Instantiate the NoticeDialogListener so we can send events to the host
//			mListener = (ContactItemAdapterListener) context;
//		} catch (ClassCastException e) {
//			// The activity doesn't implement the interface, throw exception
//			throw new ClassCastException(mContext.toString() + " must implement ContactItemAdapterListener");
//		}
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
		viewHolder.ContactBtn.setFocusable(false);
		viewHolder.ContactBtn.setVisibility(View.GONE);

		return convertView;
	}
	
	
	@Override
	public Filter getFilter(){
		if(mContactFilter == null){
			mContactFilter = new ItemAdapterFilter(this, mContactOriginalData);
		}
		return mContactFilter;
	}
}
