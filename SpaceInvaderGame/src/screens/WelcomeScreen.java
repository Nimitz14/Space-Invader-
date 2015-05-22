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

//STARTFENSTER

public class WelcomeScreen extends JFrame implements WindowListener,
		ActionListener {

	public static WelcomeScreen frame;

	public static void main(String[] args) {
		createAndShowGUI();
	}

	// konstrukteur von startfenster
	public static void createAndShowGUI() {
		frame = new WelcomeScreen("Welcome to SPACEINVADER");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(game.Setup.width, game.Setup.height);
		frame.setLocationRelativeTo(null);
		WScreen wscreen = new WScreen();
		frame.add(wscreen);
		frame.setVisible(true);
	}

	public WelcomeScreen(String title) {
		super(title);
		addWindowListener(this);
	}

	// ACTIONLISTENER
	// wechselt zu einem anderem fenster

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == WScreen.button1) {
			game.Setup setup = new game.Setup();
			frame.setVisible(false);
		}
		if (e.getSource() == WScreen.button2) {
			ControlsScreen controlscreen = new ControlsScreen();
			frame.setVisible(false);
		}
		if (e.getSource() == WScreen.button3) {
			AboutScreen aboutscreen = new AboutScreen();
			frame.setVisible(false);
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

class WScreen extends JPanel implements Runnable {

	public static JButton button1;
	public static JButton button2;
	public static JButton button3;

	public Rectangle[] wstar;
	public int wstars = 100;
	public int wcurrentStarSize;
	public boolean defStars;

	public WScreen() {
		this.setBackground(Color.BLACK);
		defStars = false;
		run();
	}

	public void run() {
		defineStars();
		JLabel label;
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		label = new JLabel("SPACE INVADER");
		label.setFont(new Font("Jokerman", Font.BOLD, 60));
		label.setForeground(Color.RED);
		c.gridx = 0;
		c.gridy = 0;
		c.weighty = 0.1;
		this.add(label, c);

		button1 = new JButton("Play");
		c.gridx = 0;
		c.gridy = 1;
		c.weighty = 0.2;
		button1.addActionListener(WelcomeScreen.frame);
		this.add(button1, c);

		button2 = new JButton("Controls");
		c.gridx = 0;
		c.gridy = 2;
		c.weighty = 0.2;
		this.add(button2, c);
		button2.addActionListener(WelcomeScreen.frame);

		button3 = new JButton("About");
		c.gridx = 0;
		c.gridy = 3;
		c.weighty = 0.2;
		button3.addActionListener(WelcomeScreen.frame);
		this.add(button3, c);

	}

	// gibt stern array aus

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (defStars) {
			g.setColor(Color.WHITE);

			for (int j = 0; j < wstar.length; j++) {
				g.fill3DRect(wstar[j].x, wstar[j].y, wstar[j].width,
						wstar[j].height, true);
			}

		}

	}

	// erstellt zufaellige anordnung von sternen zufaelliger groesse

	public void defineStars() {
		wstar = new Rectangle[wstars];

		for (int j = 0; j < wstar.length; j++) {
			wcurrentStarSize = game.Gameprocess.randomWithRange(1, 4);
			wstar[j] = new Rectangle(
					game.Gameprocess.randomWithRange(0, game.Setup.width),
					game.Gameprocess.randomWithRange(0, game.Setup.height),
					wcurrentStarSize, wcurrentStarSize);
		}
		defStars = true;
	}

}
