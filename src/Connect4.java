
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class Connect4 {

	private int[][] board;
	private boolean player1;
	private boolean gameOver;
	private LinkedList<Move> moves;
	private String filepath;

	/**
	 * Constructor
	 * 
	 * @param filename
	 */
	public Connect4(String filepath) {
		this.filepath = filepath;
		FileReaderWriter fr = new FileReaderWriter(filepath);
		board = fr.getBoard();
		int player = fr.getPlayer();
		if (player == 0 || player == 1) {
			player1 = true;
		} else if (player == 2) {
			player1 = false;
		}
		start();
	}

	/**
	 * reset (re-)sets the game state to start a new game.
	 */
	public void reset() {
		FileReaderWriter fr = new FileReaderWriter(filepath);
		board = new int[6][7];
		player1 = true;
		gameOver = false;
		moves = new LinkedList<>();
		fr.writeWinner(board, 1);
	}

	public void start() {
		FileReaderWriter f = new FileReaderWriter(filepath);
		board = f.getBoard();
		if (f.getPlayer() == 2) {
			player1 = false;
		} else {
			player1 = true;
		}
		gameOver = false;
		moves = new LinkedList<>();
	}

	/**
	 * playTurn allows players to play a turn. Returns true if the move is
	 * successful and false if a player tries to play in a location that is taken or
	 * after the game has ended. If the turn is successful and the game has not
	 * ended, the player is changed. If the turn is unsuccessful or the game has
	 * ended, the player is not changed.
	 * 
	 * @param c column to play in
	 * @return whether the turn was successful
	 */
	public boolean playTurn(int c) {
		checkWinner();
		
		if (gameOver == true) {
			return false;
		}

		if (c < 0 || c > 6) {
			return false;
		}

		// check to see if the column is full
		if (board[0][c] != 0) {
			return false;
		}

		// find the row that the piece will play in
		int row = 0;
		for (int i = 0; i < 6; i++) {
			if (board[i][c] == 1 || board[i][c] == 2) {
				row = i - 1;
				break;
			} else {
				row = 5;
			}
		}

		if (player1) {
			board[row][c] = 1;
			player1 = false;
		} else {
			board[row][c] = 2;
			player1 = true;
		}

		// add the move to the linkedlist of moves
		Move newMove = new Move(row, c, player1);
		moves.addFirst(newMove);
		return true;
	}

	/**
	 * Finds the last move and undoes it from the board
	 */
	public void undo() {
		checkWinner();
		if(!gameOver) { 
			try {
				Move lastMove = moves.getFirst();
				int col = lastMove.getCol();
				int row = lastMove.getRow();

				player1 = !lastMove.getPlayer1();

				moves.removeFirst();

				board[row][col] = 0;
			} catch (NoSuchElementException e) {
				reset();
			}
		}
	}

	/**
	 * checkWinner checks whether the game has reached a win condition.
	 * 
	 * @return 0 if nobody has won yet, 1 if player 1 has won, and 2 if player 2 has
	 *         won, 3 if the game hits stalemate
	 */
	public int checkWinner() {
		// Check horizontal win
		for (int i = 0; i < board.length; i++) {
			int winner = helperCheckWinner(board[i]);
			if (winner != 0) {
				gameOver = true;
				return winner;
			}
		}

		// check vertical win
		int[] column = new int[6];
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < board.length; j++) {
				column[j] = board[j][i];
			}
			int winner = helperCheckWinner(column);
			if (winner == 1 || winner == 2) {
				gameOver = true;
				return winner;
			}
		}
		

		// check diagonal up
		int diag = diagUpWin();
		if (diag == 1 || diag == 2) {
			gameOver = true;
			return diag;
		}

		// check diagonal win down
		int diagDown = diagDownWin();
		if (diagDown == 1 || diagDown == 2) {
			gameOver = true;
			return diagDown;
		}

		return 0;
	}

	/**
	 * checks to see if there are four in a row in a downward diagonal
	 * 
	 * @return 1 if player1 wins, 2 if player2 wins and 0 otherwise
	 */
	private int diagUpWin() {
		for (int i = 3; i < board.length; i++) {
			for (int j = 0; j < board[i].length - 3; j++) {
				if (board[i][j] == 1) {
					if (board[i - 1][j + 1] == 1 && board[i - 2][j + 2] == 1 && board[i - 3][j + 3] == 1) {
						return 1;
					}
				} else if (board[i][j] == 2) {
					if (board[i - 1][j + 1] == 2 && board[i - 2][j + 2] == 2 && board[i - 3][j + 3] == 2) {
						return 2;
					}
				}
			}
		}
		return 0;
	}

	private int diagDownWin() {
		for (int i = 3; i < board.length; i++) {
			for (int j = 3; j < board[i].length; j++) {
				if (board[i][j] == 1) {
					if (board[i - 1][j - 1] == 1 && board[i - 2][j - 2] == 1 && board[i - 3][j - 3] == 1) {
						return 1;
					}
				} else if (board[i][j] == 2) {
					if (board[i - 1][j - 1] == 2 && board[i - 2][j - 2] == 2 && board[i - 3][j - 3] == 2) {
						return 2;
					}
				}
			}
		}
		return 0;
	}

	/**
	 * checks to see if there are four in a row in an upward diagonal
	 * 
	 * @return 1 if player1 wins, 2 if player2 wins and 0 otherwise
	 */

	/**
	 * checks to see if there are four in a row in the 1d array
	 * 
	 * @param arr
	 * @return 1 if player1 wins, 2 if player2 wins and 0 otherwise
	 */
	private int helperCheckWinner(int[] arr) {
		for (int i = 0; i <= arr.length - 4; i++) {
			if ((arr[i] == arr[i + 1]) && (arr[i + 2] == arr[i + 3]) && (arr[i] == arr[i + 2])) {
				return arr[i];
			}
		}
		return 0;
	}

	public void save() {
		if (gameOver) {
			FileReaderWriter f = new FileReaderWriter(filepath);
			f.writeWinner(new int[6][7], 1);
		} else {
			int val = 1;
			if (!player1) {
				val = 2;
			}
			FileReaderWriter f = new FileReaderWriter(filepath);
			f.writeWinner(this.board, val);
		}
	}

	/**
	 * printGameState prints the current game state for debugging.
	 */
	public void printGameState() {

		System.out.println(moves.toString());

		System.out.println("winner: " + checkWinner());

		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				System.out.print(board[i][j]);
				if (j < 7) {
					System.out.print(" | ");
				}
			}

			if (i < 6) {
				System.out.println("\n-----------------------------------------");
			}
		}
	}

	/**
	 * getCurrentPlayer is a getter for the player whose turn it is in the game.
	 * 
	 * @return true if it's Player 1's turn, false if it's Player 2's turn.
	 */
	public boolean getCurrentPlayer() {
		return player1;
	}

	/**
	 * getCell is a getter for the contents of the cell specified by the method
	 * arguments.
	 * 
	 * @param c column to retrieve
	 * @param r row to retrieve
	 * @return an integer denoting the contents of the corresponding cell on the
	 *         game board. 0 = empty, 1 = Player 1, 2 = Player 2
	 */
	public int getCell(int r, int c) {
		if (r < 0 || c < 0 || r > 5 || c > 6) {
			throw new IllegalArgumentException("invalid input to get cell");
		}
		return board[r][c];
	}

	public LinkedList<Move> getMoves() {
		return this.moves;
	}

	public boolean getGameOver() {
		return this.gameOver;
	}

	public int[][] getBoard() {
		return this.board;
	}

	public static void main(String[] args) {
		Connect4 game = new Connect4("files/connect4Test.txt");

		game.printGameState();

		game.playTurn(4);
		game.printGameState();

		game.playTurn(4);
		game.printGameState();

		game.playTurn(4);
		game.printGameState();

		game.playTurn(4);
		game.printGameState();
		game.playTurn(4);
		game.printGameState();
		game.playTurn(4);
		game.printGameState();
		game.playTurn(4);
		game.printGameState();
		game.playTurn(4);
		game.printGameState();

	}
}
