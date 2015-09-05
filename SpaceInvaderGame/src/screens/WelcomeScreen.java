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

//==========================================

//STARTFENSTER
@SuppressWarnings("serial")
public class WelcomeScreen extends JFrame implements WindowListener,
		ActionListener {

	public static WelcomeScreen welScreen;
	public AboutScreen A_Screen;
	public ControlsScreen C_Screen;
	public WelScreen W_Screen;
	public JButton button1;
	public JButton button2;
	public JButton button3;
	
	
	public static void main(String[] args) {
		welScreen = new WelcomeScreen();
	}

	// konstrukteur von startfenster
	public WelcomeScreen() {
		this("Welcome to SPACEINVADER");
		
	}

	public WelcomeScreen(String title) {
		super(title);
		getContentPane().setBackground(Color.BLACK);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(game.Setup.width, game.Setup.height);
		setLocationRelativeTo(null);
		W_Screen = new WelScreen();
		add(W_Screen);
		createGUI();
		setVisible(true);
		addWindowListener(this);
		A_Screen = new AboutScreen();
		A_Screen.setVisible(false);
		C_Screen = new ControlsScreen();
		C_Screen.setVisible(false);
		
	}
	
	// ---------------------------------------------------

	// ACTIONLISTENER
	// wechselt zu einem anderem fenster

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == button1) {
			game.Setup setup = new game.Setup();
			setVisible(false);
		}
		if (e.getSource() == button2) {
			C_Screen.setVisible(true);
			setVisible(false);
		}
		if (e.getSource() == button3) {
			A_Screen.setVisible(true);
			setVisible(false);
		}

	}
	
	// ---------------------------------------------------
	
	public void createGUI() {
		
		W_Screen.setBackground(Color.BLACK);
		
		JLabel label;
		W_Screen.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		label = new JLabel("SPACE INVADER");
		label.setFont(new Font("Jokerman", Font.BOLD, 60));
		label.setForeground(Color.RED);
		c.gridx = 0;
		c.gridy = 0;
		c.weighty = 0.1;
		W_Screen.add(label, c);

		button1 = new JButton("Play");
		c.gridx = 0;
		c.gridy = 1;
		c.weighty = 0.2;
		button1.addActionListener(this);
		W_Screen.add(button1, c);

		button2 = new JButton("Controls");
		c.gridx = 0;
		c.gridy = 2;
		c.weighty = 0.2;
		W_Screen.add(button2, c);
		button2.addActionListener(this);

		button3 = new JButton("About");
		c.gridx = 0;
		c.gridy = 3;
		c.weighty = 0.2;
		button3.addActionListener(this);
		W_Screen.add(button3, c);
		
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
class WelScreen extends JPanel {
	
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