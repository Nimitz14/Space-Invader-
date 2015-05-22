package screens;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.*;

public class AboutScreen extends JFrame implements WindowListener,
		ActionListener {

	// VARS

	public static AboutScreen aboutFrame;

	// WELCOME SCREEN FRAME
	public AboutScreen() {

		aboutFrame = new AboutScreen("About SPACEINVADER");
		aboutFrame.getContentPane().setBackground(Color.BLACK);
		aboutFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		aboutFrame.setSize(game.Setup.width, game.Setup.height);
		aboutFrame.setLocationRelativeTo(null);
		AScreen AScreen = new AScreen();
		aboutFrame.add(AScreen);
		aboutFrame.setVisible(true);

	}

	public AboutScreen(String title) {
		super(title);
		addWindowListener(this);
	}

	// ACTIONLISTENER
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == AScreen.button) {
			WelcomeScreen.createAndShowGUI();
			aboutFrame.setVisible(false);
		}

	}

	// MISC
	public void windowClosing(WindowEvent e) {
		dispose();
		System.exit(0);
	}

	public void windowOpened(WindowEvent e) {
	}

	public void windowActivated(WindowEvent e) {
	}

	public void windowIconified(WindowEvent e) {
	}

	public void windowDeiconified(WindowEvent e) {
	}

	public void windowDeactivated(WindowEvent e) {
	}

	public void windowClosed(WindowEvent e) {
	}

}

// USER INTERFACE

class AScreen extends JPanel implements Runnable {

	public static JButton button;

	public Rectangle[] astar;
	public int astars = 100;
	public int acurrentStarSize;
	public boolean adefStars;

	public AScreen() {
		this.setBackground(Color.BLACK);
		adefStars = false;
		run();
	}

	public void run() {
		adefineStars();

		JLabel label;
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		label = new JLabel("About SPACEINVADER");
		label.setFont(new Font("Berlin Sans FB", Font.BOLD, 50));
		label.setForeground(Color.RED);
		c.gridx = 0;
		c.gridy = 0;
		c.weighty = 0.45;
		this.add(label, c);

		JLabel disclaimer = new JLabel(
				"Created in 2015 by R.A. Braun, all rights reserved.");
		disclaimer.setForeground(Color.WHITE);
		c.gridx = 0;
		c.gridy = 1;
		c.weighty = 0.05;
		this.add(disclaimer, c);

		JLabel email = new JLabel("rab014@googlemail.com");
		email.setForeground(Color.WHITE);
		c.gridx = 0;
		c.gridy = 2;
		c.weighty = 0.05;

		this.add(email, c);

		button = new JButton("Back");
		c.gridx = 0;
		c.gridy = 3;
		c.weighty = 0.45;
		button.addActionListener(AboutScreen.aboutFrame);
		this.add(button, c);

	}

	// gibt stern array aus

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (adefStars) {
			g.setColor(Color.WHITE);

			for (int j = 0; j < astar.length; j++) {
				g.fill3DRect(astar[j].x, astar[j].y, astar[j].width,
						astar[j].height, true);
			}

		}

	}

	// erstellt zufaellige anordnung von sternen zufaelliger groesse

	public void adefineStars() {
		astar = new Rectangle[astars];

		for (int j = 0; j < astar.length; j++) {
			acurrentStarSize = game.Gameprocess.randomWithRange(1, 4);
			astar[j] = new Rectangle(
					game.Gameprocess.randomWithRange(0, game.Setup.width),
					game.Gameprocess.randomWithRange(0, game.Setup.height),
					acurrentStarSize, acurrentStarSize);
		}
		adefStars = true;
	}

}