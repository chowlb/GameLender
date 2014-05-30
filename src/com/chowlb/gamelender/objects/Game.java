package com.chowlb.gamelender.objects;

public class Game {
	protected int id;
	protected String title;
	protected String platform;
	
	public Game(int id, String title, String platform) {
		this.id = id;
		this.title = title;
		this.platform = platform;
	}
	
	public Game() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}
	
	
}
