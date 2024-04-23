package objects.map;

import objects.entities.Entity;

public class Square {

	private int column;
	private int row;
	private Entity e;
	
	public Square(int column, int row, Entity e) {
		this.setColumn(column);
		this.setRow(row);
		this.setEntity(e);
	}
	
	public Square() {
		this.setColumn(0);
		this.setRow(0);
		this.setEntity(null);
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

	public Entity getEntity() {
		return e;
	}

	public void setEntity(Entity e) {
		this.e = e;
	}

}
