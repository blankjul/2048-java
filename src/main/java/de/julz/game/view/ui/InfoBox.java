package de.julz.game.view.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class InfoBox extends JPanel {

	private static final long serialVersionUID = 909465498151126382L;

	private int width;
	private int height;

	private JLabel lblTitle;
	private JLabel lblValue;

	public InfoBox(int width, int height, String title, String value) {

		this.width = width;
		this.height = height;

		setSize(width, height);

		setOpaque(true);
		setBackground(new Color(187, 173, 160));
		GridBagLayout gbl = new GridBagLayout();
		setLayout(gbl);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(2, 2, 2, 2);

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridheight = 1;
		lblTitle = new JLabel(title);
		lblTitle.setForeground(Color.WHITE);
		lblTitle.setFont(new Font("default", Font.BOLD, 14));
		lblTitle.setHorizontalAlignment(JLabel.CENTER);
		lblTitle.setForeground(new Color(236, 228, 218));
		gbl.setConstraints(lblTitle, gbc);
		add(lblTitle);

		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridheight = 2;
		lblValue = new JLabel(value);
		lblValue.setForeground(Color.WHITE);
		lblValue.setFont(new Font("default", Font.BOLD, 20));
		lblValue.setHorizontalAlignment(JLabel.CENTER);
		gbl.setConstraints(lblValue, gbc);
		add(lblValue);

	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public String getTitle() {
		return lblTitle.getText();
	}

	public void setTitle(String title) {
		lblTitle.setText(title);
	}

	public String getValue() {
		return lblValue.getText();
	}

	public void setValue(String value) {
		lblValue.setText(value);
	}

}
