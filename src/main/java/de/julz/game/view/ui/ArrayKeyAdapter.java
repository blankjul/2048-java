package de.julz.game.view.ui;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.julz.game.controller.Controller;
import de.julz.game.event.ActionEvent;
import de.julz.game.model.Action;

public class ArrayKeyAdapter extends KeyAdapter {

	private final static Logger LOGGER = Logger.getLogger(ArrayKeyAdapter.class.getName()); 
	
	private HashMap<Integer,ActionEvent> map = new HashMap<Integer, ActionEvent>();
	
	public ArrayKeyAdapter() {
		map.put(KeyEvent.VK_LEFT, new ActionEvent(Action.LEFT));
		map.put(KeyEvent.VK_RIGHT, new ActionEvent(Action.RIGHT));
		map.put(KeyEvent.VK_UP, new ActionEvent(Action.UP));
		map.put(KeyEvent.VK_DOWN, new ActionEvent(Action.DOWN));
	}
	
    @Override
    public void keyReleased(KeyEvent event) {
    	Controller controller = Controller.getInstance();
        int keyCode = event.getKeyCode();
        LOGGER.log(Level.INFO, "Get input code: " + keyCode);
        
        if (map.containsKey(keyCode)) {
        	ActionEvent a = map.get(keyCode);
        	LOGGER.log(Level.INFO, "Notify " + a.getAction());
        	controller.notify(a);
        }
    }

 
}