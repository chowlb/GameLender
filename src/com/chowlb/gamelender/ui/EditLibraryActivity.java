package com.chowlb.gamelender.ui;

import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.chowlb.gamelender.R;
import com.chowlb.gamelender.objects.Game;
import com.chowlb.gamelender.utils.ParseConstants;

public class EditLibraryActivity extends ListActivity {

	protected EditText mLibrarySearch;
	protected ArrayAdapter<String> adapter;
	protected List<Game> mGamesList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		
		setContentView(R.layout.activity_edit_library);

		getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		
		mLibrarySearch = (EditText) findViewById(R.id.librarysearchField);
		mLibrarySearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
		    @Override
		    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
		            Log.e("chowlb", "Search started: " + mLibrarySearch.getText().toString());
		        	performSearch();
		            return true;
		        }
		        return false;
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
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	
	protected void performSearch() {
		String searchText = mLibrarySearch.getText().toString();
		Log.e("chowlb", "PeformSearch called: " + searchText);
		GetGamesListAsync getBlogPostsTask = new GetGamesListAsync();
		getBlogPostsTask.execute(searchText);
	}
	
	public void handleGamesList() {
		
		String[] gameNames = new String[mGamesList.size()];
		int i = 0;
		for(Game game : mGamesList) {
			gameNames[i] = game.getTitle();
			i++;
		}
		adapter = new ArrayAdapter<String>(EditLibraryActivity.this, android.R.layout.simple_list_item_checked, gameNames);
		setListAdapter(adapter);
	}
	
	
	private class GetGamesListAsync extends AsyncTask<String, Void, List<Game>>{

		@Override
		protected List<Game> doInBackground(String... arg0) {
			String xmlSource = ParseConstants.URL_GET_GAME_LIST + arg0[0];
			List<Game> gameList = new ArrayList<Game>();
			
			try {
				URL url = new URL(xmlSource);
				URLConnection con = url.openConnection();
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
				Document doc = builder.parse(con.getInputStream());
				
				NodeList nodes = doc.getElementsByTagName("Game");
	            for (int i = 0; i < nodes.getLength(); i++) {
	            	
	                Element element = (Element) nodes.item(i);
	                NodeList id = element.getElementsByTagName("id");
	                Element elementId = (Element) id.item(0);
	                NodeList title = element.getElementsByTagName("GameTitle");
	                Element elementTitle = (Element) title.item(0);
	                NodeList platform = element.getElementsByTagName("Platform");
	                Element elementPlatform = (Element) platform.item(0);
	                Game game = new Game(Integer.valueOf(elementId.getTextContent()), elementTitle.getTextContent(), elementPlatform.getTextContent());
	                gameList.add(game);
	            }
	            
				
			}catch(Exception e){
				e.printStackTrace();
			}
			
			return gameList;
		}
		
		
		@Override
		protected void onPostExecute(List<Game> result) {
			mGamesList = result;
			handleGamesList();
		}
	}
	

}
