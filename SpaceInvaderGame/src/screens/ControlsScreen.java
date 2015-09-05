package screens;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

//==========================================

@SuppressWarnings("serial")
public class ControlsScreen extends JFrame implements WindowListener,
		ActionListener {

	// VARS

	
	public JButton button;
	public ConScreen CScreen;
	public boolean is_backg_done = false;

	// WELCOME SCREEN FRAME
	public ControlsScreen() {
		this("Controls for SPACEINVADER");
		

	}

	public ControlsScreen(String title) {
		super(title);
		getContentPane().setBackground(Color.BLACK);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(game.Setup.width, game.Setup.height);
		setLocationRelativeTo(null);
		CScreen = new ConScreen();
		add(CScreen);
		createGUI();
		setVisible(true);
		addWindowListener(this);
	}
	
	// ---------------------------------------------------


	public void createGUI() {
		
		CScreen.setBackground(Color.BLACK);
		
		CScreen.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		JLabel label = new JLabel("Controls for SPACEINVADER");
		label.setFont(new Font("Berlin Sans FB", Font.BOLD, 50));
		label.setForeground(Color.RED);
		c.gridx = 0;
		c.gridy = 0;
		c.weighty = 0.3;
		CScreen.add(label, c);

		JLabel text = new JLabel(
				"The goal is survive by not letting more than 4 aliens through.");
		text.setForeground(Color.WHITE);
		c.gridx = 0;
		c.gridy = 1;
		c.weighty = 0.06;
		CScreen.add(text, c);

		JLabel text1 = new JLabel(
				"Use A and D to move left or right with your gun.");
		text1.setForeground(Color.WHITE);
		c.gridx = 0;
		c.gridy = 2;
		c.weighty = 0.06;
		CScreen.add(text1, c);

		JLabel text2 = new JLabel("Use the spacebar to shoot.");
		text2.setForeground(Color.WHITE);
		c.gridx = 0;
		c.gridy = 3;
		c.weighty = 0.06;
		CScreen.add(text2, c);

		JLabel text3 = new JLabel(
				"Use your mouse to select and use other weapons when they become available.");
		text3.setForeground(Color.WHITE);
		c.gridx = 0;
		c.gridy = 4;
		c.weighty = 0.06;
		CScreen.add(text3, c);

		JLabel text4 = new JLabel("Good luck!");
		text4.setForeground(Color.WHITE);
		c.gridx = 0;
		c.gridy = 5;
		c.weighty = 0.06;
		CScreen.add(text4, c);

		button = new JButton("Back");
		c.gridx = 0;
		c.gridy = 6;
		c.weighty = 0.4;
		button.addActionListener(this);
		CScreen.add(button, c);
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

//==========================================

@SuppressWarnings("serial")
class ConScreen extends JPanel {
	
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
