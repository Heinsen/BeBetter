package com.bebetter.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.bebetter.R;
import com.bebetter.fragments.AlertDialogFragment;
import com.bebetter.fragments.LoginFragment;
import com.bebetter.fragments.AlertDialogFragment.AlertDialogFragmentListener;
import com.bebetter.fragments.LoginFragment.LoginFragmentListener;

public class LoginActivity extends FragmentActivity implements LoginFragmentListener, AlertDialogFragmentListener {

	private LoginFragment mLoginFragment;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login); 
        
        mLoginFragment = (LoginFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_login);
	}
	
	
	@Override
	public void OnLoginBtnClick(String Username, String Password) {
		if(Username.isEmpty() || Password.isEmpty())
			InstantiateAlertDialog(R.string.fragment_login_alert_dialog_title, R.string.fragment_login_alert_dialog_error_msg);
		else
			loginExecuted("");
			//TODO ASYNC login
	}
	
	public void InstantiateAlertDialog(int dialogTitle, int dialogMsg){
		//Instantiate an AlertDialog.Builder with its constructor
		AlertDialogFragment alertDialogBuilder = new AlertDialogFragment();
		//Setter methods to set the dialog characteristics
		alertDialogBuilder.setDialogText(getResources().getString(dialogTitle), getResources().getString(dialogMsg));
		//Showing dialog
		alertDialogBuilder.show(getSupportFragmentManager(), "AlertDialogFragment");
	}
		
	private void loginExecuted(String result){
		if(result == "fail"){  //TODO Handle the result from async login
			InstantiateAlertDialog(R.string.fragment_login_alert_dialog_title, R.string.fragment_login_alert_dialog_error_msg);
		}
		else{
			Intent ActionBarTabsPagerActivity = new Intent(this, ActionBarTabsPagerActivity.class);
			startActivity(ActionBarTabsPagerActivity);
		}
	}
	
	@Override
	public void onDialogDismiss() {	
		mLoginFragment.onDialogDismiss();
	}
}
