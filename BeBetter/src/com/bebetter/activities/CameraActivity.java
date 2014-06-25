package com.bebetter.activities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.bebetter.R;
import com.bebetter.activities.ActionBarTabsPagerActivity.MyAdapter;
import com.bebetter.adapters.ContactItemAdapter.ContactItemAdapterListener;
import com.bebetter.camera.CameraPreview;
import com.bebetter.domainmodel.Contact;
import com.bebetter.fragments.AlertDialogFragment;
import com.bebetter.fragments.AlertDialogFragment.AlertDialogFragmentListener;
import com.bebetter.fragments.CameraFragment;
import com.bebetter.fragments.CameraPreviewFragment;
import com.bebetter.fragments.ChallengeFeedFragment;
import com.bebetter.fragments.ContactsFragment;
import com.bebetter.fragments.ContactsFragment.ContactsFragmentListener;
import com.bebetter.fragments.MainFragment;
import com.bebetter.fragments.CameraFragment.ICameraActivityCallback;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.media.ExifInterface;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.content.DialogInterface;
import android.content.Intent;

public class CameraActivity extends FragmentActivity implements ICameraActivityCallback, AlertDialogFragmentListener, ContactItemAdapterListener, ContactsFragmentListener{

	static final int BEBETTER_FRIENDS = 1;
	static final Boolean BACK_CAMERA = true; 
	static final Boolean FRONT_CAMERA = false;
	static final int NUM_ITEMS = 3;
	MyAdapter mAdapter;
	ViewPager mPager;
	private Camera mBackCamera;
	private Camera mFrontCamera;
	private static SparseArray<Fragment> mRegisteredFragments = new SparseArray<Fragment>();
	File mPictureFile;
	Boolean mChosenCamera;
	Boolean mImageHasBeenTake = false;
	CameraPreview mCameraPreview;
	List<Contact> mSelectedContacts;
	static ContactsFragment mContactsFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera); 
        
		mAdapter = new MyAdapter(getSupportFragmentManager());
		mPager = (ViewPager)findViewById(R.id.activity_camera_pager);
		mPager.setAdapter(mAdapter);
		mSelectedContacts = new ArrayList<Contact>();

		//Instantiating cameras
		mBackCamera = getCameraInstance(Camera.CameraInfo.CAMERA_FACING_BACK);
		mFrontCamera = getCameraInstance(Camera.CameraInfo.CAMERA_FACING_FRONT);
			
		//Show alarm message if no cameras are available
		if(mBackCamera == null && mFrontCamera == null){
			InstantiateAlertDialog(R.string.fragment_alert_dialog_error_title, R.string.fragment_alert_dialog_no_camera_available);
		}
		//Show FRONT_CAMERA if BACK_CAMERA is not available
		else if(mBackCamera == null){
			mChosenCamera = FRONT_CAMERA;
		}
		 //Show BACK_CAMERA if it is available
		else{
			mChosenCamera = BACK_CAMERA;
		}
		
		//Setting up OnPageChangeListener
        mPager.setOnPageChangeListener(new OnPageChangeListener() {
    		
    		@Override
    		public void onPageSelected(int arg0) {
    			// TODO Auto-generated method stub
    		}
    		
    		@Override
    		public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    			//If the offset to the page in position is 0
    			if(positionOffset == 0 && mPager.getCurrentItem() == 0){
    					if(mImageHasBeenTake){
    						ReInstanciatePreview();
    						mImageHasBeenTake = false;
    					}
    			}
    		}
    		
    		@Override
    		public void onPageScrollStateChanged(int position) {
    			// TODO Auto-generated method stub
    		}
    	});
    }
    
    private PictureCallback mPicture = new PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {

            mPictureFile = getOutputMediaFile();
            if (mPictureFile == null){
            	InstantiateAlertDialog(R.string.fragment_alert_dialog_error_title, R.string.fragment_alert_dialog_file_error);
                return;
            }
            try {
                FileOutputStream fos = new FileOutputStream(mPictureFile);
                fos.write(data);
                fos.close();
            } catch (FileNotFoundException e) {
            	InstantiateAlertDialog(R.string.fragment_alert_dialog_error_title, R.string.fragment_alert_dialog_file_error);
            } catch (IOException e) {
            	InstantiateAlertDialog(R.string.fragment_alert_dialog_error_title, R.string.fragment_alert_dialog_file_error);
            }
            //Switching to CameraPreviewFragment
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
            	InstantiateAlertDialog(R.string.fragment_alert_dialog_error_title, R.string.fragment_alert_dialog_file_error);
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
			InstantiateAlertDialog(R.string.fragment_alert_dialog_error_title, R.string.fragment_alert_dialog_file_error);
			e.printStackTrace();
			return null;
		}
        return mediaFile;
    }

    private Bitmap setImageInPortrait(String ImagePath){
    	Bitmap bitmap = BitmapFactory.decodeFile(ImagePath);
    	
    	//If width is greater than height, rotate 90 degrees
    	if (bitmap.getWidth() > bitmap.getHeight()) {
            Matrix matrix = new Matrix();
            matrix.postRotate(90);
            return bitmap = Bitmap.createBitmap(bitmap , 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        }
    	else
    		return bitmap;
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
			if(position == 2){
				mContactsFragment = ContactsFragment.newInstance(BEBETTER_FRIENDS);
				return mContactsFragment;
			}
			if(position == 1){
				return CameraPreviewFragment.newInstance();
			}
			if(position == 0)
				return CameraFragment.newInstance();
			
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
    
	public void InstantiateAlertDialog(int dialogTitle, int dialogMsg){
		//Instantiate an AlertDialog.Builder with its constructor
		AlertDialogFragment alertDialogBuilder = new AlertDialogFragment();
		//Setter methods to set the dialog characteristics
		alertDialogBuilder.setDialogText(getResources().getString(dialogTitle), getResources().getString(dialogMsg));
		//Showing dialog
		alertDialogBuilder.show(getSupportFragmentManager(), "AlertDialogFragment");
	}
    
    private Camera getCameraInstance(int cameraID) {
    	Camera camera = null;
    	try {
    		camera = Camera.open(cameraID); // attempt to get a Camera instance
    		camera.setDisplayOrientation(90);
    		Camera.Parameters params = camera.getParameters();
    		params.setRotation(90);
    		camera.setParameters(params);
    	}
    	catch (Exception e){
    		return null;
    	}
    	return camera;
	}

	public CameraPreview getBackCameraPreview() {
		if(mBackCamera != null){
			return new CameraPreview(this, mBackCamera);
		}
		else
			return null;
	}
	
	private CameraPreview getFrontCameraPreview(){
		if(mFrontCamera != null){
			return new CameraPreview(this, mFrontCamera);
		}
		else
			return null;
	}
	
	@Override
	public void OnCameraFragmentBtnToggleChosenCameraClicked(){
		//Toggle the chosen camera, and make a new preview sending it to the CameraFragment
		if(mChosenCamera == BACK_CAMERA && mFrontCamera != null){
			mChosenCamera = FRONT_CAMERA;
			ReInstanciatePreview();
		}
		else if(mChosenCamera == FRONT_CAMERA && mBackCamera != null){
			mChosenCamera = BACK_CAMERA;
			ReInstanciatePreview();
		}
	}
	
	@Override
	public void OnCameraFragmentBtnCaptureClicked() {
		// get an image from the camera
		if(mChosenCamera == BACK_CAMERA && mBackCamera != null){
			mBackCamera.takePicture(null, null, mPicture);
		}
		else if(mChosenCamera == FRONT_CAMERA && mFrontCamera != null){
			mFrontCamera.takePicture(null, null, mPicture);
		}
		mImageHasBeenTake = true;
	}

	private void SwitchToCameraPreviewFragment(){
		//Switching to CameraPreviewFragment
		mPager.setCurrentItem(1);
		//Sending the taken picture to the CameraPreviewFragment
        ((CameraPreviewFragment) mAdapter.getRegisteredFragment(1)).setImageView(setImageInPortrait(mPictureFile.getAbsolutePath()));
	}
	
	private void stopPreviewAndFreeCamera() {

	    if (mBackCamera != null) {
	        // Call stopPreview() to stop updating the preview surface.
	    	try {
	    		mBackCamera.stopPreview();
	    	} catch (Exception e){
	    		// ignore: tried to stop a non-existent preview
	    	}
	        // Releasing the BackCamera 
	        mBackCamera.release();
	        mBackCamera = null;
	    }
	    
	    if(mFrontCamera != null){
	        // Call stopPreview() to stop updating the preview surface.
	    	 try {
	    		 mFrontCamera.stopPreview();
	         } catch (Exception e){
	           // ignore: tried to stop a non-existent preview
	         }	    
	        // Releasing the FrontCamera 
	    	mFrontCamera.release();
	    
	    	mFrontCamera = null;
	    }
	}
	
	private void ReInstanciatePreview(){
		if(mChosenCamera == BACK_CAMERA){
			try {
				mBackCamera.stopPreview();
			} catch (Exception e){
				// ignore: tried to stop a non-existent preview
			}
			if( mAdapter.getRegisteredFragment(0) != null)
				((CameraFragment) mAdapter.getRegisteredFragment(0)).SetCameraPreview(getBackCameraPreview());
		}
		else if(mChosenCamera == FRONT_CAMERA){
			try{
				mFrontCamera.stopPreview();
			} catch (Exception e){
				// ignore: tried to stop a non-existent preview
			}
			if( mAdapter.getRegisteredFragment(0) != null)
				((CameraFragment) mAdapter.getRegisteredFragment(0)).SetCameraPreview(getFrontCameraPreview());
		}
	}
	
	@Override
	protected void onDestroy() {
		stopPreviewAndFreeCamera();
		super.onDestroy();
	}
	
	@Override
	protected void onPause() {
		stopPreviewAndFreeCamera();
		super.onPause();
	}
	
	@Override
	protected void onResume() {
		//Instantiating cameras
		if(mBackCamera == null)
			mBackCamera = getCameraInstance(Camera.CameraInfo.CAMERA_FACING_BACK);
		if(mFrontCamera == null)
			mFrontCamera = getCameraInstance(Camera.CameraInfo.CAMERA_FACING_FRONT);
		
		ReInstanciatePreview();

		super.onResume();
	}
	
	@Override
	public void OnFragmentResume() {
		ReInstanciatePreview();
	}

	
	@Override
	public void onDialogDismiss() {
		stopPreviewAndFreeCamera();
		this.finish();
	}

	@Override
	public void onBeBetterFriendCheckedChangedListener(Contact BeBetterFriendContact, boolean isChecked) {
		if(isChecked){
			mSelectedContacts.add(BeBetterFriendContact);
		}
		else{
			mSelectedContacts.remove(BeBetterFriendContact);
		}
		
		if(mSelectedContacts.size() == 0){
			mContactsFragment.setSendChallengeFriendBtnAvailability(false);
		}
		else{
			mContactsFragment.setSendChallengeFriendBtnAvailability(true);
		}
	}
	
	private void SendChallenge(){
		//TODO Send challenge to server site and show user the challenge in the feed
		stopPreviewAndFreeCamera();
		this.finish();
	}

	@Override
	public void onSendChallengeBtnClick() {
		SendChallenge();
		
	}
	
}
