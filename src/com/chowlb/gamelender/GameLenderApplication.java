package com.chowlb.gamelender;

import android.app.Application;

import com.parse.Parse;

public class GameLenderApplication extends Application{

	@Override
	public void onCreate() {
		super.onCreate();
		Parse.initialize(this, "oGbl7N20ABKQJNHWyymU9fAa1lJDPOsSMRim3Rhp", "fuQBW6ex44O2mTOUCkVo47NlB1aDqOW1qfAp8ih8");
        
        
     
    }
}
