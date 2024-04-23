package game;

import objects.entities.Ball;
import objects.entities.Entity;
import objects.entities.characters.GameCharacter;
import objects.entities.characters.Pacman;
import objects.entities.characters.ghost.Ghost;
import objects.entities.characters.ghost.GhostName;
import objects.entities.fruits.Fruit;
import objects.entities.fruits.FruitType;
import objects.map.Floor;
import objects.map.Map;

public class Game {
	
	private Map map = new Map();
	private Pacman pacman;
	private Ghost blinky;
	//private Ghost pinky;
	//private Ghost inky;
	//private Ghost clyde;
	
	private final byte POINTS_PER_SMALL_BALL = 10;
	private final byte POINTS_PER_BIG_BALL = 50;
	
	public final short BALLS_FOR_FIRST_FRUIT = (short)(map.getNumberOfBalls()*70/240);
	public final short BALLS_FOR_SECOND_FRUIT = (short)(map.getNumberOfBalls()*170/240);

	private long points;
	private int ballsEaten;
	private int level;
	
	private boolean bigBallEatenRecently;
	private boolean fruitPresent;
	
	
	public void initializeGame() {
		pacman = new Pacman(map.PACMAN_INITIAL_ROW, map.PACMAN_INITIAL_COLUMN);
		blinky = new Ghost(GhostName.BLINKY, 26, 2, map.getEntityAt(26, 2));
		//pinky = new Ghost(GhostName.PINKY, 2, 2, map.getEntityAt(2, 2));
		//inky = new Ghost(GhostName.INKY, 26, 28, map.getEntityAt(26, 28));
		//clyde = new Ghost(GhostName.CLYDE, 2, 28, map.getEntityAt(2, 28));
		
		ballsEaten = 0;
		level = 1;
		setPoints(0);
		
		map.initializeMap();
	}

	
	/**************************************************************************
	 * Method that moves any game character
	 * 
	 * @param character any game character, be it Pacman or any of the Ghosts
	 * @return <b>true</b> if the movement succeeded or <br>
	 * 		   <b>false</b> if an obstacle was found
	 **************************************************************************/
	public boolean moveCharacter(GameCharacter character) {
		Entity collided;
		boolean status = true;
		
		switch(character.getOrientation()) {
		
			case RIGHT ->{
				// If there's a wall to the right, do not move
				if(getBitToTheRight(character) == 0 || getBitToTheRight(character) == 2) {
					status = false;
					
				}else {
					// Get the entity located on the tile the entity is going to go
					collided = map.getEntityAt(character.getColumn(), character.getRow()+1);

					manageCollision(character, collided);
					
					character.setRow(character.getRow()+1);
				}
			}
			
			case UP->{
				// If there's a wall up, do not move
				if(getBitOver(character) == 0 || getBitOver(character) == 2) {
					status = false;
				}else {
					// Get the entity located on the tile entity is going to go
					collided = map.getEntityAt(character.getColumn()-1, character.getRow());

					manageCollision(character, collided);
					
					character.setColumn(character.getColumn()-1);
				}
				
			}
			
			case DOWN->{
				// If there's a wall down, do not move
				if(getBitUnder(character) == 0 || getBitUnder(character) == 2) {
					status = false;
				}else {
					// Get the entity located on the tile entity is going to go
					collided = map.getEntityAt(character.getColumn()+1, character.getRow());

					manageCollision(character, collided);
					
					character.setColumn(character.getColumn()+1);
				}
			}
			
			case LEFT->{
				// If there's a wall to the left, do not move
				if(getBitToTheLeft(character) == 0 || getBitToTheLeft(character) == 2) {
					status = false;
				}else {
					// Get the entity located on the tile entity is going to go
					collided = map.getEntityAt(character.getColumn(), character.getRow()-1);

					manageCollision(character, collided);
					
					character.setRow(character.getRow()-1);
				}
			}
			
			
			default->{}
		}
		
		// TODO manejar la coleccion entre pacman y fantasmas aqui usando el getRow y getColumn
		
		return status;
	}
	
	
	/****************************************************************
	 * Manages a collision between a character and another thing/Entity
	 * @param character the moving character: Pacman or any of the Ghosts
	 * @param collided the entity the character has collided with
	 *****************************************************************/
	private void manageCollision(GameCharacter character, Entity collided) {
		
		/* PACMAN COLLIDING */
		if(character instanceof Pacman) {
			
			// If there's a ball, eat it and set a floor where it was
			if(collided instanceof Ball) {
				
				Ball ball = (Ball) collided;
				
				ballsEaten++;
				
				if(ball.isBig()) {
					setPoints(getPoints() + POINTS_PER_BIG_BALL);
					bigBallEatenRecently = true;
					
				}else {
					setPoints(getPoints() + POINTS_PER_SMALL_BALL);
				}
				
				//System.out.println(ballsEaten+"/"+map.getNumberOfBalls());
				
				switch(character.getOrientation()) {
					case DOWN->{
						map.setEntityAt(pacman.getColumn()+1, pacman.getRow(), new Floor());
					}
					case LEFT->{
						map.setEntityAt(pacman.getColumn(), pacman.getRow()-1, new Floor());
					}
					case RIGHT->{
						map.setEntityAt(pacman.getColumn(), pacman.getRow()+1, new Floor());
					}
					case UP->{
						map.setEntityAt(pacman.getColumn()-1, pacman.getRow(), new Floor());
					}
					default ->{}
				
				}
	
			// If there's a ghost... 
			}else if(collided instanceof Ghost) {

				// TODO
				Ghost ghost = (Ghost) collided;
				
				// ... handle the situation according to its state
				switch(ghost.getState()) {
					
					// If it's just chilling, kill pacman and stop the current game
					case NORMAL->{
						pacman.setDead(true);
					}
						
					// If it's vulnerable, it should eat it
					case VULNERABLE->{
					}
					
					// If it's eaten, colliding with it will have no effect
					case EATEN->{
					}
						
					// This is impossible.
					case READY->{
					}
					
					default->{
					}
				}
			
			// If there's a fruit
			}else if(collided instanceof Fruit) {
				Fruit fruit = (Fruit) collided;
	
				setPoints(getPoints() + fruit.getType().points);
				
				despawnFruit();
			}
		
			
		/* GHOST COLLIDING */
		}else if(character instanceof Ghost) {

			// TODO 
			Ghost ghost = (Ghost) character;
			
			// Update the ball that it's under da Ghost
			if(collided instanceof Ball) {
				ghost.setEntityUnderGhost((Ball)collided);
			
			// Update the fruit under the Ghost
			}else if(collided instanceof Fruit) {
				ghost.setEntityUnderGhost((Fruit)collided);

			// Put the 
			}else if(collided instanceof Ghost) {
				ghost.setEntityUnderGhost((Ghost)collided);
				
			// Kill Pacman
			}else if(collided instanceof Pacman) {
				pacman.setDead(true);
				
			// Update so the ghost has no ball underneath
			}else if(collided instanceof Floor && !ghost.isOnAnEntity()) {
				ghost.setEntityUnderGhost(null);
			}
			
			// Return the object that was in the previous place
			map.setEntityAt(ghost.getColumn(), ghost.getRow(), ghost.getEntityUnderGhost());
		}
	}
	
	
	public void spawnFruit() {
		int column = map.getFruitCoords()[1];
		int row = map.getFruitCoords()[0];
		Fruit fruit = new Fruit(column, row);
		
		fruit.setType(switch(level) {
			case 1 ->{
				yield FruitType.CHERRY;
			}
			case 2 ->{
				yield FruitType.STRAWBERRY;
			}
			case 3,4 ->{
				yield FruitType.PEACH;
			}
			case 5,6->{
				yield FruitType.APPLE;
			}
			case 7,8->{
				yield FruitType.GRAPES;
			}
			case 9,10 ->{
				yield FruitType.GALAXIAN_FLAGSHIP;
			}
			case 11, 12 ->{
				yield FruitType.BELL;
			}
			default ->{
				yield FruitType.KEY;
			}
		});
		map.setEntityAt(row, column, fruit);
		
		fruitPresent = true;
	}
	
	public void despawnFruit() {
		int column = map.getFruitCoords()[1];
		int row = map.getFruitCoords()[0];
		map.setEntityAt(row, column, new Floor());
		
		fruitPresent = false;
	}
	
	
	private byte getBitUnder(GameCharacter c) {
		return map.BITMAP[c.getColumn()+1][c.getRow()];
	}
	
	private byte getBitOver(GameCharacter c) {
		return map.BITMAP[c.getColumn()-1][c.getRow()];
	}
	
	private byte getBitToTheRight(GameCharacter c) {
		return map.BITMAP[c.getColumn()][c.getRow()+1];
	}
	
	private byte getBitToTheLeft(GameCharacter c) {
		return map.BITMAP[c.getColumn()][c.getRow()+-1];
	}
	
	/*********************************AUTO-GENERATED METHODS*************************************/
	public Map getMap() {
		return this.map;
	}

	public Pacman getPacman() {
		return pacman;
	}
	
	public boolean isWon() {
		return ballsEaten == map.getNumberOfBalls();
	}

	public long getPoints() {
		return points;
	}

	public void setPoints(long points) {
		this.points = points;
	}

	public Ghost getBlinky() {
		return blinky;
	}

	public int getLevel() {
		return level;
	}
	public void levelUp() {
		this.level++;
	}
	
	public void resetLevel() {
		this.level = 0;
	}
	
	public int getBallsEaten() {
		return ballsEaten;
	}
	
	public void resetBallsEaten() {
		this.ballsEaten = 0;
	}

	public boolean isLost() {
		return pacman.getLives() <= 0;
	}

	public boolean hasEatenBigBallRecently() {
		return bigBallEatenRecently;
	}

	public void resetBigBallEaten() {
		this.bigBallEatenRecently = false;
	}

	public boolean isFruitPresent() {
		return fruitPresent;
	}

	
	
	/*/* Generic movement method pacman 
	public void movePacman() {
		Entity collided;
		
		switch(pacman.getOrientation()) {
		
			case RIGHT ->{
				// If there's a wall to the right, do not move
				if(getBitToTheRight(pacman) == 0 || getBitToTheRight(pacman) == 2) {
					return;
				}else {
					// Get the entity located on the tile pacman is going to go
					collided = map.getEntityAt(pacman.getColumn(), pacman.getRow()+1);

					manageCollision(pacman, collided, Direction.RIGHT);
					
					pacman.setRow(pacman.getRow()+1);
				}
			}
			
			case UP->{
				// If there's a wall up, do not move
				if(getBitOver(pacman) == 0 || getBitOver(pacman) == 2) {
					return;
				}else {
					// Get the entity located on the tile pacman is going to go
					collided = map.getEntityAt(pacman.getColumn()-1, pacman.getRow());

					manageCollision(pacman, collided, Direction.UP);
					
					pacman.setColumn(pacman.getColumn()-1);
				}
				
			}
			
			case DOWN->{
				// If there's a wall down, do not move
				if(getBitUnder(pacman) == 0 || getBitUnder(pacman) == 2) {
					return;
				}else {
					// Get the entity located on the tile pacman is going to go
					collided = map.getEntityAt(pacman.getColumn()+1, pacman.getRow());

					manageCollision(pacman, collided, Direction.DOWN);
					
					pacman.setColumn(pacman.getColumn()+1);
				}
			}
			
			case LEFT->{
				// If there's a wall to the left, do not move
				if(getBitToTheLeft(pacman) == 0 || getBitToTheLeft(pacman) == 2) {
					return;
				}else {
					// Get the entity located on the tile pacman is going to go
					collided = map.getEntityAt(pacman.getColumn(), pacman.getRow()-1);

					manageCollision(pacman, collided, Direction.LEFT);
					
					pacman.setRow(pacman.getRow()-1);
				}
			}
			
			default->{}
		}
	}*/
}
