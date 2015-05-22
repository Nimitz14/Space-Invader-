package screens;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class ControlsScreen extends JFrame implements WindowListener,
		ActionListener {

	// VARS

	public static ControlsScreen controlsFrame;

	// WELCOME SCREEN FRAME
	public ControlsScreen() {

		controlsFrame = new ControlsScreen("Controls for SPACEINVADER");
		controlsFrame.getContentPane().setBackground(Color.BLACK);
		controlsFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		controlsFrame.setSize(game.Setup.width, game.Setup.height);
		controlsFrame.setLocationRelativeTo(null);
		CScreen CScreen = new CScreen();
		controlsFrame.add(CScreen);
		controlsFrame.setVisible(true);

	}

	public ControlsScreen(String title) {
		super(title);
		addWindowListener(this);
	}

	// WELCOME SCREEN BUTTONS;LABELS;CONTAINER
	public static void addComponentsToPane(Container pane, ControlsScreen frame) {

	}

	// ACTIONLISTENER
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == CScreen.button) {
			WelcomeScreen.createAndShowGUI();
			controlsFrame.setVisible(false);
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

class CScreen extends JPanel implements Runnable {

	public static JButton button;

	public Rectangle[] cstar;
	public int cstars = 100;
	public int ccurrentStarSize;
	public boolean cdefStars;

	public CScreen() {
		this.setBackground(Color.BLACK);
		cdefStars = false;
		run();
	}

	public void run() {
		cdefineStars();
		JLabel label;
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		label = new JLabel("Controls for SPACEINVADER");
		label.setFont(new Font("Berlin Sans FB", Font.BOLD, 50));
		label.setForeground(Color.RED);
		c.gridx = 0;
		c.gridy = 0;
		c.weighty = 0.3;
		this.add(label, c);

		JLabel text = new JLabel(
				"The goal is survive by not letting more than 4 aliens through.");
		text.setForeground(Color.WHITE);
		c.gridx = 0;
		c.gridy = 1;
		c.weighty = 0.06;
		this.add(text, c);

		JLabel text1 = new JLabel(
				"Use A and D to move left or right with your gun.");
		text1.setForeground(Color.WHITE);
		c.gridx = 0;
		c.gridy = 2;
		c.weighty = 0.06;
		this.add(text1, c);

		JLabel text2 = new JLabel("Use the spacebar to shoot.");
		text2.setForeground(Color.WHITE);
		c.gridx = 0;
		c.gridy = 3;
		c.weighty = 0.06;
		this.add(text2, c);

		JLabel text3 = new JLabel(
				"Use your mouse to select and use other weapons when they become available.");
		text3.setForeground(Color.WHITE);
		c.gridx = 0;
		c.gridy = 4;
		c.weighty = 0.06;
		this.add(text3, c);

		JLabel text4 = new JLabel("Good luck!");
		text4.setForeground(Color.WHITE);
		c.gridx = 0;
		c.gridy = 5;
		c.weighty = 0.06;
		this.add(text4, c);

		button = new JButton("Back");
		c.gridx = 0;
		c.gridy = 6;
		c.weighty = 0.4;
		button.addActionListener(ControlsScreen.controlsFrame);
		this.add(button, c);
	}

	// gibt stern array aus

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (cdefStars) {
			g.setColor(Color.WHITE);

			for (int j = 0; j < cstar.length; j++) {
				g.fill3DRect(cstar[j].x, cstar[j].y, cstar[j].width,
						cstar[j].height, true);
			}

		}

	}

	// erstellt zufaellige anordnung von sternen zufaelliger groesse

	public void cdefineStars() {
		cstar = new Rectangle[cstars];

		for (int j = 0; j < cstar.length; j++) {
			ccurrentStarSize = game.Gameprocess.randomWithRange(1, 4);
			cstar[j] = new Rectangle(
					game.Gameprocess.randomWithRange(0, game.Setup.width),
					game.Gameprocess.randomWithRange(0, game.Setup.height),
					ccurrentStarSize, ccurrentStarSize);
		}
		cdefStars = true;
	}
}