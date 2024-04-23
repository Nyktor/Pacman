package objects.entities;

public class Entity {

	protected int column;
	protected int row;
	
	public Entity() {
		this.column = 1;
		this.row = 1;
	}
	
	public Entity(int column, int row) {
		this.column = column;
		this.row = row;
	}
	
	public int getColumn() {
		return column;
	}
	public void setColumn(int column) {
		this.column = column;
	}
	
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
}
