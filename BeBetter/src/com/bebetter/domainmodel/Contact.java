package com.bebetter.domainmodel;

public class Contact {

	private int mContactType = 100;
	boolean mIsChecked;
	String mPhoneNumber;
	String mDisplayName;

	public int getContactType() {
		return mContactType;
	}
	public void setContactType(int ContactType) {
		this.mContactType = ContactType;
	}
	
	public boolean isChecked() {
		return mIsChecked;
	}
	
	public void setChecked(boolean isChecked) {
		this.mIsChecked = isChecked;
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
