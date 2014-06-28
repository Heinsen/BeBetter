package com.bebetter.fragments;

import com.bebetter.R;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ChallengeFragment extends Fragment{
	
	private ImageSwitcher mImageSwitcher;
	//private IChallengeFragmentCallback mCallback;
	String mPicturePath = "";
	ImageView mImageView;
	ProgressBar mProgressBar;
	TextView mTextView;
	
	public static ChallengeFragment newInstance(){
		ChallengeFragment challengeFragment = new ChallengeFragment();		
		return challengeFragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		
		View view = inflater.inflate(R.layout.fragment_challenge, container, false);
		
		mImageView = (ImageView) view.findViewById(R.id.fragment_challenge_image_view);
		mProgressBar = (ProgressBar) view.findViewById(R.id.fragment_challenge_progress_bar);
		mTextView = (TextView) view.findViewById(R.id.fragment_challenge_text_view);
		SetImageAndText();
        return view;	
	}
	
	public void SetImageAndText(){
		//TODO get image and image text from Database
		//Setting the image resource and showing the image view
		mImageView.setImageResource(R.drawable.fyr);
		mImageView.setVisibility(View.VISIBLE);
		//Setting text and showing the text view
		mTextView.setText("Test Text");
		mTextView.setVisibility(View.VISIBLE);
		
		//Removing the progress bar from the view
		mProgressBar.setVisibility(View.GONE);
	}
	
}
