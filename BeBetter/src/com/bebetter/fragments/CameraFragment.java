package com.bebetter.fragments;

import com.bebetter.R;
import com.bebetter.camera.CameraPreview;
import com.bebetter.domainmodel.Challenge;
import com.bebetter.fragments.MainFragment.OnCurrentChallengesListener;

import android.app.Activity;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

public class CameraFragment extends Fragment{
	
	//private Camera mCamera;
    private CameraPreview mPreview;
    private ICameraActivityCallback mCallback;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		View cameraFragmentView = inflater.inflate(R.layout.fragment_camera, container, false);
		
		//mCamera = mCallback.getCameraInstance(1);
        //TODO else if no cameras are available
        
        // Create our Preview view and set it as the content of our activity.
        mPreview = mCallback.getCameraPreview();
		FrameLayout frameLayout = (FrameLayout) cameraFragmentView.findViewById(R.id.camera_preview);
		frameLayout.addView(mPreview);
	    
		// Add a listener to the Capture button
	    Button captureButton = (Button) cameraFragmentView.findViewById(R.id.button_capture);
	    captureButton.setOnClickListener(
	        new View.OnClickListener() {
	            @Override
	            public void onClick(View v) {
	            	mCallback.OnCameraFragmentBtnCaptureClicked();
	            }
	        }
	    );
	    
	    return cameraFragmentView;
	}

	// Container Activity must implement this interface
	public interface ICameraActivityCallback{
		public CameraPreview getCameraPreview();
		public void OnCameraFragmentBtnCaptureClicked();
	}
	
	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (ICameraActivityCallback) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }
	
    public static CameraFragment newInstance() {
    	CameraFragment fragment = new CameraFragment();

        return fragment;
    }
}
