package de.julz.game.view.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JPanel;

import de.julz.game.model.Board;
import de.julz.game.view.ui.components.Field;
import de.julz.game.view.ui.components.FieldFactory;

public class GameFieldPanel extends JPanel {


	private static final long serialVersionUID = 1L;

	private Board board;

	final private Color BACKGROUND_COLOR = new Color(187, 173, 160);
	
	final static protected int FIELD_DISTANCE = 15;

	protected int fieldSize = -1;
	
	protected int size = -1;
	
	
	public GameFieldPanel(Board board, int size) {
		this.board = board;
		
		this.size = size;
		setSize(new Dimension(size,size));
		fieldSize = (int) (size - 5 * FIELD_DISTANCE) / 4;
		
		setVisible(true);
	}


	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		

		// paint the background
		RoundRectangle2D rect = new RoundRectangle2D.Double(0, 0, size,size, 10, 10);
		g2d.setColor(BACKGROUND_COLOR);
		g2d.fill(rect);
		
		// paint the fields
		int y = FIELD_DISTANCE;
		for (int j = 0; j < Board.FIELD_SIZE; j++) {
			int x = FIELD_DISTANCE;
			for (int i = 0; i < Board.FIELD_SIZE; i++) {
				Field field = FieldFactory.getInstance().getField(board.get(j, i));
				field.paint(g2d, x, y);
				x += FIELD_DISTANCE + fieldSize;
			}
			y += FIELD_DISTANCE + fieldSize;
		}

	}
	

	public void update(Board board) {
		this.board = board;
	}
	

}
