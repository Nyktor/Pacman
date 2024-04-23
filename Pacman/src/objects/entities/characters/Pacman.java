package objects.entities.characters;

public class Pacman extends GameCharacter {
	
	private byte lives;
	private boolean dead;
	
	public Pacman(int column, int row) {
		this.column = column;
		this.row = row;
		this.lives = 3;
	}

	public byte getLives() {
		return lives;
	}

	public void setLives(byte lives) {
		this.lives = lives;
	}

	public boolean isDead() {
		return dead;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}

}
