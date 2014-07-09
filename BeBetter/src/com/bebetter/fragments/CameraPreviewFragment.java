package com.bebetter.fragments;

import com.bebetter.R;
import com.bebetter.fragments.NotificationCenterFragment.OnNotificationFragmentSelectedListener;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

public class CameraPreviewFragment extends Fragment{

	ImageView mCameraPreviewImageView;
	private OnCamereaPreviewFragmentSelectedListener mCallback;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View view = inflater.inflate(R.layout.fragment_camera_preview,
		        container, false);
		
		mCameraPreviewImageView = (ImageView) view.findViewById(R.id.fragment_camera_preview_image_view);
	
		Button RetakeImageBtn = (Button) view.findViewById(R.id.fragment_camera_preview_btn_retake_image);
		RetakeImageBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mCallback.onRetakeImageBtnClicked();				
			}
		});
		
		Button AcceptImageBtn = (Button) view.findViewById(R.id.fragment_camera_preview_btn_accept_image);
		AcceptImageBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mCallback.onAcceptImageBtnClicked();				
			}
		});
		
		return view;
	}
	
    public static CameraPreviewFragment newInstance() {
    	CameraPreviewFragment fragment = new CameraPreviewFragment();

        return fragment;
    }
    
    public void setImageView(Bitmap Bitmap)
    {
    	mCameraPreviewImageView.setImageBitmap(Bitmap);
    }
    
    public interface OnCamereaPreviewFragmentSelectedListener{
    	void onRetakeImageBtnClicked();
    	void onAcceptImageBtnClicked();
    }
    
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnCamereaPreviewFragmentSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }
	
}
