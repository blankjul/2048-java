package de.julz.game.view.ui;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import de.julz.game.model.Board;
import de.julz.game.view.View;

public class MainFrame extends JFrame implements View {

	private static final long serialVersionUID = 1L;


	public MainFrame(Board board) {
		super();
		
		addKeyListener(new ArrayKeyAdapter());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("2048");
        setResizable(false);
		setSize(400, 400);
        
		setLayout(new BorderLayout(50,50));
        
        this.getContentPane().add(new InfoPanel());
        this.getContentPane().add(new GameFieldPanel(board, 400));
        
        setVisible(true);

	}


	

	public void update(Board board) {
		this.repaint();
	}
	

}
