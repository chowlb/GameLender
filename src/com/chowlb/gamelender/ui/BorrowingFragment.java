package com.chowlb.gamelender.ui;


import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chowlb.gamelender.R;

public class BorrowingFragment extends ListFragment{
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_borrowing, container, false);
		
		return rootView;
	}
}
