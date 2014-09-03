package com.chowlb.gamelender.ui;


import java.util.List;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.chowlb.gamelender.R;
import com.chowlb.gamelender.utils.ParseConstants;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class InboxFragment extends ListFragment{
	
	protected List<ParseObject> mMessages;
	protected ArrayAdapter<String> adapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_borrowing, container, false);
		
		return rootView;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		Log.e("chowlb", "Calling on Resume in Inbox Fragment");
		
		getActivity().setProgressBarIndeterminateVisibility(true);
		
		retrieveMessages();
		
	}
	
	private void retrieveMessages() {
		ParseQuery<ParseObject> query = ParseQuery.getQuery(ParseConstants.CLASS_MESSAGES);
		query.whereEqualTo(ParseConstants.KEY_RECIPIENT_IDS, ParseUser.getCurrentUser().getObjectId());
		query.addDescendingOrder(ParseConstants.KEY_CREATED_AT);
		query.findInBackground(new FindCallback<ParseObject>() {
			
			@Override
			public void done(List<ParseObject> messages, ParseException e) {
				Log.e("chowlb", "Calling DONE!");
				getActivity().setProgressBarIndeterminateVisibility(false);
				
//				if(mSwipeRefreshLayout.isRefreshing()) {
//					mSwipeRefreshLayout.setRefreshing(false);
//				}
				
				if(e == null) {
					Log.e("chowlb", "Messages is at capacity: " + messages.size());
					//success
					mMessages = messages;
					String[] usernames = new String[mMessages.size()];
					int i = 0;
					for(ParseObject message : mMessages) {
						usernames[i] = message.getString(ParseConstants.KEY_SENDER_NAME) + " - " + message.getCreatedAt().toString();
						i++;
					}
					
					adapter = new ArrayAdapter<String>(
							getListView().getContext(),
							android.R.layout.simple_list_item_1,
							usernames);
					setListAdapter(adapter);
					
//					if(getListView().getAdapter() == null) {
//						MessageAdapter adapter = new MessageAdapter(
//							getListView().getContext(), mMessages);
//						setListAdapter(adapter);
//					}else {
//						//refill adapter
//						((MessageAdapter)getListView().getAdapter()).refill(mMessages);
//					}
				}
				else {
					Log.e("chowlb", e.toString());
				}
			}
		});
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		
		super.onListItemClick(l, v, position, id);
	
		ParseObject message = mMessages.get(position);
		
		String messageString = message.getString(ParseConstants.KEY_SENDER_NAME) + " is requesting: " + message.getString(ParseConstants.KEY_GAME_TITLE) + " - " + message.getString(ParseConstants.KEY_GAME_PLATFORM);
		
		AlertDialog.Builder builder = new AlertDialog.Builder(getListView().getContext());
		builder.setMessage(messageString)
			.setTitle(message.getString(ParseConstants.KEY_SENDER_NAME))
			.setPositiveButton(R.string.accept_button_string, null)
			.setNegativeButton(R.string.decline_button_string, null);

		AlertDialog dialog = builder.create();
		dialog.show();
		
		//Delete the message
		
		
		
		
//		if(ids.size() == 1) {
//			//last recipient.  delete whole list
//			message.deleteInBackground();
//		}
//		else {
//			//remove recipient and save
//			ids.remove(ParseUser.getCurrentUser().getObjectId());
//			ArrayList<String> idsToRemove = new ArrayList<String>();
//			idsToRemove.add(ParseUser.getCurrentUser().getObjectId());
//			
//			message.removeAll(ParseConstants.KEY_RECIPIENT_IDS, idsToRemove);
//			message.saveInBackground();
//		}
		
	}
}
