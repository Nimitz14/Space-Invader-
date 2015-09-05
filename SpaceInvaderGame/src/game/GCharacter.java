package game;

public class GCharacter {

	public static final int G_WIDTH = 10;
	public static final int G_HEIGHT = 40;
	public static final int movementSpeed = 6;
	public static int char_x;
	public static int char_y;

	public GCharacter() {
		char_x = Setup.width / 2 - G_WIDTH / 2;
		char_y = Gameprocess.FLOOR - G_HEIGHT;
	}

	public void movement() {
		if (Gameprocess.RIGHT) {
			char_x += movementSpeed;
		}
		if (Gameprocess.LEFT) {
			char_x -= movementSpeed;
		}
	}

}
