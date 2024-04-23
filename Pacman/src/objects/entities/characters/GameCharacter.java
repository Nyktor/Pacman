package objects.entities.characters;

import game.Direction;
import objects.entities.Entity;

public class GameCharacter extends Entity {

	private Direction orientation;
	
	public GameCharacter() {
		super();
		this.orientation = Direction.LEFT;
	}

	public Direction getOrientation() {
		return orientation;
	}

	public void setOrientation(Direction orientation) {
		this.orientation = orientation;
	}

}
