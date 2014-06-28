package com.bebetter.fragments;

import java.util.ArrayList;
import java.util.List;

import com.bebetter.R;
import com.bebetter.activities.CameraActivity;
import com.bebetter.activities.ChallengeActivity;
import com.bebetter.adapters.ChallengeFeedItemAdapter;
import com.bebetter.domainmodel.Challenge;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ChallengeFeedFragment extends Fragment{

	ArrayAdapter<Challenge> mAdapter;
	List<Challenge> mChallenges;
	
    public static ChallengeFeedFragment newInstance() {
    	ChallengeFeedFragment fragment = new ChallengeFeedFragment();

        return fragment;
    }
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
	    
		mChallenges = new ArrayList<Challenge>(2);
		mChallenges.add(new Challenge());
		mChallenges.add(new Challenge());
		
	    View challengeFeedFragmentView = inflater.inflate(R.layout.fragment_challenge_feed, container, false);
	    ListView listView = (ListView) challengeFeedFragmentView.findViewById(R.id.fragment_challenge_feed_list_view);
	    
	    mAdapter = new ChallengeFeedItemAdapter(getActivity(), R.id.fragment_challenge_feed_list_view, mChallenges);
    	
    	listView.setAdapter(mAdapter);
    	
    	listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent CameraActivityIntent = new Intent(getActivity(), ChallengeActivity.class);
				CameraActivityIntent.putExtra("ChallengeID", mChallenges.get(position).getChallengeID());
				startActivity(CameraActivityIntent);
			}
		});
    	
	    return challengeFeedFragmentView;
	}
}
