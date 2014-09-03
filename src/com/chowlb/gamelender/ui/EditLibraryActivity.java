package com.chowlb.gamelender.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.chowlb.gamelender.R;
import com.chowlb.gamelender.objects.TempGameObj;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class EditLibraryActivity extends ListActivity {

	protected EditText mLibrarySearch;
	protected ArrayAdapter<String> adapter;
	protected List<TempGameObj> mGamesList = new ArrayList<TempGameObj>();
	protected List<ParseObject> mParseGameList = new ArrayList<ParseObject>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

		setContentView(R.layout.activity_edit_library);

		getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		
		mLibrarySearch = (EditText) findViewById(R.id.librarysearchField);
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

	}

	@Override
	public void onResume() {
		super.onResume();

		ParseUser mCurrentUser = ParseUser.getCurrentUser();
		setProgressBarIndeterminateVisibility(true);
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Game");
		query.addAscendingOrder("title");
		query.whereEqualTo("owner", mCurrentUser.getObjectId());
		query.findInBackground(new FindCallback<ParseObject>() {

			@Override
			public void done(List<ParseObject> games, ParseException e) {
				setProgressBarIndeterminateVisibility(false);
				if (e == null) {
					String[] gameNames = new String[games.size()];
					int i = 0;
					mParseGameList = games;
					for (ParseObject game : games) {
						// TempGameObj tempGame = new TempGameObj();
						// tempGame.setPlatform(game.getString("platform"));
						// tempGame.setTitle(game.getString("title"));
						// mGamesList.add(tempGame);

						gameNames[i] = game.getString("title") + " - "
								+ game.getString("platform");
						i++;
					}

					Log.e("chowlb", "GameList: " + mParseGameList.size()
							+ " vs. SearchLIst: " + gameNames.length);

					adapter = new ArrayAdapter<String>(
							EditLibraryActivity.this,
							android.R.layout.simple_list_item_checked,
							gameNames);
					setListAdapter(adapter);

					addGameCheckMarks();
				} else {
					Log.e("Chowlb", e.getMessage());
				}

			}

		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_library, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_add_game) {
			Intent intent = new Intent(this, AddGameActivity.class);
			startActivity(intent);
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {

		super.onListItemClick(l, v, position, id);
		if (getListView().isItemChecked(position)) {
			ParseObject game = new ParseObject("Game");
			game = mParseGameList.get(position);
			game.saveInBackground();

		} else {

			// remove
			ParseObject tempGame = mParseGameList.get(position);
			tempGame.deleteInBackground();
			// ParseQuery<ParseObject> query = ParseQuery.getQuery("Game");
			// query.whereEqualTo("owner", ParseUser.getCurrentUser()
			// .getObjectId());
			// query.whereEqualTo("title", tempGame.getTitle());
			// query.whereEqualTo("platform", tempGame.getTitle());
			// query.findInBackground(new FindCallback<ParseObject>() {
			//
			// @Override
			// public void done(List<ParseObject> games, ParseException e) {
			// if (e == null) {
			// for (ParseObject listedGame : games) {
			// listedGame.deleteInBackground();
			// }
			// } else {
			//
			// Log.e("chowlb", e.getMessage());
			//
			// }
			//
			// }
			// });
		}

	}

	private void addGameCheckMarks() {

		ParseQuery<ParseObject> query = ParseQuery.getQuery("Game");
		query.whereEqualTo("owner", ParseUser.getCurrentUser().getObjectId());
		query.findInBackground(new FindCallback<ParseObject>() {

			@Override
			public void done(List<ParseObject> games, ParseException e) {
				if (e == null) {
					// list returned - look for match
					Log.e("chowlb", "GameList: " + mParseGameList.size()
							+ " vs. SearchLIst: " + games.size());
					for (int i = 0; i < mParseGameList.size(); i++) {

						getListView().setItemChecked(i, true);

					}
				} else {
					Log.e("chowlb", e.getMessage());
				}

			}

		});

	}

}
