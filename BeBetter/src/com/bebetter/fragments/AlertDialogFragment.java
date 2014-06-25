package com.bebetter.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class AlertDialogFragment extends DialogFragment {

	AlertDialogFragmentListener mListener;
	private String mDialogTitle = "";
	private String mDialogMsg = "";

	public interface AlertDialogFragmentListener {
		public void onDialogDismiss();
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Build the dialog and set up the button click handlers
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(mDialogMsg)
				.setMessage(mDialogTitle)
				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// Send the negative button event back to the
								// host activity
								mListener.onDialogDismiss();
							}
						});
		return builder.create();
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		// Verify that the host activity implements the callback interface
		try {
			// Instantiate the NoticeDialogListener so we can send events to the
			// host
			mListener = (AlertDialogFragmentListener) activity;
		} catch (ClassCastException e) {
			// The activity doesn't implement the interface, throw exception
			throw new ClassCastException(activity.toString()
					+ " must implement NoticeDialogListener");
		}
	}
	
	@Override
	public void onDismiss(DialogInterface dialog) {
		mListener.onDialogDismiss();
		super.onDismiss(dialog);
	}

	public void setDialogText(String DialogTitle, String DialogMsg) {
		mDialogTitle = DialogTitle;
		mDialogMsg = DialogMsg;
	}

}
