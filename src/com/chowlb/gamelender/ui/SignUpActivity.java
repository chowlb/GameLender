package com.chowlb.gamelender.ui;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.chowlb.gamelender.R;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUpActivity extends Activity {

	protected EditText mUsername;
	protected EditText mPassword;
	protected EditText mPasswordVerify;
	protected EditText mEmail;
	protected Button mSignUpButton;
	protected Button mCancelButton;
	protected TextView mLogo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.activity_sign_up);

		getActionBar().hide();
		
		mUsername = (EditText) findViewById(R.id.usernameField);
		mPassword = (EditText) findViewById(R.id.passwordField);
		mPasswordVerify = (EditText) findViewById(R.id.passwordVerifyField);
		mEmail = (EditText) findViewById(R.id.emailField);
		mSignUpButton = (Button) findViewById(R.id.signupButton);
		mCancelButton = (Button) findViewById(R.id.cancelButton);
		mLogo = (TextView) findViewById(R.id.logoText);
		
		Typeface typeFace = Typeface.createFromAsset(getAssets(), "fonts/eightbit.ttf");
		mLogo.setTypeface(typeFace);
		
		mCancelButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		
		
		mSignUpButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String username = mUsername.getText().toString();
				String password = mPassword.getText().toString();
				String passwordVerify = mPasswordVerify.getText().toString();
				String email = mEmail.getText().toString();
			
				//Trim whitepsaces
				username = username.trim();
				password = password.trim();
				passwordVerify = passwordVerify.trim();
				email = email.trim();
				
				if(username.isEmpty() || password.isEmpty() || passwordVerify.isEmpty() || email.isEmpty()) {
					//one of the fields is empty
					AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
					builder.setMessage(R.string.signup_error_message)
						.setTitle(R.string.signup_error_title)
						.setPositiveButton(android.R.string.ok, null);
					
					AlertDialog dialog = builder.create();
					dialog.show();
					
				}
				else if(!password.equals(passwordVerify)){
					AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
					builder.setMessage(R.string.signup_password_mismatch)
						.setTitle(R.string.signup_error_title)
						.setPositiveButton(android.R.string.ok, null);
					
					AlertDialog dialog = builder.create();
					dialog.show();
				}
				else {
					//create new user!
					setProgressBarIndeterminateVisibility(true);
					ParseUser newuser = new ParseUser();
					newuser.setUsername(username);
					newuser.setPassword(password);
					newuser.setEmail(email);
					
					//Use the background method so it's on a separate thread
					// saves the user in the background and makes a callback when it's done
					newuser.signUpInBackground(new SignUpCallback() {
						
						@Override
						public void done(ParseException e) {
							setProgressBarIndeterminateVisibility(false);
							if(e == null){
								//Success!
								Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
								intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
								intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
								startActivity(intent);
							}
							else {
								//if e is not null then there is an error. 
								
								//parseException allows  e.getMessage() function to return a string message
								AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
								builder.setMessage(e.getMessage())
									.setTitle(R.string.signup_error_title)
									.setPositiveButton(android.R.string.ok, null);
								
								AlertDialog dialog = builder.create();
								dialog.show();
							}
						}
					});
					
				}
				
			}
		});
	}

}
