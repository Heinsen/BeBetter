package com.bebetter.fragments;

import java.util.ArrayList;

import com.bebetter.R;
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
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class SettingsFragment extends Fragment{

	public static SettingsFragment newInstance() {
		SettingsFragment fragment = new SettingsFragment();

        return fragment;
    }
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
	    	
	    View challengeFeedFragmentView = inflater.inflate(R.layout.fragment_challenge_feed, container, false);
	    ListView listView = (ListView) challengeFeedFragmentView.findViewById(R.id.fragment_challenge_feed_list_view);
	    

	    return challengeFeedFragmentView;
	}
	
}
