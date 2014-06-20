package com.bebetter.fragments;

import java.util.ArrayList;
import java.util.List;

import com.bebetter.R;
import com.bebetter.adapters.ChallengeFeedItemAdapter;
import com.bebetter.domainmodel.Challenge;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainFragment extends Fragment{
	
	private ArrayAdapter mAdapter;
	private List<Challenge> mCurrentChallengeListItems;
	private OnCurrentChallengesListener mCallback;
	
    public static MainFragment newInstance() {
    	MainFragment fragment = new MainFragment();

        return fragment;
    }
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		
	    View mainFragmentView = inflater.inflate(R.layout.fragment_main, container, false);
	    ListView listView = (ListView) mainFragmentView.findViewById(R.id.fragment_main_list_view_current_challenges);
	    
	    mCurrentChallengeListItems = new ArrayList<Challenge>();
	    mCurrentChallengeListItems.add(new Challenge());
	    
	    List<String> mCurrentChallengeListItemHeadlineTexts = new ArrayList<String>();
	    
	    for(int i = 0; mCurrentChallengeListItems.size() > i; i++){
	    	mCurrentChallengeListItemHeadlineTexts.add(mCurrentChallengeListItems.get(i).getHeadlineText());
	    }
	    
	    mAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, mCurrentChallengeListItemHeadlineTexts);
        listView.setAdapter(mAdapter);
        
        listView.setOnItemClickListener(new OnItemClickListener() {
    		@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
    			mCallback.OnCurrentChallengeSelected(mCurrentChallengeListItems.get(position));    			
			}

		});
	    
	    return mainFragmentView;
	}
	
	// Container Activity must implement this interface
	public interface OnCurrentChallengesListener{
		public void OnCurrentChallengeSelected(Challenge selectedCurrentChallengeListItem);
	}
	
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnCurrentChallengesListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }
	
}
