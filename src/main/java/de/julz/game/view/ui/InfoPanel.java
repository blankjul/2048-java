package de.julz.game.view.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import de.julz.game.event.EventDispatcher;
import de.julz.game.event.NewGameEvent;
import de.julz.game.view.ui.components.InfoBox;

public class InfoPanel extends JPanel {

	private static final long serialVersionUID = 3700912712497117182L;

	private InfoBox infoScore;
	
	private InfoBox infoBest;
	
	public InfoPanel() {
		setSize(new Dimension(200,200));
		setLayout(null);
		setOpaque(false);
		
		JLabel title = new JLabel("2048");
		title.setBounds(0,0,200,100);
		title.setForeground(new Color(120,110,100));
		title.setFont(new Font("default", Font.BOLD, 40));
		add(title);
		
		JLabel subtitle = new JLabel("<html>Join the numbers and get to the <b>2048 tile!</b></html>");
		subtitle.setBounds(0,100,400,100);
		subtitle.setForeground(new Color(120,110,100));
		subtitle.setFont(new Font("default", Font.PLAIN, 16));
		add(subtitle);
		
		infoScore = new InfoBox(110,70, "SCORE", String.valueOf(0));
		infoScore.setBounds(250,20,infoScore.getWidth(),infoScore.getHeight());
		infoScore.setFont(new Font("default", Font.PLAIN, 16));
		add(infoScore);
		
		infoBest = new InfoBox(110,70, "BEST", String.valueOf(0));
		infoBest.setBounds(380,20,infoScore.getWidth(),infoScore.getHeight());
		infoBest.setFont(new Font("default", Font.PLAIN, 16));
		add(infoBest);
		
		JButton btn = new JButton("New Game");
		btn.setBounds(360,130,130,40);
		btn.setFont(new Font("default", Font.BOLD, 14));
		btn.setBackground(new Color(143,122,102));
		btn.setForeground(new Color(250,250,250));
		btn.setFocusPainted(false);
		btn.setFocusable(false);
		Border emptyBorder = BorderFactory.createEmptyBorder();
		btn.setBorder(emptyBorder);
		btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
            	EventDispatcher.getInstance().notify(new NewGameEvent());
            }
        }); 
		add(btn);
		
		setVisible(true);
	}
	
	
	public void update(int score, int best) {
		infoScore.setValue(String.valueOf(score));
		infoBest.setValue(String.valueOf(best));
	}

}
