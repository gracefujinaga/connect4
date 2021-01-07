import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;
import java.util.NoSuchElementException;

import javax.swing.JLabel;

/** 
 *  You can use this file (and others) to test your
 *  implementation.
 */

public class GameTest {
	
	int[][] tb0 = new int[6][7];
	int[][] tb1 = new int[6][7];
	
	private boolean arrayEquality (int[][] expected, int[][] actual) {
		if(expected.length != actual.length || expected[0].length != actual[0].length) {
			return false;
		}
		
		for(int i = 0; i < tb1.length; i++) {
			for(int j = 0; j < tb1[i].length; j++) {
				if(expected[i][j] != actual[i][j]) {
					return false;
				}
			}
		}
		return true;
	}
	
    
    //test constructor --------------------------------------------------------------
    //null filepath
	 @Test
	 public void constructorFilePathNull() {
		 try {
			new Connect4(null);
		 } catch (IllegalArgumentException e){
			 return;
		 }
		 fail();
	 }
	 
	 @Test
	    public void testNoWinner4InARow(){
	        Connect4 c = new Connect4("files/connect4Test.txt");
			c.reset();
	        
	        c.playTurn(4);
	        c.playTurn(4);
	        c.playTurn(3);
	        c.playTurn(3);
	        c.playTurn(1);
	        c.playTurn(2);
	        c.playTurn(0);
	        
	        assertEquals(0, c.checkWinner());
	    }
	 //non existent filepath should throw illegal argument exception
	 @Test
	 public void constructorFilePathDNE() {
		 try {
			 new Connect4("files/haha");
		 } catch (IllegalArgumentException e){
			 return;
		 }
		 fail();
	 }
	    
	 //reset tests ------------------------------------------------------------------
	 @Test
	 public void testReset() {
		 Connect4 c = new Connect4("files/connect4Test.txt");
		 c.playTurn(3);
		 c.playTurn(2);
		 c.reset();
		 assertTrue(arrayEquality(tb0, c.getBoard()));
		 assertTrue(c.getCurrentPlayer());
		 assertFalse(c.getGameOver());
		 assertTrue(c.getMoves().isEmpty());
	 }
    
    //test playturn------------------------------------------------------------------
    /* 1) cant play in a location that is already used up
     * 2) a normal turn: add the moves to the collection,
     * 	that the right number is added
     * 3) cant play in a column that is not from 0 to 6
     * 4) cant play if the game is over
     */
	 @Test
	 public void testPTAddMoves() {
		 Connect4 c = new Connect4("files/goodArray.txt");
		 c.reset();
		 
		 assertTrue(c.getMoves().isEmpty());
		 
		 c.playTurn(4);
		 assertFalse(c.getMoves().isEmpty());
		 
		 Move that = c.getMoves().getFirst();
		 assertEquals(4, that.getCol());
		 assertEquals(5, that.getRow());
		 assertFalse(that.getPlayer1());
	 }
	 
	 @Test
	 public void testGameboardFull() {
		 Connect4 c = new Connect4("files/fullBoarNoWinner.txt");
		 assertEquals(0, c.checkWinner());
	 }
	 
	 //tests playturn, checkwinner, and getGameOver
	 @Test
	 public void testPTGameOver() {
		 Connect4 c = new Connect4("files/connect4Test.txt");
		 c.reset();
		 
		 
		 //player 1
		 c.playTurn(1);
		 //player 2
		 c.playTurn(2);
		 //player1
		 c.playTurn(1);
		 //player2
		 c.playTurn(2);
		 //player1
		 c.playTurn(1);
		 //player 2
		 c.playTurn(2);
		 //player 1
		 c.playTurn(1);
		 
		 assertEquals(1, c.checkWinner());
		 assertTrue(c.getGameOver());
		 assertFalse(c.playTurn(2));
	 }
	 
	 @Test
	 public void testPTOOB() {
		 Connect4 c = new Connect4("files/goodArray.txt");
		 
		 assertFalse(c.playTurn(8));
		 assertFalse(c.playTurn(-1));
	 }
	 
	 @Test
	 public void testPTWorks() {
		 Connect4 c = new Connect4("files/connect4Test.txt");
		 
		 //player one plays in column 4
		 c.playTurn(4);
		 assertEquals(1, c.getCell(5, 4));
		 
		 //player 2 plays in column 3
		 c.playTurn(3);
		 assertEquals(2, c.getCell(5, 3));
		 
		 //player 1 plays in column 2
		 c.playTurn(2);
		 assertEquals(1, c.getCell(5, 2));
		 
		 //player 2 plays in column 4
		 c.playTurn(4);
		 assertEquals(2, c.getCell(4, 4));
		 
		 //check that no other squares changed in size
		 assertEquals(0, c.getCell(0, 4));
		 assertEquals(0, c.getCell(0, 3));
		 assertEquals(0, c.getCell(0, 5));
		 assertEquals(0, c.getCell(0, 6));
		 assertEquals(0, c.getCell(0, 1));
	 }

    //test undo------------------------------------------------------------------
    /* 1) if its a valid undo, player1 should change, row and col should be changed correctly, 
     * 	0 should be the value in the game board, moves should no longer contain the old move
     * 2) nothing happens if gameover is true
     * 3) if the move doesnt exist (ie none played or none in list), board should be reset to zeros
     * 4) order of moves should be maintained
     */
	 @Test
	 public void testUndoElException() {
		 Connect4 c = new Connect4("files/connect4Test.txt");
		 c.reset();
		 c.playTurn(0);
		 c.undo();
		 c.undo();
		 
		 assertTrue(arrayEquality(tb0, c.getBoard()));
	 }
	 
	 @Test
	 public void testUndo() {
		 Connect4 c = new Connect4("files/connect4Test.txt");
		 c.reset();

		 c.playTurn(4);
		 c.playTurn(4);
		 
		 c.undo();
		 c.undo();
		
		 assertTrue(c.getMoves().isEmpty());
	 }
    
	 @Test
	 public void testUndo1Move() {
		 Connect4 c = new Connect4("files/connect4Test.txt");
		 c.reset();
		 c.playTurn(0);
		 
		 c.undo();
		 
		 //should make the cell 0
		 assertEquals(0, c.getCell(5, 0));
		 assertTrue(c.getMoves().isEmpty());
		 
	 }
	 
	 //this test checks that order is maintained
	 @Test
	 public void testUndo2Move() {
		 Connect4 c = new Connect4("files/connect4Test.txt");
		 c.reset();
		 
		 c.playTurn(0);
		 c.playTurn(0);
		 
		 c.undo();
		 assertEquals(0, c.getCell(4, 0));
		 assertEquals(1, c.getCell(5, 0));
		 
		 c.undo();
		 assertEquals(0, c.getCell(5, 0));
	 }
	 
	 
	 /*note that in a real game, not every value would be one, 
	  * but the file that was read in was intentionally all ones
	  */
	 @Test
	 public void testUndoGameOver() {
		 Connect4 c = new Connect4("files/arrayOnes.txt");
		 int[][] that = c.getBoard();
		 c.undo(); 
		 for(int i = 0; i < that.length; i++) {
			 for(int j = 0; j < that[i].length; j++) {
				 assertEquals(1, that[i][j]);		 
			 }
		 }
		 
	 }
	 
    //test checkWinner------------------------------------------------------------------
    /* 1) check horizontal winner (player 2 wins)
     * 2) check vertical winner (player 1 wins): testPTGameOver() already tests this
     * 3) check diag up winner (player 1 wins)
     * 4) check diag down winner (player 2 wins)
     * 5) check that returns 0 if no winner: this is tested throughout the tests
     * Note that playturn, checkwinner, and getGameOver are also tested here
     */
	 
	 @Test
	 public void testPTColFull() {
		 Connect4 c = new Connect4("files/connect4Test.txt");
		 c.reset();
		 
		 c.playTurn(4);
		 c.playTurn(4);
		 c.playTurn(4);
		 c.playTurn(4);
		 c.playTurn(4);
		 c.playTurn(4);
		
		 assertFalse(c.playTurn(4));
		 
	 }
	 
	 @Test
	 public void testCheckHorizWinner() {
		 Connect4 c = new Connect4("files/connect4Test.txt");
		 c.reset();

		 c.playTurn(0);
		 c.playTurn(1);
		 
		 c.playTurn(0);
		 c.playTurn(2);
		 
		 assertEquals(0, c.checkWinner());
		 
		 c.playTurn(0);
		 c.playTurn(3);
		 
		 c.playTurn(1);
		 c.playTurn(4);
		 
		 assertEquals(2, c.checkWinner());
		 assertTrue(c.getGameOver());
	 }
	 
	 @Test
	 public void testDiagUpWinner() {
		 Connect4 c = new Connect4("files/connect4Test.txt");
		 c.reset();
		 //player1
		 c.playTurn(0);
		 //player2
		 c.playTurn(1);
		 //p1
		 c.playTurn(1);
		 //p2
		 c.playTurn(2);
		 
		 assertEquals(0, c.checkWinner());
		 
		 //p1
		 c.playTurn(2);
		 //p1
		 c.playTurn(0);
		 //p2
		 c.playTurn(2);
		 //p1
		 c.playTurn(3);
		 c.playTurn(3);
		 c.playTurn(3);
		 c.playTurn(3);
		 
		 assertEquals(1, c.checkWinner());
	 }
	 
	 @Test 
	 public void testDiagDownWinner() {
		 Connect4 c = new Connect4("files/connect4Test.txt");
		 c.reset();
		 
		 assertEquals(0, c.checkWinner());
		 
		 //player1
		 c.playTurn(0);
		 //player2
		 c.playTurn(6);
		 //player1
		 c.playTurn(5);
		 //p2
		 c.playTurn(5);
		 //p1
		 c.playTurn(4);
		 //p2
		 c.playTurn(4);
		 //p1
		 c.playTurn(0);
		 //p2
		 c.playTurn(4);
		 //p1
		 c.playTurn(3);
		 //p2
		 c.playTurn(3);
		 //p1
		 c.playTurn(3);
		 //p2
		 c.playTurn(3);
		 
		 assertEquals(2, c.checkWinner());
	 }
	 
	
	 
	 

    //test save------------------------------------------------------------------------
    /*
     * 1) test that if its saved, a new game is made with the same file, the boards and player1 
     * value is equal
     * 2) test that if a game is saved after someone won, writes an array of 0s 
     */
	 @Test
	 public void testSave() {
		 Connect4 c = new Connect4("files/connect4Test.txt");
		 c.reset();
		 
		 c.playTurn(0);
		 c.playTurn(0);
		 
		 boolean p1 = c.getCurrentPlayer();
		 int[][] board1 = c.getBoard();
		 
		 c.save();
		 
		 Connect4 c2 = new Connect4("files/connect4Test.txt");
		 int[][] board2 = c2.getBoard();
		 
		 assertTrue(arrayEquality(board1, board2));
		 assertEquals(p1, c2.getCurrentPlayer());
	 }
	 
	 @Test
	 public void testSaveGameOver() {
		 Connect4 c = new Connect4("files/connect4Test.txt");
		 c.reset();
		 c.playTurn(0);
		 c.playTurn(1);
		 c.playTurn(0);
		 c.playTurn(1);
		 c.playTurn(0);
		 c.playTurn(1);
		 c.playTurn(0);
		 
		 //this last turn shouldn't affect anything
		 c.playTurn(1);
		 
		 c.save();
		 
		 Connect4 c2 = new Connect4("files/connect4Test.txt");
		 int[][] board2 = c2.getBoard();
		 
		 assertTrue(arrayEquality(tb0, board2));
		 assertTrue(c2.getCurrentPlayer());
	 }
	 

	 
    
    //test getters:---------------------------------------------------------------------
    /*
     * 1) getCurrentPlayer(): already tested
     * 2) getCell(int r, int c) returns right value if valid r and c : already tested
     * 3) getCell(int r, int c) throw IllegalArgumentException
     * 4) getMoves: already tested
     * 6) getGameOver: already tested in playTurnTests and check winner tests
     */
	 
	 @Test
	 public void getCellOOB() {
		 Connect4 c = new Connect4("files/connect4Test.txt");
		 try {
			 c.getCell(12, 0);
		 } catch (IllegalArgumentException e) {
			 return;
		 }
		 fail();
	 }

	 //note that gameboard and action listeners were tested through actually playing the game.
	 //they simply access the model and connect to it, but can't be tested in JUnit testing without
	 //physical mouse clicks
}