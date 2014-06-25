package com.bebetter.camera;

import java.io.IOException;

import com.bebetter.R;
import com.bebetter.activities.CameraActivity;
import com.bebetter.fragments.AlertDialogFragment;
import com.bebetter.fragments.AlertDialogFragment.AlertDialogFragmentListener;

import android.content.Context;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {

	private SurfaceHolder mHolder;
    private Camera mCamera;
    private CameraActivity mContext;
	
	public CameraPreview(Context context, Camera camera) {
		super(context);
		mCamera = camera;
		mContext = (CameraActivity) context;
        // Install a SurfaceHolder.Callback so we get notified when the
        // underlying surface is created and destroyed.
        mHolder = getHolder();
        mHolder.addCallback(this);
        // deprecated setting, but required on Android versions prior to 3.0
        //mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// No changes to preview, no rotation
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// The Surface has been created, now tell the camera where to draw the preview.
        try {
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
        } catch (IOException e) {
        	mContext.InstantiateAlertDialog(R.string.fragment_alert_dialog_error_title, R.string.fragment_alert_dialog_camera_preview);
        }		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		this.getHolder().removeCallback(this);
	}
}
