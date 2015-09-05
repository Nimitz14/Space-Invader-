package game;

import java.awt.*;

//import java.util.Timer;
import java.util.ArrayList;
//import java.util.TimerTask;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.*;

//import java.util.Random;

@SuppressWarnings("serial")
public class Gameprocess extends JPanel implements Runnable {

	// OBJECTS
	public GCharacter character;
	public static ArrayList<Alien> Alienl = new ArrayList<Alien>();
	public static ArrayList<Projectile> projectilel = new ArrayList<Projectile>();
	public static ArrayList<Projectile> pgraveList = new ArrayList<Projectile>();
	public static ArrayList<Alien> agraveList = new ArrayList<Alien>();

	// GLOBAL VARIABLES
	public static final int BUFFER = 30;
	public static final int FLOOR = 500;
	public static int MISSED = 0;
	public static boolean RIGHT = false;
	public static boolean LEFT = false;
	public static boolean RUNNING = true;
	public static int counter = 0;
	public static boolean gameover = false;
	public static long timer = 0;

	// MOVEMENT VARS AND MISC
	public int keyUp = KeyEvent.VK_W;
	public int keyLEFT = KeyEvent.VK_A;
	public int keyRIGHT = KeyEvent.VK_D;
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
				if (e.getKeyCode() == keyLEFT) {
					LEFT = true;
				}
				if (e.getKeyCode() == keyRIGHT) {
					RIGHT = true;
				}

				if (e.getKeyCode() == keyFire) {
					if(Projectile.is_reloaded()) {
						projectilel.add(new Projectile());
					}
				}
			}

			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == keyLEFT) {
					LEFT = false;
				}
				if (e.getKeyCode() == keyRIGHT) {
					RIGHT = false;
				}

			}
		});

	}

	// -----------------------------------------------
	
	// game process

	public void run() {
		while (RUNNING) {

			// moves character LEFT/RIGHT
			character.movement();

			// moves alien, changes variable aliendeath if alien hits ground
			// and increases MISSED counter
			for (Alien A : Alienl) {
				A.alienBehaviour();
			}

			// moves projectile changes pdeath if it hits alien or ceiling
			for (Projectile P : projectilel) {
				P.projectileBehaviour();
			}

			// checks for game state, ends it if MISSED>5
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
			Projectile.rel_counter +=1;

		}
	}
	
	// -----------------------------------------------

	// MALT GRAFIK

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (objectsDefined) {

			g.setColor(Color.WHITE);

			// draws character
			g.fillRect(GCharacter.char_x, GCharacter.char_y, GCharacter.G_WIDTH,
					GCharacter.G_HEIGHT);

			// draws aliens that are not exploding
			for (Alien A : Alienl) {
				if (!A.explosion) {
					g.fillRect(A.posX, A.posY, Alien.A_WIDTH, Alien.A_HEIGHT);
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
					g.fillRect(A.part1.eX, A.part1.eY, Part.P_WIDTH, Part.P_HEIGHT);
					g.fillRect(A.part2.eX, A.part2.eY, Part.P_WIDTH, Part.P_HEIGHT);
					g.fillRect(A.part3.eX, A.part3.eY, Part.P_WIDTH, Part.P_HEIGHT);
					g.fillRect(A.part4.eX, A.part4.eY, Part.P_WIDTH, Part.P_HEIGHT);

				}
			}

			// draws stars
			g.setColor(Color.WHITE);
			for (int j = 0; j < star.length; j++) {
				g.fill3DRect(star[j].x, star[j].y, star[j].width,
						star[j].height, true);
			}

			// draws FLOOR
			g.setColor(Color.GRAY);
			g.fillRect(0, FLOOR, Setup.width, Setup.height);

		}

	}

	// -----------------------------------------------
	
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
			currentStarSize = screens.Util.randomWithRange(1, 4);
			star[j] = new Rectangle(screens.Util.randomWithRange(0, Setup.width),
					screens.Util.randomWithRange(0, FLOOR), currentStarSize, currentStarSize);
		}

		objectsDefined = true;
	}

	
	// -----------------------------------------------
	
	// checks for collision between projectile and alien, changes state of projectile so that it 
	// will be deleted, variable explosion so alien not shown anymore
	// initializes parts of explosion and gives out destroyed nr of aliens
	
	public void checkCollision(Alien alien, Projectile P) {
		if (alien.posX < P.pX && alien.posX + Alien.A_WIDTH > P.pX) {

			if (alien.posY < P.pY && alien.posY + Alien.A_WIDTH > P.pY) {
				P.pDeath = true;
				alien.explosion = true;

				if (!alien.initialized) {
					alien.part1 = new Part(alien.posX, alien.posY);
					alien.part2 = new Part(alien.posX + Alien.A_WIDTH, alien.posY);
					alien.part3 = new Part(alien.posX, alien.posY
							+ Alien.A_HEIGHT);
					alien.part4 = new Part(alien.posX + Alien.A_WIDTH, alien.posY
							+ Alien.A_HEIGHT);
					alien.initialized = true;
				}

				Gameprocess.counter++;

				String n = "" + counter;
				Text("You have destroyed " + n + " enemy space ships!");
			}
		}

	}

	// -----------------------------------------------
	
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
	
	// -----------------------------------------------

	// game state
	public void gameState() {
		if (MISSED == 4) {
			ab.panel.Text("You are about to lose Earth!");
		}
		if (MISSED >= 5) {
			ab.panel.Text("EARTH HAS BEEN DESTROYED!");
			Gameprocess.RUNNING = false;
		}
	}
	
	// -----------------------------------------------

	// function for label
	public void Text(String text) {
		this.removeAll();
		label = new JLabel(text);
		label.setFont(new Font("Serif", Font.BOLD, 30));
		label.setForeground(Color.RED);
		this.add(label);
		this.updateUI();
	}
	
	// -----------------------------------------------

	// FPS Setter
	@SuppressWarnings("static-access")
	public void fpsSetter() {
		try {
			game.sleep(BUFFER);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
}
