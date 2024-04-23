package objects.entities.fruits;

public enum FruitType {
	
	CHERRY(100),
	STRAWBERRY(300),
	PEACH(500),
	APPLE(700),
	GRAPES(1000),
	GALAXIAN_FLAGSHIP(2000),
	BELL(3000),
	KEY(5000);
	
	public final int points;

	private FruitType(int points) {
		this.points = points;
	}
}
