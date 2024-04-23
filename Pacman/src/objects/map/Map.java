package objects.map;

import objects.entities.Ball;
import objects.entities.Entity;

public class Map {

	/* Byte representation of the map, where: 
	 * 0 = unaccessible
	 * 1 = accessible, with balls
	 * 2 = accessible, but only for the ghosts
	 * 3 = accessible, but no balls
	 * 4 = acceswsible, with a big ball
	 * 5 = accessible, fruit spawn, no balls. There must be only ONE.
	 */
	public final byte BITMAP[][] = {{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
									{0,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,1,1,1,1,1,1,1,1,1,1,1,0},
									{0,1,0,0,0,0,1,0,0,0,0,0,1,0,0,1,0,0,0,0,0,1,0,0,0,0,1,0},
									{0,4,0,0,0,0,1,0,0,0,0,0,1,1,1,1,0,0,0,0,0,1,0,0,0,0,4,0},
									{0,1,0,0,0,0,1,0,0,0,0,0,1,0,0,1,0,0,0,0,0,1,0,0,0,0,1,0},
									{0,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,1,1,1,1,1,1,1,1,1,1,1,0},
									{0,1,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,1,0},
									{0,1,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,1,0},
									{0,1,1,1,1,1,1,0,0,1,1,1,1,0,0,1,1,1,1,0,0,1,1,1,1,1,1,0},
									{0,0,0,1,0,0,1,0,0,0,0,0,1,0,0,1,0,0,0,0,0,1,0,0,1,0,0,0},
									{0,0,0,1,0,0,1,0,0,0,0,0,1,0,0,1,0,0,0,0,0,1,0,0,1,0,0,0},
									{0,1,1,1,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,1,1,0},
									{0,1,0,0,0,0,1,0,0,1,0,0,0,2,2,0,0,0,1,0,0,1,0,0,0,0,1,0},
									{0,1,0,0,0,0,1,0,0,1,0,0,3,3,3,3,0,0,1,0,0,1,0,0,0,0,1,0},
									{0,1,1,1,1,1,1,0,0,1,0,0,3,3,3,3,0,0,1,0,0,1,1,1,1,1,1,0},
									{0,1,0,0,0,0,1,0,0,1,0,0,3,3,3,3,0,0,1,0,0,1,0,0,0,0,1,0},
									{0,1,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,1,0},
									{0,1,1,1,0,0,1,0,0,3,3,3,3,5,3,3,3,3,3,0,0,1,0,0,1,1,1,0},
									{0,0,0,1,0,0,1,0,0,3,0,0,0,0,0,0,0,0,3,0,0,1,0,0,1,0,0,0},
									{0,0,0,1,0,0,1,0,0,3,0,0,0,0,0,0,0,0,3,0,0,1,0,0,1,0,0,0},
									{0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0},
									{0,1,0,0,0,0,1,0,0,0,0,0,1,0,0,1,0,0,0,0,0,1,0,0,0,0,1,0},
									{0,1,0,0,0,0,1,0,0,0,0,0,1,0,0,1,0,0,0,0,0,1,0,0,0,0,1,0},
									{0,4,1,1,0,0,1,1,1,1,1,1,1,0,0,1,1,1,1,1,1,1,0,0,1,1,4,0},
									{0,0,0,1,0,0,1,0,0,1,0,0,0,0,0,0,0,0,1,0,0,1,0,0,1,0,0,0},
									{0,0,0,1,0,0,1,0,0,1,0,0,0,0,0,0,0,0,1,0,0,1,0,0,1,0,0,0},
									{0,1,1,1,1,1,1,0,0,1,1,1,1,0,0,1,1,1,1,0,0,1,1,1,1,1,1,0},
									{0,1,0,0,0,0,1,0,0,0,0,0,1,0,0,1,0,0,0,0,0,1,0,0,0,0,1,0},
									{0,1,0,0,0,0,1,0,0,0,0,0,1,0,0,1,0,0,0,0,0,1,0,0,0,0,1,0},
									{0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0},
									{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}};
	public final int WIDTH = BITMAP[0].length; // 28
	public final int HEIGHT = BITMAP.length; // 30
	
	public final int PACMAN_INITIAL_COLUMN = 13;
	public final int PACMAN_INITIAL_ROW = 17;
	
	private int numberOfBalls;
	private int[] fruitCoords = new int[2];
	
	private Square[][] squareMap = new Square[HEIGHT][WIDTH];
	
	/* Constructor */
	public Map() {
		setNumberOfBalls(0);
		initializeMap();
	}
	
	/* Method that initializes the map */
	public void initializeMap() {
		numberOfBalls = 0;
		
		for(int i = 0; i < HEIGHT-1; i++) {
			for(int j = 0; j < WIDTH-1; j++) {
				switch(BITMAP[i][j]) {
				
					// Wall
					case 0 ->{
						squareMap[i][j]  = new Square(j, i, new Wall());
					}
					
					// Small ball
					case 1 ->{
						squareMap[i][j]  = new Square(j, i, new Ball(j, i));
						numberOfBalls++;
					}
					
					// Big ball
					case 4 ->{
						squareMap[i][j]  = new Square(j, i, new Ball(true, j, i));
						numberOfBalls++;
					}
					
					case 5 ->{
						squareMap[i][j]  = new Square(j, i, new Floor());
						fruitCoords[0] = i; 
						fruitCoords[1] = j;
					}
					
					// Floor
					default ->{
						squareMap[i][j]  = new Square(j, i, new Floor());
					}
				}
				
			}
		}
	}
	
	public void refillMap() {
		for(int i = 0; i < HEIGHT-1; i++) {
			for(int j = 0; j < WIDTH-1; j++) {
				switch(BITMAP[i][j]) {
					case 1 ->{
						squareMap[i][j]  = new Square(j, i, new Ball(j, i));
					}
					
					// Big ball
					case 4 ->{
						squareMap[i][j]  = new Square(j, i, new Ball(true, j, i));
					}
					
					default ->{
						continue;
					}
				}
				
			}
		}
	}

	public int getNumberOfBalls() {
		return numberOfBalls;
	}

	public void setNumberOfBalls(int numberOfBalls) {
		this.numberOfBalls = numberOfBalls;
	}
	
	public Entity getEntityAt(int column, int row) {
		return squareMap[column][row].getEntity();
	}
	
	public void setEntityAt(int column, int row, Entity e) {
		squareMap[column][row].setEntity(e);
	}
	
	public int[] getFruitCoords() {
		return fruitCoords;
	}

}
