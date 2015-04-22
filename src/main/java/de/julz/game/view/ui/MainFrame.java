package de.julz.game.view.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import de.julz.game.event.CloseEvent;
import de.julz.game.event.Event;
import de.julz.game.event.EventDispatcher;
import de.julz.game.event.EventListener;
import de.julz.game.event.UpdateEvent;
import de.julz.game.model.Board;

public class MainFrame extends JFrame implements EventListener {

	private static final long serialVersionUID = 1L;

	private InfoPanel info;
	
	private GameFieldPanel game;
	
	private JPanel panel;

    
	
	public MainFrame(Board board) {
		super();
		
		setSize(540, 720);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		setTitle("2048");
		setResizable(false);
		
		this.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
            	EventDispatcher.getInstance().notify(new CloseEvent());
            }
        });
		
		panel = new JPanel();
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
			info.update(e.getGame().getScore(), e.getBestScore(), !e.getGame().hastNextState());
			repaint();
			waitForPaint();
		} 
	}
	
	// this is a hack method to wait for the UI
	private void waitForPaint() {
		final Runnable updateEvent = new Runnable() {
			public void run() {
			}
		};
		try {
			SwingUtilities.invokeAndWait(updateEvent);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	

}
