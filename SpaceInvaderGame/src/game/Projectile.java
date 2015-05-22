package game;



public class Projectile {
	public static int pWidth = 2;
	public static int pHeight = 2;
	public int pSpeed = 10;
	
	
	public int pX;
	public int pY;

	public boolean pDeath;

	
	public Projectile() {
		this.pX = GCharacter.X + GCharacter.Width/2;
		this.pY = GCharacter.Y + GCharacter.Height/2;
		this.pDeath = false;
	}
	
	
	public void projectileBehaviour() {
		if (this.pY <= 10) {
			pDeath = true;
		}
		
		if (!this.pDeath) {
		this.pY -= pSpeed;
			
		}
	}
	
	// zerstoert projektil falls pDeath 
	public static void projectileDeath() {
		for (Projectile P : Gameprocess.projectilel) {
			if (P.pDeath) {
				Gameprocess.pgraveList.add(P);
			}
		}
		for (Projectile P : Gameprocess.pgraveList) {
			Gameprocess.projectilel.remove(P);
		}
	}
	
	
	
}
