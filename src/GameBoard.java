

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class GameBoard extends JPanel {

    private Connect4 c; // model for the game
    private JLabel status; // current status text

    // Game constants
    public static final int BOARD_WIDTH = 700;
    public static final int BOARD_HEIGHT = 600;
    
    public static final Color P1COLOR = Color.BLUE;
    public static final Color P2COLOR = Color.RED;

    /**
     * Initializes the game board.
     */
    public GameBoard(JLabel statusInit) {
        // creates border around the game area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Enable keyboard focus on the court area.
        // When this component has the keyboard focus, key events are handled by its key listener.
        setFocusable(true);
               
        c = new Connect4("files/connect4Test.txt"); // initializes model for the game
        status = statusInit; // initializes the status JLabel

        /**
         * Listens for mouseclicks.  Updates the model, then updates the game board
         * based off of the updated model.
         */
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Point p = e.getPoint();
                
                // updates the model given the coordinates of the mouseclick
                c.playTurn(p.x / 100);
                
                //updates the status JLabel
                updateStatus(); 
                
                //repaints the game board
                repaint(); 
            }
        });
    }

    /**
     * (Re-)sets the game to its initial state.
     */
    public void reset() {
        c.reset();
        status.setText("Player 1's Turn");
        repaint();

        requestFocusInWindow();
    }
    
    public void start() {
    	c.start();

    	if(c.checkWinner() == 0) {
    		if(c.getCurrentPlayer()) {
    			status.setText("Player 1's Turn");
    		} else {
    			status.setText("Player 2's Turn");
    		}
    	} 
    	
    	repaint();
    	requestFocusInWindow();
    }
    
    public void undo() {
    	c.undo();
    	
    	if(c.checkWinner() == 0) {
    		if(c.getCurrentPlayer()) {
    			status.setText("Player 1's Turn");
    		} else {
    			status.setText("Player 2's Turn");
    		}
    	} 
    	
    	repaint();
    	requestFocusInWindow();
    }
    
    public void save() {
    	c.save();
    }
    

    /**
     * Updates the JLabel to reflect the current state of the game.
     */
    private void updateStatus() {
        if (c.getCurrentPlayer()) {
            status.setText("Player 1's Turn");
        } else {
            status.setText("Player 2's Turn");
        }
        
        
        int winner = c.checkWinner();
        if (winner == 1) {
            status.setText("Player 1 wins!!!");
        } else if (winner == 2) {
            status.setText("Player 2 wins!!!");
        } 
       
    }
    
    /**
     * Draws the game board.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // Draws board grid
        g.drawLine(0, 0, 0, 600);
        g.drawLine(0, 0, 700, 0);
        g.drawLine(0, 600, 700, 600);
        g.drawLine(700,  600, 700, 0);
        
        g.drawLine(100, 0, 100, 600);
        g.drawLine(200, 0, 200, 600);
        g.drawLine(300, 0, 300, 600);
        g.drawLine(400, 0, 400, 600);
        g.drawLine(500, 0, 500, 600);
        g.drawLine(600, 0, 600, 600);
        
        g.drawLine(0, 100, 700, 100);
        g.drawLine(0, 200, 700, 200);
        g.drawLine(0, 300, 700, 300);
        g.drawLine(0, 400, 700, 400);
        g.drawLine(0, 500, 700, 500);
        
        // Draws X's and O's
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                int state = c.getCell(i, j);
                if (state == 1) {
                	g.setColor(P1COLOR);
                	g.fillOval(30 + 100 * j, 30 + 100 * i, 40, 40);
                } else if (state == 2) {
                	g.setColor(P2COLOR);
                	g.fillOval(30 + 100 * j, 30 + 100 * i, 40, 40);
                } 
            }
        }
    }

    /**
     * Returns the size of the game board.
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
    }
}