package com.bebetter.domainmodel;

public class Contact {

	boolean mIsSelected;
	String mPhoneNumber;
	String mDisplayName;
	
	public boolean getIsSelected() {
		return mIsSelected;
	}
	
	public void setIsSelected(boolean mIsSelected) {
		this.mIsSelected = mIsSelected;
	}
	
	public String getPhoneNumber() {
		return mPhoneNumber;
	}
	public void setPhoneNumber(String mPhoneNumber) {
		this.mPhoneNumber = mPhoneNumber;
	}
	
	public String getDisplayName() {
		return mDisplayName;
	}
	public void setDisplayName(String mDisplayName) {
		this.mDisplayName = mDisplayName;
	}
	
}
