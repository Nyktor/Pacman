package objects.entities.characters.ghost;

import game.Direction;
import objects.entities.Entity;
import objects.entities.characters.GameCharacter;

public class Ghost extends GameCharacter {
	
	private GhostName name;
	private GhostState state;
	private Direction dir;
	private Entity entityUnderneath; // this is just to manage double collision between Pacman, a Ghost and a Fruit/Ball
	
	public Ghost(GhostName name) {
		super();
		this.name = name;
		this.state = GhostState.READY;
		this.dir = Direction.LEFT;
		this.entityUnderneath = null;
	}
	
	public Ghost(GhostName name, int column, int row, Direction dir) {
		super();
		this.column = column;
		this.row = row;
		this.name = name;
		this.dir = dir;
		this.state = GhostState.READY;
	}
	
	public Ghost(GhostName name, int column, int row, Entity entityUnderneath) {
		super();
		this.column = column;
		this.row = row;
		this.entityUnderneath = entityUnderneath;
		this.name = name;
		this.dir = Direction.LEFT;
		this.state = GhostState.READY;
	}

	public GhostName getName() {
		return name;
	}

	public void setName(GhostName name) {
		this.name = name;
	}

	public GhostState getState() {
		return state;
	}

	public void setState(GhostState state) {
		this.state = state;
	}
	
	public Entity getEntityUnderGhost() {
		return entityUnderneath;
	}
	
	public void setEntityUnderGhost(Entity entity) {
		this.entityUnderneath = entity;
	}

	public boolean isOnAnEntity() {
		return entityUnderneath != null;
	}

	public Direction getDirection() {
		return dir;
	}

	public void setDirection(Direction dir) {
		this.dir = dir;
	}


}
