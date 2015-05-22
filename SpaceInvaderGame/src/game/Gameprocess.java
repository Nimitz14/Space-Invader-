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

	// Keylistener, initialisiert objekte und startet run() == SPIELVERLAUF.

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

	// SPIELVERLAUF

	public void run() {
		while (running) {

			// Bewegt character links/rechts.
			character.movement();

			// Bewegt alien, veraendert aliendeath falls noetig damit alien
			// geloescht und
			// zaehlt wieviel durch.
			for (Alien A : Alienl) {
				A.alienBehaviour();
			}

			// Bewegt projektil veraendert pdeath falls noetig damit alien
			// geloescht.
			for (Projectile P : projectilel) {
				P.projectileBehaviour();
			}

			// Prueft spielzustand, endet falls noetig.
			gameState();

			// Prueft fuer kollision zwischen projektil und alien, veraendert
			// variablen pdeath damit projektil geloescht, explosion damit alien
			// nicht mehr angezeigt wird,
			// initialisiert teile von explosion und gibt treffer aus.
			for (Alien A : Alienl) {
				for (Projectile P : projectilel) {
					checkCollision(A, P);
				}
			}

			// Loescht alien falls noetig.
			Alien.alienDeathTime();

			// Loescht projektil falls noetig.
			Projectile.projectileDeath();

			fpsSetter();

			repaint();

			// krieg ich nicht hin mit timer/timertask, fuegt aliens hinzu.
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

			// malt character
			g.fillRect(GCharacter.X, GCharacter.Y, GCharacter.Width,
					GCharacter.Height);

			// malt Aliens die nicht gerade explodieren
			for (Alien A : Alienl) {
				if (!A.explosion) {
					g.fillRect(A.posX, A.posY, Alien.Width, Alien.Height);
				}
			}

			// malt projektil
			for (Projectile P : projectilel) {
				g.fillRect(P.pX, P.pY, Projectile.pWidth, Projectile.pHeight);
			}

			// zeichnet explosion(en)
			for (Alien A : Alienl) {
				if (A.explosion) {

					// veraendert koordinaten von parts/teile von explosion
					explosionTime(A);

					g.setColor(Color.RED);

					// Ohne das hier krieg ich ein (nicht fatalen) Error.

					if (A.part1 == null) {
						IO.println("");
					}

					// Komischerweise klappt es hiermit aber nicht!
					// (A.part1 == null) {
					// System.out.print("");
					// }

					// malt parts/teile von explosion
					g.fillRect(A.part1.eX, A.part1.eY, Part.width, Part.height);
					g.fillRect(A.part2.eX, A.part2.eY, Part.width, Part.height);
					g.fillRect(A.part3.eX, A.part3.eY, Part.width, Part.height);
					g.fillRect(A.part4.eX, A.part4.eY, Part.width, Part.height);

				}
			}

			// malt sterne
			g.setColor(Color.WHITE);
			for (int j = 0; j < star.length; j++) {
				g.fill3DRect(star[j].x, star[j].y, star[j].width,
						star[j].height, true);
			}

			// malt boden
			g.setColor(Color.GRAY);
			g.fillRect(0, floor, Setup.width, Setup.height);

		}

	}

	// FUNKTIONEN: ~~

	// Initialisiert objekte: charakter, alien und sterne.
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

	// Prueft fuer kollision zwischen projektil und alien, veraendert variablen
	// pDeath damit projektil geloescht, explosion damit alien nicht mehr
	// angezeigt wird, initialisiert teile von explosion und gibt counter aus.

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

	// explosionsverlauf
	public void explosionTime(Alien A) {

		// falls explosionsteile intialisiert
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

	// spielende ?
	public void gameState() {
		if (missed == 4) {
			ab.panel.Text("You are about to lose Earth!");
		}
		if (missed >= 5) {
			ab.panel.Text("EARTH HAS BEEN DESTROYED!");
			Gameprocess.running = false;
		}
	}

	// funktion fuer label
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

	// funktion fuer zufaellige zahlen
	public static int randomWithRange(int min, int max) {
		Random random = new Random();
		int range = (max - min) + 1;
		return random.nextInt(range) + min;
	}
}
