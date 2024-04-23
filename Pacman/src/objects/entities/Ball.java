package objects.entities;

public class Ball extends Entity{
	
	private boolean big;
	
	public Ball(int column, int row) {
		this.big = false;
		this.column = column;
		this.row = row;
	}
	
	public Ball(boolean big, int column, int row) {
		this.big = big;
		this.column = column;
		this.row = row;
	}

	public boolean isBig() {
		return big;
	}

	public void setBig(boolean big) {
		this.big = big;
	}
	
}
