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
    private FrameLayout mFrameLayout;
    private Button mSwitchCameraButton;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		View cameraFragmentView = inflater.inflate(R.layout.fragment_camera, container, false);
        mFrameLayout = (FrameLayout) cameraFragmentView.findViewById(R.id.fragment_camera_preview);
	    
		// Add a listener to the Capture button
	    Button captureButton = (Button) cameraFragmentView.findViewById(R.id.fragment_camera_btn_capture);
	    captureButton.setOnClickListener(
	        new View.OnClickListener() {
	            @Override
	            public void onClick(View v) {
	            	mCallback.OnCameraFragmentBtnCaptureClicked();
	            }
	        }
	    );
	    
	    mSwitchCameraButton = (Button) cameraFragmentView.findViewById(R.id.fragment_camera_btn_toggle_chosen_camera);
	    mSwitchCameraButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mCallback.OnCameraFragmentBtnToggleChosenCameraClicked();
			}
		});
	    
	    return cameraFragmentView;
	}

	// Container Activity must implement this interface
	public interface ICameraActivityCallback{
		public void OnCameraFragmentBtnCaptureClicked();
		public void OnCameraFragmentBtnToggleChosenCameraClicked();
		public void OnFragmentResume();
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
    
    public void HideCameraBtnToggleCamera(){
    	mSwitchCameraButton.setVisibility(View.GONE);
    }
    
    public void SetCameraPreview(CameraPreview cameraPreview){
    	mFrameLayout.removeAllViews();
    	mFrameLayout.addView(cameraPreview);
    }
    
    @Override
    public void onDestroy() {
    	//mFrameLayout.removeAllViews();
    	super.onDestroy();
    }
    
    @Override
    public void onPause() {
    	//mFrameLayout.removeAllViews();
    	super.onPause();
    }
    
    @Override
    public void onResume() {
    	mCallback.OnFragmentResume();
    	super.onResume();
    }
}
