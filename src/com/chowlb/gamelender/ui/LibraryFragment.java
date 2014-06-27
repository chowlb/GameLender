package com.chowlb.gamelender.ui;


import java.util.List;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.chowlb.gamelender.R;
import com.chowlb.gamelender.utils.ParseConstants;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

public class LibraryFragment extends ListFragment{
	
	public static final String TAG = LibraryFragment.class.getSimpleName();
	protected List<ParseObject> mGames;
	protected ParseRelation<ParseObject> mGamesRelation;
	protected ParseUser mCurrentUser;
	protected TextView mEditLibrary;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.fragment_library, container, false);
		
		mEditLibrary = (TextView) rootView.findViewById(android.R.id.empty);
		mEditLibrary.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getListView().getContext(), EditLibraryActivity.class);
				startActivity(intent);
			}
		});
		
		return rootView;
	}

	@Override
	public void onResume() {
		super.onResume();
		
		mCurrentUser = ParseUser.getCurrentUser();
		mGamesRelation = mCurrentUser.getRelation(ParseConstants.KEY_GAMES_RELATION);
		
		getActivity().setProgressBarIndeterminateVisibility(true);
		
		ParseQuery<ParseObject> query =  mGamesRelation.getQuery();
	    query.addAscendingOrder(ParseConstants.KEY_USERNAME);
				
		query.findInBackground(new FindCallback<ParseObject>() {

			@Override
			public void done(List<ParseObject> games, ParseException e) {
				getActivity().setProgressBarIndeterminateVisibility(false);
				if(e == null) {
					mGames = games;
					String[] gameNames = new String[mGames.size()];
					int i = 0;
					for(ParseObject game : mGames) {
						gameNames[i] = game.getString("name");
						i++;
					}
					
					ArrayAdapter<String> adapter = new ArrayAdapter<String>(getListView().getContext(), android.R.layout.simple_list_item_1, gameNames);
					setListAdapter(adapter);
				}else {
					Log.e(TAG, e.getMessage());
					AlertDialog.Builder builder = new AlertDialog.Builder(getListView().getContext());
					builder.setMessage(e.getMessage())
						.setTitle(R.string.error_title)
						.setPositiveButton(android.R.string.ok, null);
	
					AlertDialog dialog = builder.create();
					dialog.show();
				}
				
			}
		
		});
		
	}

}
