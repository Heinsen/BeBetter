package com.bebetter.domainmodel;

import java.util.Date;

import android.content.Context;

import com.bebetter.R;

public class BeBetterNotification {
	
	private int mBeBetterNotificationType;
	private String mHeadLineText;
	private String mBtnText;
	private String mTimeStampText = "";
	private Date mTimeStamp;
	private Context mContext;
	
	public BeBetterNotification(Context Context, int BeBetterNotificationType){
		mContext = Context;
		mBeBetterNotificationType = BeBetterNotificationType;
	}
	
	public int getmBeBetterNotificationType() {
		return mBeBetterNotificationType;
	}
	public int getBeBetterNotificationType() {
		return mBeBetterNotificationType;
	}
	public void setBeBetterNotificationType(int BeBetterNotificationType) {
		this.mBeBetterNotificationType = BeBetterNotificationType;
	}
	public String getHeadLineText() {
		return mHeadLineText;
	}
	public void setHeadLineText(String headLineText) {
		mHeadLineText = headLineText;
	}
	public String getBtnText() {
		return mBtnText;
	}
	public void setBtnText(String btnText) {
		mBtnText = btnText;
	}
	public String getTimeStampText() {
		if(mTimeStamp != null){

			Date currentDate = new Date();
			long diffInMillis = currentDate.getTime() - mTimeStamp.getTime();
			long diffInMin = diffInMillis/1000/60;
			long diffInHour = diffInMin/60;
			long diffInDay = diffInHour/24;
			long diffInMonth = diffInDay/30;

			if(diffInMin < 10){
				mTimeStampText = mContext.getResources().getString(R.string.TIME_MINUTE_ONE_INTERVAL);
			}
			else if(diffInMin <= 15){
				mTimeStampText = "10" + mContext.getResources().getString(R.string.TIME_MINUTE_INTERVAL);
			}
			else if(diffInMin <= 20){
				mTimeStampText = "15" + mContext.getResources().getString(R.string.TIME_MINUTE_INTERVAL);
			}
			else if(diffInMin <= 30){
				mTimeStampText = "20" + mContext.getResources().getString(R.string.TIME_MINUTE_INTERVAL);
			}
			else if(diffInMin <= 40){
				mTimeStampText = "30" + mContext.getResources().getString(R.string.TIME_MINUTE_INTERVAL);
			}
			else if(diffInHour <= 1.5){
				mTimeStampText = mContext.getResources().getString(R.string.TIME_HOUR_ONE_INTERVAL);
			}
			else if(diffInHour > 1.5){
				int nHoursDiff = (int) diffInHour;
				mTimeStampText = nHoursDiff + mContext.getResources().getString(R.string.TIME_HOUR_INTERVAL);
			}
			else if(diffInDay < 2){ //under 2 days
				mTimeStampText = mContext.getResources().getString(R.string.TIME_DAY_ONE_INTERVAL);
			}
			else if(diffInDay >= 2){ //over 1 day
				int nDaysDiff = (int) diffInDay;
				mTimeStampText = nDaysDiff + mContext.getResources().getString(R.string.TIME_DAY_INTERVAL);
			}
			else if(diffInMonth < 2){ //under 2 month
				mTimeStampText = mContext.getResources().getString(R.string.TIME_MONTH_ONE_INTERVAL);
			}
			else if(diffInMonth <= 3){ //under 3 months
				int nMonthsDiff = (int) diffInMonth;
				mTimeStampText = nMonthsDiff + mContext.getResources().getString(R.string.TIME_MONTH_INTERVAL);
			}
			else
				mTimeStampText = ""; //Over 3.0 months
		}
		return mTimeStampText;
	}

	public void setTimeStamp(Date timeStamp) {
		mTimeStamp = timeStamp;
	}
}
