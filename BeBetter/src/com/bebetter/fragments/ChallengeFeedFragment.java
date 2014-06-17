package com.bebetter.fragments;

import java.util.ArrayList;
import java.util.List;

import com.bebetter.R;
import com.bebetter.adapters.ChallengeFeedItemAdapter;
import com.bebetter.domainmodel.Challenge;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ChallengeFeedFragment extends Fragment{

	ArrayAdapter<Challenge> mAdapter;
	
    public static ChallengeFeedFragment newInstance() {
    	ChallengeFeedFragment fragment = new ChallengeFeedFragment();

        return fragment;
    }
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
	    
		List<Challenge> challenges = new ArrayList<Challenge>(2);
		challenges.add(new Challenge());
		challenges.add(new Challenge());
		
	    View challengeFeedFragmentView = inflater.inflate(R.layout.fragment_challenge_feed, container, false);
	    ListView listView = (ListView) challengeFeedFragmentView.findViewById(R.id.fragment_challenge_feed_list_view);
	    
	    mAdapter = new ChallengeFeedItemAdapter(getActivity(), R.id.fragment_challenge_feed_list_view, challenges);
    	
    	listView.setAdapter(mAdapter);
	    
	    return challengeFeedFragmentView;
	}
}
