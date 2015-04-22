package de.julz.game.view.ui;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;

import de.julz.game.Controller;
import de.julz.game.model.Action;

public class KeyInput extends KeyAdapter {

	private HashMap<Integer,Action> map;
	
	private Action currentAction;
	
	public KeyInput() {
		map = new HashMap<Integer, Action>();
		map.put(KeyEvent.VK_LEFT, Action.LEFT);
		map.put(KeyEvent.VK_RIGHT, Action.RIGHT);
		map.put(KeyEvent.VK_UP, Action.UP);
		map.put(KeyEvent.VK_DOWN, Action.DOWN);
		reset();
	}
	
    @Override
    public void keyReleased(KeyEvent event) {
        int keyCode = event.getKeyCode();
        
        if (keyCode == 82) {
        	Controller.getInstance().resetScore();
        	System.out.println("RESET");
        }
        
        if (map.containsKey(keyCode)) {
        	currentAction = map.get(keyCode);
        }
    }
    
    public void reset() {
    	currentAction = Action.NIL;
    }

	public Action getAction() {
		return currentAction;
	}


 
}