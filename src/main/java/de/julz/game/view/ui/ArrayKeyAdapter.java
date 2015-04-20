package de.julz.game.view.ui;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;

import de.julz.game.event.ActionEvent;
import de.julz.game.event.EventDispatcher;
import de.julz.game.model.Action;

public class ArrayKeyAdapter extends KeyAdapter {

	private HashMap<Integer,ActionEvent> map = new HashMap<Integer, ActionEvent>();
	
	public ArrayKeyAdapter() {
		map.put(KeyEvent.VK_LEFT, new ActionEvent(Action.LEFT));
		map.put(KeyEvent.VK_RIGHT, new ActionEvent(Action.RIGHT));
		map.put(KeyEvent.VK_UP, new ActionEvent(Action.UP));
		map.put(KeyEvent.VK_DOWN, new ActionEvent(Action.DOWN));
	}
	
    @Override
    public void keyReleased(KeyEvent event) {
        int keyCode = event.getKeyCode();
        
        if (map.containsKey(keyCode)) {
        	ActionEvent a = map.get(keyCode);
        	EventDispatcher.getInstance().notify(a);
        }
    }

 
}