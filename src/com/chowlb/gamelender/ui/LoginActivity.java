package com.chowlb.gamelender.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chowlb.gamelender.R;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends Activity {

	protected TextView mSignUpTextView;
	protected TextView mForgotTextView;
	protected EditText mUsername;
	protected EditText mPassword;
	protected Button mLoginButton;
	protected TextView mLogo;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		
		setContentView(R.layout.activity_login);

		Log.e("chowlb", "Starting LOGIN Activity");
		
		getActionBar().hide();
		
		mPassword = (EditText) findViewById(R.id.passwordField);
		mUsername = (EditText) findViewById(R.id.usernameField);
		mLoginButton = (Button) findViewById(R.id.loginButton);
		mLogo = (TextView) findViewById(R.id.logoText);
		Typeface typeFace = Typeface.createFromAsset(getAssets(), "fonts/eightbit.ttf");
		
		mLogo.setTypeface(typeFace);
		
		
		mLoginButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String username = mUsername.getText().toString();
				String password = mPassword.getText().toString();
				
			
				//Trim whitepsaces
				username = username.trim();
				password = password.trim();
				
				if(username.isEmpty() || password.isEmpty()) {
					//one of the fields is empty
					AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
					builder.setMessage(R.string.login_error_message)
						.setTitle(R.string.login_error_title)
						.setPositiveButton(android.R.string.ok, null);
					
					AlertDialog dialog = builder.create();
					dialog.show();
					
				}else {
					//Login User!
					setProgressBarIndeterminateVisibility(true);
					ParseUser.logInInBackground(username, password, new LogInCallback() {
						@Override
						public void done(ParseUser user, ParseException e) {
							setProgressBarIndeterminateVisibility(false);
							if (e == null) {
							       // Hooray! The user is logged in.
									Intent intent = new Intent(LoginActivity.this, MainActivity.class);
									intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
									intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
									startActivity(intent);
							    } else {
							    	AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
									builder.setMessage(e.getMessage())
										.setTitle(R.string.login_error_title)
										.setPositiveButton(android.R.string.ok, null);
									
									AlertDialog dialog = builder.create();
									dialog.show();
							    }
						}
					});
				}
				
			}
		});
		
		
		mSignUpTextView = (TextView) findViewById(R.id.signupText);
		mSignUpTextView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
				startActivity(intent);
			}
		});
		
		mForgotTextView = (TextView) findViewById(R.id.forgotPasswordText);
		mForgotTextView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(LoginActivity.this, R.string.forgotlogin_toast_message, Toast.LENGTH_LONG).show();
			}
		});
	}
	
}
