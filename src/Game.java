/**
 * CIS 120 HW09 - TicTacToe Demo
 * (c) University of Pennsylvania
 * Created by Bayley Tuch, Sabrina Green, and Nicolas Corona in Fall 2020.
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * This class sets up the top-level frame and widgets for the GUI.
 * 
 * This game adheres to a Model-View-Controller design framework.  This
 * framework is very effective for turn-based games.  We STRONGLY 
 * recommend you review these lecture slides, starting at slide 8, 
 * for more details on Model-View-Controller:  
 * https://www.seas.upenn.edu/~cis120/current/files/slides/lec37.pdf
 * 
 * In a Model-View-Controller framework, Game initializes the view,
 * implements a bit of controller functionality through the reset
 * button, and then instantiates a GameBoard.  The GameBoard will
 * handle the rest of the game's view and controller functionality, and
 * it will instantiate a TicTacToe object to serve as the game's model.
 */
public class Game implements Runnable {
    public void run() {
        // NOTE: the 'final' keyword denotes immutability even for local variables.
  
    	
        // Top-level frame in which game components live
        final JFrame frame = new JFrame("Connect4");
        frame.setLocation(300, 300);
        frame.setMinimumSize(new Dimension(700, 685));
        
        final String INSTRUCTIONS = ("How To Play:\nThis is like traditional connect4 but with a "
                + "strategic twist! As usual, you click a column and the colored piece" +
                " slides down to the lowest available row. \nEach player gets one turn" +
                " However, in this game, you can use a turn to undo the most recent turn." 
         + " This may be your opponents or it may be your own. \nThe choice is yours :)" + 
         "\nIt is possible to undo every turn that has been played. \nYou are not able to"
         + " undo a winning turn because that would be no fun :( \nFurthermore, you are" +
         " able to save your game and come back to it later. If you wish to save, simply"
         + "click the save button and close out of the window. \nYour game will be" + 
         " waiting for you when you're ready. \nFinally click reset to start over!" +
         "\nHave fun!");
        JOptionPane.showMessageDialog(frame, INSTRUCTIONS, "Instructions", 
        JOptionPane.PLAIN_MESSAGE);

        // Status panel
        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);
        final JLabel status = new JLabel("Setting up...");
        status_panel.add(status);

        // Game board
        final GameBoard board = new GameBoard(status);
        frame.add(board, BorderLayout.CENTER);

        // Reset button
        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.NORTH);

        final JButton reset = new JButton("Reset");
        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                board.reset();
            }
        });
        
        // Undo Button
        final JButton undo = new JButton("Undo");
        undo.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		board.undo();
        	}
        });
        
        // Save Button
        final JButton save = new JButton("Save");
        save.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		board.save();
        	}
        });
        

        control_panel.add(reset);
        control_panel.add(undo);
        control_panel.add(save);
        
  

        // Put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Start the game
        board.start();
    }

    /**
     * Main method run to start and run the game. Initializes the GUI elements specified in Game and
     * runs it. IMPORTANT: Do NOT delete! You MUST include this in your final submission.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Game());
    }
}