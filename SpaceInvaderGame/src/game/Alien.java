package game;

public class Alien {
	public static int Width = 15;
	public static int Height = 30;
	// public static long Frequency = 10000;

	public boolean explosion;

	// laenge von explosion counter
	public int i;

	public boolean initialized = false;

	public int aSpeed;
	public int minSpeed = 2;
	public int maxSpeed = 3;

	public int posX;
	public int posY;

	public boolean alienDeath;

	Part part1;
	Part part2;
	Part part3;
	Part part4;

	public Alien() {
		this.posX = Gameprocess.randomWithRange(0, 750) + Alien.Width;
		this.posY = 0;
		this.aSpeed = Gameprocess.randomWithRange(minSpeed, maxSpeed);
		i = 0;
		alienDeath = false;
	}

	// Bewegt alien, veraendert aliendeath falls notwendig und veraendert missed
	// counter.
	public void alienBehaviour() {

		if (!this.alienDeath) {
			this.posY += this.aSpeed;
		}

		if (this.posY >= Gameprocess.floor) {
			this.alienDeath = true;
			Gameprocess.missed++;
		}

	}

	// Loescht alien falls notwendig.
	public static void alienDeathTime() {

		for (Alien A : Gameprocess.Alienl) {
			if (A.alienDeath) {
				Gameprocess.agraveList.add(A);
			}
		}
		for (Alien A : Gameprocess.agraveList) {
			Gameprocess.Alienl.remove(A);
		}

	}

	public static void alienAdd() {
		Gameprocess.Alienl.add(new Alien());
	}

}

// Teile von explosion.

class Part {

	public static int width = Alien.Width / 2;
	public static int height = Alien.Height / 2;
	public static int eSpeed = 5;

	public int eX;
	public int eY;

	public Part(int x, int y) {
		this.eX = x;
		this.eY = y;
	}

}
