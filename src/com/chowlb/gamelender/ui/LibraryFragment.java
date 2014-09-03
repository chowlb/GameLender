package com.chowlb.gamelender.ui;


import java.util.List;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.chowlb.gamelender.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class LibraryFragment extends ListFragment{
	
	public static final String TAG = LibraryFragment.class.getSimpleName();
	protected List<ParseObject> mGames;
	protected ParseUser mCurrentUser;
	protected TextView mEditLibrary;
	protected EditText mLibrarySearch;
	protected ArrayAdapter<String> adapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.fragment_library, container, false);
		
		mEditLibrary = (TextView) rootView.findViewById(android.R.id.empty);
		mEditLibrary.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getListView().getContext(), AddGameActivity.class);
				startActivity(intent);
			}
		});
		
		mLibrarySearch = (EditText) rootView.findViewById(R.id.librarysearchField);
		mLibrarySearch.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				adapter.getFilter().filter(s.toString());
			}
		});
		
		return rootView;
	}

	@Override
	public void onResume() {
		super.onResume();
		
		mCurrentUser = ParseUser.getCurrentUser();
		
		
		getActivity().setProgressBarIndeterminateVisibility(true);
		
		ParseQuery<ParseObject> query =  ParseQuery.getQuery("Game");
	    query.addAscendingOrder("title");
	    query.whereEqualTo("owner",  mCurrentUser.getObjectId());
	    
		query.findInBackground(new FindCallback<ParseObject>() {

			@Override
			public void done(List<ParseObject> games, ParseException e) {
				getActivity().setProgressBarIndeterminateVisibility(false);
				if(e == null) {
					mGames = games;
					String[] gameNames = new String[mGames.size()];
					int i = 0;
					for(ParseObject game : mGames) {
						String gameNameString = game.getString("title") + " - " + game.getString("platform");
						if(!game.getString("status").equals("0"))
						{
							gameNameString = gameNameString + " to: " + game.getString("status"); 
						}
						gameNames[i] = gameNameString;
						i++;
					}
					
					adapter = new ArrayAdapter<String>(getListView().getContext(), android.R.layout.simple_list_item_1, gameNames);
					setListAdapter(adapter);
					setRowColors();
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
	
	private void setRowColors() {

		ParseQuery<ParseObject> query = ParseQuery.getQuery("Game");
		query.whereEqualTo("owner", ParseUser.getCurrentUser().getObjectId());
		query.findInBackground(new FindCallback<ParseObject>() {

			@Override
			public void done(List<ParseObject> games, ParseException e) {
				if (e == null) {
					// list returned - look for match
					
					for (int i = 0; i < mGames.size(); i++) {
						ParseObject game = new ParseObject("Game");
						game = mGames.get(i);
						if(!game.getString("status").equals("0"))
						{
							getListView().getChildAt(i).setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
						}

					}
				} else {
					Log.e("chowlb", e.getMessage());
				}

			}

		});

	}

}
