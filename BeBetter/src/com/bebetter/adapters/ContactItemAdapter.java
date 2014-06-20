package com.bebetter.adapters;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bebetter.R;
import com.bebetter.domainmodel.Challenge;
import com.bebetter.domainmodel.Contact;

public class ContactItemAdapter extends ArrayAdapter<Contact>{

	private Filter mContactFilter;
	
	private List<Contact> mContactOriginalData;
	private List<Contact> mContactFilterData;
	private Context mContext;
	
	public ContactItemAdapter(Context context, int resource, List<Contact> ContactItems) {
		super(context, resource, ContactItems);
	
		mContactOriginalData = new ArrayList<Contact>();
		mContactOriginalData.addAll(ContactItems);
		
		mContext = context;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		
		ContactItemViewHolder viewHolder;
		if (convertView == null || convertView.getTag() == null) {
						
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_adapter_contact, null);
			viewHolder = new ContactItemViewHolder();
			viewHolder.ContactName = (TextView) convertView.findViewById(R.id.item_adapter_phone_contacts_text_view_contact_name);
			viewHolder.ContactBtn = (Button) convertView.findViewById(R.id.item_adapter_phone_contacts_btn_add_contact);
		}
		else{
			viewHolder = (ContactItemViewHolder) convertView.getTag();
		}
		
		Contact contact = getItem(position);
		
		viewHolder.ContactName.setText(contact.getmDisplayName());
		viewHolder.ContactBtn.setText("+");
		
		return convertView;
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
					String name = data.getmDisplayName().toLowerCase();
					
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
