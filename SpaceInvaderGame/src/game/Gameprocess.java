package game;

import java.awt.*;
import java.util.Timer;
import java.util.ArrayList;
import java.util.TimerTask;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.*;

import java.util.Random;

@SuppressWarnings("serial")
public class Gameprocess extends JPanel implements Runnable {

	// OBJECTS
	public GCharacter character;
	public static ArrayList<Alien> Alienl = new ArrayList<Alien>();
	public static ArrayList<Projectile> projectilel = new ArrayList<Projectile>();
	public static ArrayList<Projectile> pgraveList = new ArrayList<Projectile>();
	public static ArrayList<Alien> agraveList = new ArrayList<Alien>();

	// GLOBAL VARIABLES
	public static int buffer = 30;
	public static int floor = 500;
	public static int missed = 0;
	public static boolean right = false;
	public static boolean left = false;
	public static boolean running = true;
	public static int counter = 0;
	public static boolean gameover = false;
	public static long timer = 0;

	// MOVEMENT VARS AND MISC
	public int keyUp = KeyEvent.VK_W;
	public int keyLeft = KeyEvent.VK_A;
	public int keyRight = KeyEvent.VK_D;
	public int keyFire = KeyEvent.VK_SPACE;
	public boolean objectsDefined = false;

	JLabel label;

	// STARS
	public Rectangle[] star;
	public int stars = 100;
	public int currentStarSize;

	public Thread game;

	// Keylistener, initializes objects und via game.start() starts run() and thus the game

	public Gameprocess(ab f) {
		this.setBackground(Color.BLACK);
		defineObjects();

		game = new Thread(this);
		game.start();

		f.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == keyLeft) {
					left = true;
				}
				if (e.getKeyCode() == keyRight) {
					right = true;
				}

				if (e.getKeyCode() == keyFire) {
					projectilel.add(new Projectile());
				}
			}

			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == keyLeft) {
					left = false;
				}
				if (e.getKeyCode() == keyRight) {
					right = false;
				}

			}
		});

	}

	// game process

	public void run() {
		while (running) {

			// moves character left/right
			character.movement();

			// moves alien, changes variable aliendeath if alien hits ground
			// and increases missed counter
			for (Alien A : Alienl) {
				A.alienBehaviour();
			}

			// moves projectile changes pdeath if it hits alien or ceiling
			for (Projectile P : projectilel) {
				P.projectileBehaviour();
			}

			// checks for game state, ends it if missed>5
			gameState();

			// checks for collision between projectile and alien, changes state of projectile so that it 
			// will be deleted, variable explosion so alien not shown anymore
			// initializes parts of explosion and gives out destroyed nr of aliens
			for (Alien A : Alienl) {
				for (Projectile P : projectilel) {
					checkCollision(A, P);
				}
			}

			// delete alien if aliendeath true
			Alien.alienDeathTime();

			// delete projectile if aliendeath true
			Projectile.projectileDeath();

			fpsSetter();

			repaint();

			// adds aliens add set rate, bad implementation i know
			if (timer % 45 == 0) {
				Alien.alienAdd();
			}

			timer++;

		}
	}

	// MALT GRAFIK

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (objectsDefined) {

			g.setColor(Color.WHITE);

			// draws character
			g.fillRect(GCharacter.X, GCharacter.Y, GCharacter.Width,
					GCharacter.Height);

			// draws aliens that are not exploding
			for (Alien A : Alienl) {
				if (!A.explosion) {
					g.fillRect(A.posX, A.posY, Alien.Width, Alien.Height);
				}
			}

			// draws projectile
			for (Projectile P : projectilel) {
				g.fillRect(P.pX, P.pY, Projectile.pWidth, Projectile.pHeight);
			}

			// draws parts (explosion)
			for (Alien A : Alienl) {
				if (A.explosion) {

					// moves parts of explosion
					explosionTime(A);

					g.setColor(Color.RED);

					// without this an error occurs

					if (A.part1 == null) {
						IO.println("");
					}	

					// but doesnt work with this...
					// (A.part1 == null) {
						// System.out.print("");
					// }
				// draws parts of explosion
					g.fillRect(A.part2.eX, A.part2.eY, Part.width, Part.height);
					g.fillRect(A.part1.eX, A.part1.eY, Part.width, Part.height);
					g.fillRect(A.part3.eX, A.part3.eY, Part.width, Part.height);
					g.fillRect(A.part4.eX, A.part4.eY, Part.width, Part.height);

				}
			}

			// draws stars
			g.setColor(Color.WHITE);
			for (int j = 0; j < star.length; j++) {
				g.fill3DRect(star[j].x, star[j].y, star[j].width,
						star[j].height, true);
			}

			// draws floor
			g.setColor(Color.GRAY);
			g.fillRect(0, floor, Setup.width, Setup.height);

		}

	}

	// FUNCTIONS: ~

	// Initializes objects: character, alien and stars.
	public void defineObjects() {
		character = new GCharacter();

		// int i = IO.readInt("How many Aliens do you want to start with? ");
		int i = 1;
		for (int j = 0; j < i; j++) {
			Alienl.add(new Alien());
		}

		star = new Rectangle[stars];

		for (int j = 0; j < star.length; j++) {
			currentStarSize = randomWithRange(1, 4);
			star[j] = new Rectangle(randomWithRange(0, Setup.width),
					randomWithRange(0, floor), currentStarSize, currentStarSize);
		}

		objectsDefined = true;
	}

	// checks for collision between projectile and alien, changes state of projectile so that it 
	// will be deleted, variable explosion so alien not shown anymore
	// initializes parts of explosion and gives out destroyed nr of aliens

	public void checkCollision(Alien alien, Projectile P) {
		if (alien.posX < P.pX && alien.posX + Alien.Width > P.pX) {

			if (alien.posY < P.pY && alien.posY + Alien.Width > P.pY) {
				P.pDeath = true;
				alien.explosion = true;

				if (!alien.initialized) {
					alien.part1 = new Part(alien.posX, alien.posY);
					alien.part2 = new Part(alien.posX + Alien.Width, alien.posY);
					alien.part3 = new Part(alien.posX, alien.posY
							+ Alien.Height);
					alien.part4 = new Part(alien.posX + Alien.Width, alien.posY
							+ Alien.Height);
					alien.initialized = true;
				}

				Gameprocess.counter++;

				String n = "" + counter;
				Text("You have destroyed " + n + " enemy space ships!");
			}
		}

	}

	// explosion process
	public void explosionTime(Alien A) {

		
		if (A.initialized) {
			if (A.i <= 13) {
				if (A.i % 1 == 0) {
					A.part1.eX -= Part.eSpeed;
					A.part1.eY -= Part.eSpeed;
					A.part2.eX += Part.eSpeed;
					A.part2.eY -= Part.eSpeed;
					A.part3.eX -= Part.eSpeed;
					A.part3.eY += Part.eSpeed;
					A.part4.eX += Part.eSpeed;
					A.part4.eY += Part.eSpeed;
				}
				A.i++;
			} else {
				A.initialized = false;
				A.i = 0;
				A.explosion = false;
				A.alienDeath = true;
			}
		}
	}

	// game state
	public void gameState() {
		if (missed == 4) {
			ab.panel.Text("You are about to lose Earth!");
		}
		if (missed >= 5) {
			ab.panel.Text("EARTH HAS BEEN DESTROYED!");
			Gameprocess.running = false;
		}
	}

	// function for label
	public void Text(String text) {
		this.removeAll();
		label = new JLabel(text);
		label.setFont(new Font("Serif", Font.BOLD, 30));
		label.setForeground(Color.RED);
		this.add(label);
		this.updateUI();
	}

	// FPS Setter
	@SuppressWarnings("static-access")
	public void fpsSetter() {
		try {
			game.sleep(buffer);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// function for random number with range
	public static int randomWithRange(int min, int max) {
		Random random = new Random();
		int range = (max - min) + 1;
		return random.nextInt(range) + min;
	}
}
