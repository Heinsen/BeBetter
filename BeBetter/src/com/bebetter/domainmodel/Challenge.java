package com.bebetter.domainmodel;

import java.util.ArrayList;
import java.util.List;
import com.bebetter.R;

public class Challenge {

	int mChallengeID;
	String mHeadlineText;
	//Some list with items
	List<Integer> mPictures;
	
	public Challenge(){
		mPictures = new ArrayList<Integer>();
		mPictures.add(R.drawable.fyr);
		mPictures.add(R.drawable.dame);
		
		mHeadlineText = "TEST HeadLineText TEST";
	}
	
	public int getChallengeID() {
		return mChallengeID;
	}

	public void setChallengeID(int ChallengeID) {
		this.mChallengeID = ChallengeID;
	}

	public String getHeadlineText() {
		return mHeadlineText;
	}

	public void setHeadlineText(String HeadlineText) {
		this.mHeadlineText = HeadlineText;
	}

	public List<Integer> getPictures() {
		return mPictures;
	}

	public void setPictures(List<Integer> Pictures) {
		this.mPictures = Pictures;
	}
	
}
