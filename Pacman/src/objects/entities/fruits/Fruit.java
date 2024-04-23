package objects.entities.fruits;

import objects.entities.Entity;

public class Fruit extends Entity{
	
	private FruitType type;
	
	public Fruit(int column, int row) {
		this.column = column;
		this.row = row;
		this.type = FruitType.CHERRY;
	}
	
	public Fruit(FruitType type, int column, int row) {
		this.column = column;
		this.row = row;
		this.type = type;
	}

	public FruitType getType() {
		return type;
	}

	public void setType(FruitType type) {
		this.type = type;
	}

}
