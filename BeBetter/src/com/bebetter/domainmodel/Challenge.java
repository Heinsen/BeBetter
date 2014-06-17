package com.bebetter.domainmodel;

import java.util.ArrayList;
import java.util.List;
import com.bebetter.R;

public class Challenge {

	int mChallengeID;
	String mHeadlineText;
	String mBodyText;
	//Some list with items
	List<Integer> mPictures;
	
	public Challenge(){
		mPictures = new ArrayList<Integer>();
		mPictures.add(R.drawable.fyr);
		mPictures.add(R.drawable.dame);
		
		mHeadlineText = "TEST HeadLineText TEST";
		mBodyText = "TEST BodyText TEST";
	}

	public String getHeadlineText() {
		return mHeadlineText;
	}

	public void setHeadlineText(String HeadlineText) {
		this.mHeadlineText = HeadlineText;
	}

	public String getBodyText() {
		return mBodyText;
	}

	public void setBodyText(String BodyText) {
		this.mBodyText = BodyText;
	}

	public List<Integer> getPictures() {
		return mPictures;
	}

	public void setPictures(List<Integer> Pictures) {
		this.mPictures = Pictures;
	}
	
}
