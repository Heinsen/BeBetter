package com.bebetter.activities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.bebetter.R;
import com.bebetter.activities.ActionBarTabsPagerActivity.MyAdapter;
import com.bebetter.camera.CameraPreview;
import com.bebetter.fragments.CameraFragment;
import com.bebetter.fragments.CameraPreviewFragment;
import com.bebetter.fragments.ChallengeFeedFragment;
import com.bebetter.fragments.ContactsFragment;
import com.bebetter.fragments.MainFragment;
import com.bebetter.fragments.CameraFragment.ICameraActivityCallback;

import android.app.Activity;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.content.Intent;

public class CameraActivity extends FragmentActivity implements ICameraActivityCallback{

	static final int NUM_ITEMS = 2;
	MyAdapter mAdapter;
	ViewPager mPager;
	private CameraPreviewFragment mCameraPreviewFragment;
	private Camera mCamera;
	private static SparseArray<Fragment> mRegisteredFragments = new SparseArray<Fragment>();
	String mPicturePath;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera); 
        
		mAdapter = new MyAdapter(getSupportFragmentManager());
		
		mPager = (ViewPager)findViewById(R.id.activity_camera_pager);
		mPager.setAdapter(mAdapter);
    }
    
    private PictureCallback mPicture = new PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {

            File pictureFile = getOutputMediaFile();//getOutputMediaFile(MEDIA_TYPE_IMAGE);
            if (pictureFile == null){
                // TODO Error creating media file, check storage permissions
                return;
            }

            try {
                FileOutputStream fos = new FileOutputStream(pictureFile);
                fos.write(data);
                fos.close();
            } catch (FileNotFoundException e) {
                // TODO File not found
            } catch (IOException e) {
                // TODO Error accessing file
            }
            mPicturePath = pictureFile.getAbsolutePath();
            SwitchToCameraPreviewFragment();
         }
    };
    
    
    private File getOutputMediaFile(){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir =  this.getCacheDir();

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                // TODO failed to create directory 
                return null;
            }
        }
        // Create a media file name
        //String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        //File mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG.jpg");
        File mediaFile;
		try {
			mediaFile = File.createTempFile("prefix", "extension", mediaStorageDir);
		} catch (IOException e) {
			// TODO failed to create temporary file
			e.printStackTrace();
			return null;
		}

        return mediaFile;
    }

    public static class MyAdapter extends FragmentPagerAdapter {
		public MyAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public int getCount() {
			return NUM_ITEMS;
		}

		@Override
		public Fragment getItem(int position) {
			if(position == 1){
				return CameraPreviewFragment.newInstance();
			}
			if(position == 0)
				return CameraFragment.newInstance();

			else
				return null;
		}
		
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			Fragment fragment = (Fragment) super.instantiateItem(container, position);
			mRegisteredFragments.put(position, fragment);
			return fragment;
		}
		
	    @Override
	    public void destroyItem(ViewGroup container, int position, Object object) {
	    	mRegisteredFragments.remove(position);
	        super.destroyItem(container, position, object);
	    }
	    
	    public Fragment getRegisteredFragment(int position) {
	        return mRegisteredFragments.get(position);
	    }			
	}
    
	private Camera getCameraInstance(int cameraID) {
    	Camera c = null;
    	try {
    		c = Camera.open(cameraID); // attempt to get a Camera instance
    	}
    	catch (Exception e){
    		// TODO Camera is not available (in use or does not exist)
    	}
    	mCamera = c;
    	return mCamera; // TODO returns null if camera is unavailable
	}

	@Override
	public CameraPreview getCameraPreview() {
		return new CameraPreview(this, getCameraInstance(1));//TODO Change how the camera is selected
	}
	

	@Override
	public void OnCameraFragmentBtnCaptureClicked() {
		// get an image from the camera
        mCamera.takePicture(null, null, mPicture);
        
        
	}

	private void SwitchToCameraPreviewFragment(){
		mPager.setCurrentItem(1);
        CameraPreviewFragment cameraPreviewFragment = (CameraPreviewFragment) mAdapter.getRegisteredFragment(1);
        cameraPreviewFragment.setImageView(mPicturePath);
	}
	
}
