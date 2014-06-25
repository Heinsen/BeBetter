package com.bebetter.fragments;

import com.bebetter.R;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class CameraPreviewFragment extends Fragment{

	ImageView mCameraPreviewImageView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View view = inflater.inflate(R.layout.fragment_camera_preview,
		        container, false);
		
		mCameraPreviewImageView = (ImageView) view.findViewById(R.id.fragment_camera_preview_image_view);
	
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
	
}
