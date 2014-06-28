package com.bebetter.adapters;

import java.util.ArrayList;
import java.util.List;

import android.widget.ArrayAdapter;
import android.widget.Filter;

import com.bebetter.domainmodel.Contact;

public class ItemAdapterFilter extends Filter{

	private List<Contact> mOriginalData;
	private List<Contact> mFilterData;
	private ArrayAdapter<Contact> mArrayAdapter;
	
	public ItemAdapterFilter(ArrayAdapter<Contact> arrayAdapter, List<Contact> FilterItems) {
		
		mArrayAdapter = arrayAdapter;
		mOriginalData = new ArrayList<Contact>();
		mOriginalData.addAll(FilterItems);
	}
	
	@Override
	protected FilterResults performFiltering(CharSequence constraint) {
		FilterResults filterResults = new FilterResults();
		List<Contact> filteredItems = new ArrayList<Contact>();
		
		if(constraint != null && constraint.toString().length() > 0)
	    {
			constraint = constraint.toString().toLowerCase();
			for(Contact data : mOriginalData){
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
			filterResults.values = mOriginalData;
			filterResults.count = mOriginalData.size();
		}
		
		return filterResults;
	}

	@Override
	protected void publishResults(CharSequence constraints, FilterResults results) {
		mFilterData = (ArrayList<Contact>)results.values;
		
		mArrayAdapter.notifyDataSetChanged();
		mArrayAdapter.clear();
		
		for(Contact data : mFilterData){
			mArrayAdapter.add(data);
		}
		mArrayAdapter.notifyDataSetInvalidated();
	}
	
}
