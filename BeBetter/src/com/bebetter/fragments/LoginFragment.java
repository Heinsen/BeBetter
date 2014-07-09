package com.bebetter.fragments;

import org.w3c.dom.Notation;

import com.bebetter.R;
import com.bebetter.fragments.AlertDialogFragment.AlertDialogFragmentListener;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class LoginFragment extends Fragment{
	
	EditText mUsernameEditText;
	EditText mPasswordEditText;
	ProgressBar mProgressBar;
	LoginFragmentListener mCallback;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		View loginFragmentView = inflater.inflate(R.layout.fragment_login, container, false);
        
		mUsernameEditText = (EditText) loginFragmentView.findViewById(R.id.fragment_login_edit_text_usernamer);
		mPasswordEditText = (EditText) loginFragmentView.findViewById(R.id.fragment_login_edit_text_password);
		mPasswordEditText.setTransformationMethod(new PasswordTransformationMethod());
		Button LoginBtn = (Button) loginFragmentView.findViewById(R.id.fragment_login_btn_login);
		mProgressBar = (ProgressBar) loginFragmentView.findViewById(R.id.fragment_login_progress_bar_waiting);

	    LoginBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				String Username = mUsernameEditText.getText().toString();
				String Password = mPasswordEditText.getText().toString();
				
				mProgressBar.setVisibility(View.VISIBLE);
				mCallback.OnLoginBtnClick(Username, Password);
				
			}
		});
		
	    return loginFragmentView;
	}
		
	public void onDialogDismiss(){
		mProgressBar.setVisibility(View.INVISIBLE);
		mUsernameEditText.setText("");
		mUsernameEditText.setText("");	
	}
	

	
	// Container Activity must implement this interface for callback
	public interface LoginFragmentListener{
		public void OnLoginBtnClick(String Username, String Password);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		// This makes sure that the container activity has implemented
		// the callback interface. If not, it throws an exception
		try {
			mCallback = (LoginFragmentListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnHeadlineSelectedListener");
		}
	}

}
