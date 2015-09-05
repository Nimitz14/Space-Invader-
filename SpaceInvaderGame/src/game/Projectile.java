package game;



public class Projectile {
	public static final int pWidth = 2;
	public static final int pHeight = 2;
	public static final int pSpeed = 11;
	public static int rel_counter = 11;
	
	
	public int pX;
	public int pY;

	public boolean pDeath;

	
	public Projectile() {
		this.pX = GCharacter.char_x + GCharacter.G_WIDTH/2;
		this.pY = GCharacter.char_y + GCharacter.G_HEIGHT/2;
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
	
	public static boolean is_reloaded() {
		if(rel_counter>10) {
			rel_counter = 0;
			return true;
		} else {
			return false;
		}
	}
	
}
