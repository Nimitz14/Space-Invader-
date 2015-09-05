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

// ==========================================

@SuppressWarnings("serial")
public class AboutScreen extends JFrame implements WindowListener,
		ActionListener {

	// VARS
	public AbScreen AScreen;
	public JButton button;
	public boolean is_backg_done = false;
	
	public AboutScreen() {
		this("About SPACEINVADER");
		
	}

	public AboutScreen(String title) {
		super(title);
		getContentPane().setBackground(Color.BLACK);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(game.Setup.width, game.Setup.height);
		setLocationRelativeTo(null);
		AScreen = new AbScreen();
		add(AScreen);
		createGUI();
		setVisible(true);
		addWindowListener(this);
	}

	// ---------------------------------------------------
	
	// ACTIONLISTENER
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == button) {
			WelcomeScreen.welScreen.setVisible(true);
			setVisible(false);
		}

	}
	
	// ---------------------------------------------------

	public void createGUI() {
		
		AScreen.setBackground(Color.BLACK);

		JLabel label;
		AScreen.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		label = new JLabel("About SPACEINVADER");
		label.setFont(new Font("Berlin Sans FB", Font.BOLD, 50));
		label.setForeground(Color.RED);
		c.gridx = 0;
		c.gridy = 0;
		c.weighty = 0.45;
		AScreen.add(label, c);

		JLabel disclaimer = new JLabel(
				"Created in 2015 by R.A. Braun, all rights reserved.");
		disclaimer.setForeground(Color.WHITE);
		c.gridx = 0;
		c.gridy = 1;
		c.weighty = 0.05;
		AScreen.add(disclaimer, c);

		JLabel email = new JLabel("rab014@googlemail.com");
		email.setForeground(Color.WHITE);
		c.gridx = 0;
		c.gridy = 2;
		c.weighty = 0.05;

		AScreen.add(email, c);

		button = new JButton("Back");
		c.gridx = 0;
		c.gridy = 3;
		c.weighty = 0.45;
		button.addActionListener(this);
		AScreen.add(button, c);

	}
		
	// ---------------------------------------------------
	
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

// =======================================================

@SuppressWarnings("serial")
class AbScreen extends JPanel {
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Rectangle[] v_star_m = screens.Util.create_stars(100);
		
		g.setColor(Color.WHITE);

		for (int j = 0; j < v_star_m.length; j++) {
			g.fill3DRect(v_star_m[j].x, v_star_m[j].y, v_star_m[j].width,
					v_star_m[j].height, true);
		}		

	}
	
	
}
