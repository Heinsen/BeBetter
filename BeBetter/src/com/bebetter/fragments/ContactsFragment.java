package com.bebetter.fragments;

import java.util.ArrayList;
import java.util.List;

import com.bebetter.R;
import com.bebetter.adapters.ChallengeFeedItemAdapter;
import com.bebetter.adapters.ContactItemAdapter;
import com.bebetter.domainmodel.Challenge;
import com.bebetter.domainmodel.Contact;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Contacts;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.app.LoaderManager;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;

public class ContactsFragment extends Fragment {

    
    // Defines a variable for the search string
    private String mSearchString = "";
    // Defines a variable for the List View
    ListView mContactsList;
    // Defines a variable for the Adapter
    ArrayAdapter<Contact> mAdapter;
    //For future reference, after onCreateView has been called
    View mFragmentContactsView;
    //For future reference, so findViewByID() only has to be called once
    SearchView mSearchView;
    //For use in the SearchView event handler
    final ContactsFragment mContactsFragment = this;

    // A UI Fragment must inflate its View
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the fragment layout
    	mFragmentContactsView = inflater.inflate(R.layout.fragment_contacts,
            container, false);
    	
    	//Setting up SearchView
    	SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
    	mSearchView = (SearchView) mFragmentContactsView.findViewById(R.id.fragment_contacts_search_view);   	
    	SearchableInfo searchableInfo = searchManager.getSearchableInfo(getActivity().getComponentName());
    	mSearchView.setSearchableInfo(searchableInfo);
    	
    	return mFragmentContactsView;
    }
    
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        
        // Gets the ListView from the View list of the parent activity
        mContactsList = (ListView) mFragmentContactsView.findViewById(R.id.fragment_contacts_list_view_phone_contacts);
        // Setting up the adapter
        // TODO GetPhoneContacts, name mAdapter more specific
        mAdapter = new ContactItemAdapter(getActivity(), R.id.fragment_contacts_list_view_phone_contacts, GetPhoneContacs());
        
        mContactsList.setAdapter(mAdapter);
       
        mSearchView.setOnQueryTextListener(new OnQueryTextListener() {
			
			@Override
			public boolean onQueryTextSubmit(String query) {
				mSearchView.clearFocus();
				return true;
			}
			
			@Override
			public boolean onQueryTextChange(String newText) {
				mSearchString = mSearchView.getQuery().toString();
				mAdapter.getFilter().filter(mSearchString);
				return true;
			}
		});
    }
    
	
    public static ContactsFragment newInstance() {
    	ContactsFragment fragment = new ContactsFragment();

        return fragment;
    }
    
    private List<Contact> GetPhoneContacs(){
    	
    	List<Contact> phoneContacs = new ArrayList<Contact>();
    	
    	ContentResolver cr = getActivity().getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        if (cur.getCount() > 0) {
        	while (cur.moveToNext()) {
        		String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
        		
        		Contact currentContact = new Contact();
        		
        		currentContact.setmDisplayName(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));
        		
        		if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
        			Cursor pCur = cr.query(
        					ContactsContract.CommonDataKinds.Phone.CONTENT_URI, 
        					null, 
        					ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?", 
        					new String[]{id}, null);
        			while (pCur.moveToNext()) {
        				currentContact.setmPhoneNumber(pCur.getString(pCur.getColumnIndex(Phone.NUMBER)));
        				
        			} 
        			pCur.close();
        		}
        		if(currentContact.getmPhoneNumber() != null)
        			phoneContacs.add(currentContact);
        	}
        }
        return phoneContacs;
    }  
}
