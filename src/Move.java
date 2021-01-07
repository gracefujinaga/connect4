/**
 * Class creates the object move, it has a row and a column and a player associated with it
 */

public class Move {
	
	private int row;
	private int col;
	private boolean player1;
	
	public Move(int row, int col, boolean player1) {
		this.row = row;
		this.col = col;
		this.player1 = player1;
	}
	
	public int getRow() {
		return this.row;
	}
	
	public int getCol() {
		return this.col;
	}
	
	public boolean getPlayer1() {
		return this.player1;
	}

}
