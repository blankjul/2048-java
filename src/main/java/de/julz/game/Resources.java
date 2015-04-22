package de.julz.game;

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class Resources {

	
	private Preferences prefs = null;
	
	private int score;
	
	
	public Resources() {
		prefs = Preferences.userRoot().node("/de/julz/game");
	}

	public int getScore() {
		return score;
	}
	
	public void setScore(int value){
		this.score = value;
	}
	
	
	public void serialize() {
		Preferences prefs = Preferences.userRoot().node("/de/julz/game");
		prefs.put("score", String.valueOf(score));
		try {
			prefs.flush();
		} catch (BackingStoreException e) {
			e.printStackTrace();
		}
	}
	
	public void load() {
		score = Integer.valueOf(prefs.get("score", "0"));
	}
	
	
	
	

}
