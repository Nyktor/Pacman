package game;

import java.util.Random;

public enum Direction {
	
	UP, LEFT, RIGHT, DOWN;
	
	public static Direction getRandomDirection() {
		Random r = new Random();
		
		return switch(r.nextInt(4)) {
			case 0 ->{
				yield UP;
			}
			
			case 1 ->{
				yield LEFT;
			}
			
			case 2 ->{
				yield RIGHT;
			}
			
			default ->{
				yield DOWN;
			}
		};
	}

}
