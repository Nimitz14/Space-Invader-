package game;

public class HE {
	public static int Width = 2;
	public static int Height = 2;
	public int heSpeed = 8;
	
	public int hX;
	public int hY;
	
	public boolean hDeath;
	
	public HE() {
		this.hX = GCharacter.X + GCharacter.Width/2;
		this.hY = GCharacter.Y + GCharacter.Height/2;
		this.hDeath = false;
	}

}
