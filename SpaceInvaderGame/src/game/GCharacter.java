package game;

public class GCharacter {

	public static int Width = 10;
	public static int Height = 40;
	public static int movementSpeed = 6;
	public static int X;
	public static int Y;

	public GCharacter() {
		X = Setup.width / 2 - Width / 2;
		Y = Gameprocess.floor - Height;
	}

	public void movement() {
		if (Gameprocess.right) {
			X += movementSpeed;
		}
		if (Gameprocess.left) {
			X -= movementSpeed;
		}
	}

}
