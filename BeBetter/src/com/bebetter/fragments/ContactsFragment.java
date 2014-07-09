package com.bebetter.fragments;

import java.util.ArrayList;
import java.util.List;

import com.bebetter.R;
import com.bebetter.adapters.BeBetterContactItemAdapter;
import com.bebetter.adapters.BeBetterFriendItemAdapter;
import com.bebetter.adapters.ContactItemAdapter;

import android.app.Activity;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;

import com.bebetter.domainmodel.Contact;

public class ContactsFragment extends Fragment{

	static final int ALL_CONTACTS = 0;
	static final int BEBETTER_FRIENDS = 1;
	static final int TYPE_CONTACT = 0;
	static final int TYPE_CONTACT_WITH_BEBETTER = 1;
	static final int TYPE_BEBETTER_FRIEND = 2;
	private static int mIncludedContacts;
	
    // Defines a variable for the search string
    private String mSearchString = "";
    // Defines a variable for the List View
    ListView mContactsListView;
    // Defines a variable for the List View
    ListView mBeBetterContactsListView;
    // Defines a variable for the List View
    ListView mBeBetterFriendsListView;
    // Defines a variable for the Adapter
    ArrayAdapter<Contact> mContactsAdapter;
    // Defines a variable for the Adapter
    ArrayAdapter<Contact> mBeBetterContactsAdapter;
    // Defines a variable for the Adapter
    ArrayAdapter<Contact> mBeBetterFriendsAdapter;
    //For future reference, after onCreateView has been called
    View mFragmentContactsView;
    //For future reference, so findViewByID() only has to be called once
    SearchView mSearchView;
    //For use in the SearchView event handler
    final ContactsFragment mContactsFragment = this;
    // Defines list for Contacts
    List<Contact> mContacts;  
    // Defines list for ContactsWithBeBetter
    List<Contact> mBeBetterContacts;
    // Defines list for BeBetterFriends
    List<Contact> mBeBetterFriends;
    // Defines button for sending challenges
    Button mSendChallengeBtn;
    // Defines callback
    ContactsFragmentListener mListener;
    
    // A UI Fragment must inflate its View
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	//Fetch the SearchManager
    	SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
    	
    	// Inflate the fragment layout
    	if(mIncludedContacts == ALL_CONTACTS){
    		mFragmentContactsView = inflater.inflate(R.layout.fragment_contacts, container, false);
        	//Setting up SearchView
        	mSearchView = (SearchView) mFragmentContactsView.findViewById(R.id.fragment_contacts_search_view);   	
        	SearchableInfo searchableInfo = searchManager.getSearchableInfo(getActivity().getComponentName());
        	mSearchView.setSearchableInfo(searchableInfo);
    	}
    	else{
    		mFragmentContactsView = inflater.inflate(R.layout.fragment_contacts_be_better_friends, container, false);
        	//Setting up SearchView
        	mSearchView = (SearchView) mFragmentContactsView.findViewById(R.id.fragment_contacts_be_better_friends_search_view);   	
        	SearchableInfo searchableInfo = searchManager.getSearchableInfo(getActivity().getComponentName());
        	mSearchView.setSearchableInfo(searchableInfo);
        	
        	mSendChallengeBtn = (Button) mFragmentContactsView.findViewById(R.id.fragment_contacts_btn_send_challenge);
        	mSendChallengeBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					mListener.onSendChallengeBtnClick();			
				}
			}); 
    	}

    	return mFragmentContactsView;
    }
    
	public interface ContactsFragmentListener {
		public void onSendChallengeBtnClick();
		public void onContactClick(Contact selectedContact);
		public void onBeBetterFriendClick(Contact BeBetterFriend);
	}
    
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		// Verify that the host activity implements the callback interface
		try {
			// Instantiate the NoticeDialogListener so we can send events to the
			// host
			mListener = (ContactsFragmentListener) activity;
		} catch (ClassCastException e) {
			// The activity doesn't implement the interface, throw exception
			throw new ClassCastException(activity.toString()
					+ " must implement NoticeDialogListener");
		}
	}
    
    
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        
        SortContacts();
        InstantiateView();
        	
        mSearchView.setOnQueryTextListener(new OnQueryTextListener() {
			
			@Override
			public boolean onQueryTextSubmit(String query) {
				mSearchView.clearFocus();
				return true;
			}
			
			@Override
			public boolean onQueryTextChange(String newText) {
				mSearchString = mSearchView.getQuery().toString();
				
				if(mIncludedContacts == ALL_CONTACTS){
					mContactsAdapter.getFilter().filter(mSearchString);
					mBeBetterContactsAdapter.getFilter().filter(mSearchString);
					mBeBetterFriendsAdapter.getFilter().filter(mSearchString);
				}
				else
					mBeBetterFriendsAdapter.getFilter().filter(mSearchString);
				
				return true;
			}
		});
    }
    
    public static ContactsFragment newInstance(int IncludedContacts) {
    	mIncludedContacts = IncludedContacts;
    	ContactsFragment fragment = new ContactsFragment();
        return fragment;
    }
    
    //Instantiates the view
    private void InstantiateView(){

    	if(mIncludedContacts == ALL_CONTACTS)
    	{
    		// Gets the ListView from the View list of the parent activity
    		mContactsListView = (ListView) mFragmentContactsView.findViewById(R.id.fragment_contacts_list_view_phone_contacts);
    		// Setting up the adapter
    		mContactsAdapter = new ContactItemAdapter(getActivity(), R.id.fragment_contacts_list_view_phone_contacts, mContacts);
    		// Setting adapter
    		mContactsListView.setAdapter(mContactsAdapter);
    		
    		mContactsListView.setOnItemClickListener(new OnItemClickListener() {
        		@Override
    			public void onItemClick(AdapterView<?> parent, View view,
    					int position, long id) {			
        			mListener.onContactClick(mContacts.get(position));
    			}
    		});

    		// Gets the ListView from the View list of the parent activity
    		mBeBetterContactsListView = (ListView) mFragmentContactsView.findViewById(R.id.fragment_contacts_list_view_bebetter_contacts);
    		// Setting up the adapter
    		mBeBetterContactsAdapter = new BeBetterContactItemAdapter(getActivity(), R.id.fragment_contacts_list_view_phone_contacts, mBeBetterContacts);
    		// Setting adapter
    		mBeBetterContactsListView.setAdapter(mBeBetterContactsAdapter);
    		
            // Gets the ListView from the View list of the parent activity
            mBeBetterFriendsListView = (ListView) mFragmentContactsView.findViewById(R.id.fragment_contacts_list_view_bebetter_friends);
            // Setting up adapter
            mBeBetterFriendsAdapter = new BeBetterFriendItemAdapter(getActivity(), R.id.fragment_contacts_list_view_bebetter_friends, mBeBetterFriends, false);
            // Setting adapter
            mBeBetterFriendsListView.setAdapter(mBeBetterFriendsAdapter);
            
            // Show no bebetter friends text view if no friends are added
            if(mBeBetterFriends.size() == 0){
            	TextView noBeBetterFriendsTextView = (TextView) mFragmentContactsView.findViewById(R.id.fragment_contacts_text_view_no_bebetter_friends);
            	noBeBetterFriendsTextView.setVisibility(View.VISIBLE);
            	mBeBetterFriendsListView.setVisibility(View.GONE);
            }
            
            // Show no contacts text view if no contacts are added
            if(mContacts.size() == 0){
            	TextView noContactsTextView = (TextView) mFragmentContactsView.findViewById(R.id.fragment_contacts_text_view_no_phone_contacts);
            	noContactsTextView.setVisibility(View.VISIBLE);
            	mContactsListView.setVisibility(View.GONE);
            	RelativeLayout.LayoutParams beBetterFriendsHeadLineParams = (RelativeLayout.LayoutParams) mFragmentContactsView.findViewById(R.id.fragment_contacts_list_view_bebetter_friends).getLayoutParams();
            	beBetterFriendsHeadLineParams.addRule(RelativeLayout.BELOW, R.id.fragment_contacts_text_view_no_phone_contacts);
            }
            
    		// Show no friends text view if no friends are added
    		if(mBeBetterContacts.size() == 0){
    			TextView noFriendsTextView = (TextView) mFragmentContactsView.findViewById(R.id.fragment_contacts_text_view_no_contacts_with_bebetter);
    			noFriendsTextView.setVisibility(View.VISIBLE);
    			mBeBetterContactsListView.setVisibility(View.GONE);
    			RelativeLayout.LayoutParams contactsHeadLineParams = (RelativeLayout.LayoutParams) mFragmentContactsView.findViewById(R.id.fragment_contacts_text_view_phone_contacts_head_line).getLayoutParams();
    			contactsHeadLineParams.addRule(RelativeLayout.BELOW, R.id.fragment_contacts_text_view_no_contacts_with_bebetter);
    		}
    	}
    	else{
            // Gets the ListView from the View list of the parent activity
            mBeBetterFriendsListView = (ListView) mFragmentContactsView.findViewById(R.id.fragment_contacts_list_view_bebetter_friends);
            // Setting up adapter
            mBeBetterFriendsAdapter = new BeBetterFriendItemAdapter(getActivity(), R.id.fragment_contacts_list_view_bebetter_friends, mBeBetterFriends, true);
            // Setting adapter
            mBeBetterFriendsListView.setAdapter(mBeBetterFriendsAdapter);
    		// Hide no friends text view if some friends are added
            if(mBeBetterContacts.size() != 0)
    		{
    			TextView noFriendsTextView = (TextView) mFragmentContactsView.findViewById(R.id.fragment_contacts_be_better_friends_text_view_no_friends);
    			noFriendsTextView.setVisibility(View.GONE);
    			ListView listView = (ListView) mFragmentContactsView.findViewById(R.id.fragment_contacts_be_better_friends_list_view_bebetter_friends);
    			listView.setVisibility(View.VISIBLE);
    		}
    	}
    	
    	
        mBeBetterFriendsListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Contact selectedBeBetterFriend = mBeBetterFriends.get(position);
				if(selectedBeBetterFriend.getIsSelected())
					selectedBeBetterFriend.setIsSelected(false);
				else
					selectedBeBetterFriend.setIsSelected(true);
				mBeBetterFriendsAdapter.notifyDataSetChanged();
				mListener.onBeBetterFriendClick(selectedBeBetterFriend);
				
			}
		});
    	
    }
    
    private void SortContacts(){
    	//TODO Finish SortContacts function with async processing, and contacts seperation
    	//List<Contact> contacts = GetPhoneContacs();
    	mBeBetterContacts = new ArrayList<Contact>();
    	mBeBetterFriends = new ArrayList<Contact>();
    	mContacts = new ArrayList<Contact>();
    	
    	mBeBetterContacts.addAll(GetPhoneContacs());
    	mBeBetterFriends.addAll(GetPhoneContacs());
    	mContacts.addAll(GetPhoneContacs());
    	
    	//for(int i = 0; mBeBetterFriends.size() > i; i++){
    	//	Contact currentContact = mBeBetterFriends.get(i);
    	//}
    }
    
    private List<Contact> GetPhoneContacs(){
    	
    	List<Contact> phoneContacs = new ArrayList<Contact>();
    	
    	ContentResolver cr = getActivity().getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        if (cur.getCount() > 0) {
        	while (cur.moveToNext()) {
        		String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
        		
        		Contact currentContact = new Contact();
        		
        		currentContact.setDisplayName(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));
        		
        		if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
        			Cursor pCur = cr.query(
        					ContactsContract.CommonDataKinds.Phone.CONTENT_URI, 
        					null, 
        					ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?", 
        					new String[]{id}, null);
        			while (pCur.moveToNext()) {
        				currentContact.setPhoneNumber(pCur.getString(pCur.getColumnIndex(Phone.NUMBER)));
        				
        			} 
        			pCur.close();
        		}
        		if(currentContact.getPhoneNumber() != null)
        			phoneContacs.add(currentContact);
        	}
        }
        return phoneContacs;
    }

    public void setSendChallengeFriendBtnAvailability(boolean BtnEnabled){
    	mSendChallengeBtn.setEnabled(BtnEnabled);
    }
}
