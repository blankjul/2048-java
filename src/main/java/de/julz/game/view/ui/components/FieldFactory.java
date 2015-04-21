package de.julz.game.view.ui.components;

import java.awt.Color;
import java.util.HashMap;

public class FieldFactory {
	
	private static final FieldFactory singleton = new FieldFactory(); 
	
	private final HashMap<Integer,Field> m = new HashMap<Integer,Field>();
	
	private final Color FONT_COLOR_DARK = new Color(119, 110, 101);
	private final Color FONT_COLOR_LITE = new Color(249, 246, 242);
	
	private FieldFactory() { 
		m.put(null, new Field(null, Color.WHITE, FONT_COLOR_DARK));
		m.put(0, new Field(null, new Color(204, 192, 179), FONT_COLOR_DARK));
		m.put(1, new Field("2", new Color(238, 228, 218), FONT_COLOR_DARK));
		m.put(2, new Field("4", new Color(237, 224, 200), FONT_COLOR_DARK));
		m.put(3, new Field("8", new Color(242, 177, 121), FONT_COLOR_DARK));
		m.put(4, new Field("16", new Color(245, 149, 99), FONT_COLOR_LITE));
		m.put(5, new Field("32", new Color(246, 124, 95), FONT_COLOR_LITE));
		m.put(6, new Field("64", new Color(246, 94, 59), FONT_COLOR_LITE));
		m.put(7, new Field("128", new Color(237, 207, 114), FONT_COLOR_LITE));
		m.put(8, new Field("256", new Color(237, 204, 97), FONT_COLOR_LITE));
		m.put(9, new Field("512", new Color(237, 200, 80), FONT_COLOR_LITE));
		m.put(10, new Field("1024", new Color(237, 197, 63), FONT_COLOR_LITE));
		m.put(11, new Field("2048", new Color(237, 194, 46), FONT_COLOR_LITE));
		m.put(12, new Field("4096", Color.BLACK, FONT_COLOR_LITE));
		m.put(13, new Field("8192", Color.BLACK, FONT_COLOR_LITE));
		m.put(14, new Field("16384", Color.BLACK, FONT_COLOR_LITE));
		m.put(15, new Field("33768", Color.BLACK, FONT_COLOR_LITE));
		m.put(16, new Field("65536", Color.BLACK, FONT_COLOR_LITE));
		m.put(17, new Field("131072", Color.BLACK, FONT_COLOR_LITE));

    } 
         
    public static FieldFactory getInstance() { 
      return singleton; 
    } 
    
    public Field getField(int value) {
    	if (m.containsKey(value)) return m.get(value);
    	else return m.get(null);
    }
	
    
    

}
