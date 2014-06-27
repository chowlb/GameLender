package com.chowlb.gamelender.ui;

import com.parse.ParseUser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class DispatchActivity extends Activity{
	
	public DispatchActivity(){
		
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		//Check for current user info
		if(ParseUser.getCurrentUser() != null){
			startActivity(new Intent(this,MainActivity.class));
		}
		else{
			startActivity(new Intent(this,LoginActivity.class));
		}
	}

}
