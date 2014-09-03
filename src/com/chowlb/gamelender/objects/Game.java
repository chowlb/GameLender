package com.chowlb.gamelender.objects;
import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

@ParseClassName("Games")
public class Game extends ParseObject {
	
	public Game(ParseUser user, String title, String platform) {
		setUser(user);
		setTitle(title);
		setPlatform(platform);
		setStatus(0);
	}
	
	public Game(ParseUser user, TempGameObj game) {
		setUser(user);
		setTitle(game.getTitle());
		setPlatform(game.getPlatform());
		setStatus(0);
	}
	
	public ParseUser getUser() {
		return getParseUser("user");
	}

	public void setUser(ParseUser value) {
		put("user", value);
	}

	public String getTitle() {
		return getParseObject("title").toString();
	}
	
	public void setTitle(String value) {
		put("title", value);
	}
	
	public String getPlatform() {
		return getParseObject("platform").toString();
	}
	
	public void setPlatform(String value) {
		put("platform", value);
	}
	
	public void setStatus(int value) {
		put("status", value);
	}
	
	public int getStatus() {
		return getInt("status");
	}
	
	public static ParseQuery<Game> getQuery() {
		return ParseQuery.getQuery(Game.class);
	}
	
	

}

