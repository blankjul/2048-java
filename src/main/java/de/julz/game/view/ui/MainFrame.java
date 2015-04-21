package de.julz.game.view.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import de.julz.game.event.Event;
import de.julz.game.event.EventListener;
import de.julz.game.event.GameOverEvent;
import de.julz.game.event.UpdateEvent;
import de.julz.game.model.Board;

public class MainFrame extends JFrame implements EventListener {

	private static final long serialVersionUID = 1L;

	private InfoPanel info;
	
	private GameFieldPanel game;
	
	public MainFrame(Board board) {
		super();
		
		setSize(540, 720);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("2048");
		setResizable(false);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(new Color(250,250,240));
		
		// adjust the game
		game = new GameFieldPanel(board, 500);
		game.setBounds(20, 200, 500, 500);
        panel.add(game);
		
		info = new InfoPanel();
		info.setBounds(20, 10, 500, 180);
		panel.add(info);
        
        setContentPane(panel);
        setVisible(true);

	}


	public void handle(Event event) {
		if (event instanceof UpdateEvent) {
			UpdateEvent e = (UpdateEvent) event;
			game.update(e.getGame().getBoard());
			info.update(e.getGame().getScore(), 0, e.getGame().isFinished());
			this.repaint();
		} 
	}
	

}
