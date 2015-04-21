package de.julz.game.view.ui.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.RoundRectangle2D;

public class Field {
	
	final private String name;
	final private Color background;
	final private Color font;
	
	public Field(String name, Color background, Color font) {
		this.name = name;
		this.background = background;
		this.font = font;
	}
	
	public void paint(Graphics g, int x, int y) {
		Graphics2D g2d = (Graphics2D) g;
		
		RoundRectangle2D rect = new RoundRectangle2D.Double(x, y, Geometrics.FIELD_SIZE, Geometrics.FIELD_SIZE, 5, 5);
		g2d.setColor(background);
		g2d.fill(rect);
		
		if (name != null) {
			g.setFont(new Font("default", Font.BOLD, 26));
			g.setColor(font);
			FontMetrics fm = g.getFontMetrics();
			int fontX = x + Geometrics.FIELD_SIZE/2 - fm.stringWidth(name) / 2;
			int fontY = y + Geometrics.FIELD_SIZE/2 + 10; 
			g.drawString(name, fontX, fontY);
		}
	}
	
}
