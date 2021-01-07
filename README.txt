===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an appropriate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

  1. 2d arrays: I used 2d arrays to keep track of game board. This was the appropriate use
  of a 2d array because in the array a 0 was an empty slot, a 1 represented a player1 slot, and a
  2 represented a player2 slot. Because the size of connect4 game board is always 6 rows
  by 7 columns, an array was appropriate. 

  2. Collections: I used a linkedlist to store a move object, which I describe below. It is correct
  to us a linkedlist here because order mattered and I wanted to keep track of the order in which
  moves were made. However, it was also correct to use a linkedlist because I didn't waste time
  going through the list. I only ever access the head of the list, which makes it an efficient
  collection to use. I used collections in the undo feature in my game.

  3. File I/O: I used file I/O to add a save feature to my game. I stored two pieces of game state,
  the game board, and whose turn it was. I did not save the list of moves because if two players
  came back they probably wouldn't even remember which turn was made last! I read in from the file
  when reading in a saved game and wrote when the save button was clicked. I actually changed
  what I saved in my file after my feedback. I planned on saving a highscore, which was the lowest
  number of turns a player won in, and their username. However, as I was planning my game, 
  it seemed more applicable to be able to save a game board and the player's turn it was.

  4. J-Unit testing: I used junit testing to test the main state of my game. I tested fileReader
  too, but more so to debug than to fulfill this concept. I fulfilled this concep tin GameTest. 
  I tested the main game state here. I tested my linkedlist of moves, and also whether or not
  a move changed the 2d array game board in the state of my game. 

=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.
  
  move: the move class creates a "move" object. Each move object stores 2 integers and a boolean
  value. I created this class so that I could keep a linkedlist of the moves that were made
  in the game. If the boolean value is true, then it is player1's turns next, if it is false,
  then its player2's turn next. One integer, row, is the row in the 2d array that the move was 
  made in and the
  other integer, column, is the column in the 2d array that the move was made in. This made it easy
  to keep track of where the move occured, and simplified the undo function.
  
  fileReader: this class reads and writes to a file and keeps track of game state. It keeps track
  of which players turn it is and what the gameboard looks like. If its player 2's piece, the 
  2d array is a 2. If its player 1's piece in the 2d array its a 1, and if its a 0, no one has 
  played there yet. Each fileReader has a filename, 
  which is really a filepath, a BufferedReader and BufferedWriter, an int player, and an 
  int[][] board. I hardcoded the filepath because of the invariants of the file structure.
  Thus, I didn't want users to be able to put their own file into the
  game. While I hardcoded the value of the variable, it would be easy to switch the filepath to
  a different filepath in one move. The fileReader class is structured such that the first line of 
  the txt file is an integer. If it is 2, its player 2's turn, and if its ANY other value or
  empty its player 1's turn. This is represented by the int player. The next line has 7 characters
  that are either 0s, 1s, or 2s. fileReader stores the gamestate when saved and reads from the 
  file to give the 2d array to the connect4 class. If the file is empty and the game has never
  been played, it gives an array of 0s and its player 1s turn. If the file isn't formatted 
  correctly it does the same.
  
  connect4: connect4 is the main state class. It keeps track of the board, who's turn it is, 
  whether the game is over, the LinkedList of moves, and the filepath to the file that stores
  the game state when saved or started. It is the model in the MVC model.
  
  GameBoard: gameboard is part of the view in the MVC model. It paints the model.
  
  Game: game is the controller. It adds mouseclicks and listeners and tells the gameboard how to 
  interact with the model. 
  
