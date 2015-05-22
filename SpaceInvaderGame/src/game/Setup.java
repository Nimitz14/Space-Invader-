package game;

import java.awt.*;

import javax.swing.*;

// ERSTELLT FRAME

public class Setup {

	public static ab f;
	public static int width = 800;
	public static int height = 600;

	public Setup() {

		f = new ab();
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setResizable(false);
		f.setSize(width, height);
		f.setTitle("SPACE INVADER");
		f.setLocationRelativeTo(null);

	}
}

// FRAME, ERSTELLT JPANEL

@SuppressWarnings("serial")
class ab extends JFrame {
	public static Gameprocess panel;

	public ab() {
		panel = new Gameprocess(this);
		setLayout(new GridLayout(1, 1, 0, 0));
		add(panel);

	}

}
