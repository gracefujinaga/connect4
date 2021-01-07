/**
 * @author gracefujinaga
 *
 * This class will allow the game board to be saved and store whose turn it is
 */

import java.io.*;

public class FileReaderWriter {
	
	private String filename;
	private BufferedReader r;
	private BufferedWriter w;
	private int player;
	private int[][] board;
	
	/**
	 * Constructs a fileReader with the specified filepath
	 * throws illegal argument exception if the path is null
	 * reads in a file whos first character is 1 if its player 1's turn, 2 if player's 2 turn
	 * if the file is in the wrong format, it defaults to it being player 1's turn and an
	 * empty array
	 * @param filename
	 */
	public FileReaderWriter (String filename) {
		this.filename = filename;
		
		if(filename == null) {
    		throw new IllegalArgumentException("filePath is null");
    	}
		
		try {
			board = new int[6][7];
			Reader reader = new FileReader(this.filename);
			this.r = new BufferedReader(reader);
			
			String str = r.readLine();
			if(str == null || str.isEmpty()) {
				player = 1;
			} else {
				try { 
					player = Integer.parseInt(str);
				} catch (NumberFormatException e) {
					player = 1;
				}
			}
			if(!(player == 1 || player == 2)) {
				player = 1;
			}
			
			
			
			for(int i = 0; i < 6; i++) {
				String line = r.readLine();
				if(line == null || line.length() != 7) {
					continue;
				} else { 
					char[] vals = line.toCharArray();
					for(int j = 0; j < 7; j++) {
						int val = vals[j] % 48;
						if(!(val == 1 || val == 2)) {
							board[i][j] = 0;
						} else {
							board[i][j] = val;
						}
					}
				}
			}
			r.close();
		} catch (IOException e) {
			//if there is an IO exception, just make it an array of 0s
			board = new int[6][7];
			throw new IllegalArgumentException("File not found");
		}
    	
	}
	
	public int[][] getBoard() {
		return board; 
	}
	
	public int getPlayer() {
		return player;
	}
	
	public void writeWinner(int[][] playerboard, int val) {
		try {
			FileWriter fw = new FileWriter(this.filename, false);
	    	this.w = new BufferedWriter(fw);
	    	if(!(val == 1 || val == 2)) {
	    		val = 1;
	    	}
	    	w.write(Integer.toString(val));
	    	w.newLine();
	    	for(int i = 0; i < 6; i ++) {
	    		for(int j = 0; j< 7; j++) {
	    			String str = Integer.toString(playerboard[i][j]);
	    			w.write(str);
	    		}
	    		w.newLine();
	    	}
	    	w.close();
	    } catch (IOException e){
	    	System.out.println("Couldn't handle writing");
	    }
	    }
	
}
	



