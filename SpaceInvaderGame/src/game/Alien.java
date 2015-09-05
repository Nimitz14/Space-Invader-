package game;

public class Alien {
	public static final int A_WIDTH = 15;
	public static final int A_HEIGHT = 30;
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
		this.posX = screens.Util.randomWithRange(0, 750) + Alien.A_WIDTH;
		this.posY = 0;
		this.aSpeed = screens.Util.randomWithRange(minSpeed, maxSpeed);
		i = 0;
		alienDeath = false;
	}

	// Moves alien, changes alienDeath if alien hits ground and increases
	// counter.
	public void alienBehaviour() {

		if (!this.alienDeath) {
			this.posY += this.aSpeed;
		}

		if (this.posY >= Gameprocess.FLOOR) {
			this.alienDeath = true;
			Gameprocess.MISSED++;
		}

	}

	// if aliendeath true remove alien from arraylist
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

// Explosion parts

class Part {

	public static final int P_WIDTH = Alien.A_WIDTH / 2;
	public static final int P_HEIGHT = Alien.A_HEIGHT / 2;
	public static final int eSpeed = 5;

	public int eX;
	public int eY;

	public Part(int x, int y) {
		this.eX = x;
		this.eY = y;
	}

}
