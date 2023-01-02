package org.cis1200.Game2048;

/*
 * CIS 120 HW09 - Game2048 Demo
 * (c) University of Pennsylvania
 * Created by Bayley Tuch, Sabrina Green, and Nicolas Corona in Fall 2020.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * This class instantiates a Game2048 object, which is the model for the game.
 * As the user clicks the game board, the model is updated. Whenever the model
 * is updated, the game board repaints itself and updates its status JLabel to
 * reflect the current state of the model.
 *
 * This game adheres to a Model-View-Controller design framework. This
 * framework is very effective for turn-based games. We STRONGLY
 * recommend you review these lecture slides, starting at slide 8,
 * for more details on Model-View-Controller:
 * https://www.seas.upenn.edu/~cis120/current/files/slides/lec37.pdf
 *
 * In a Model-View-Controller framework, GameBoard stores the model as a field
 * and acts as both the controller (with a MouseListener) and the view (with
 * its paintComponent method and the status JLabel).
 */
@SuppressWarnings("serial")
public class GameBoard extends JPanel {

    private Game2048 game; // model for the game
    private JLabel status; // current game status text

    private JLabel scoreLabel; // current score text
    // Game constants
    public static final int BOARD_WIDTH = 400;
    public static final int BOARD_HEIGHT = 400;

    /**
     * Initializes the game board.
     */
    public GameBoard(JLabel statusInit, JLabel scoreInit) {
        // creates border around the court area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Enable keyboard focus on the court area. When this component has the
        // keyboard focus, key events are handled by its key listener.
        setFocusable(true);

        game = new Game2048(); // initializes model for the game
        status = statusInit; // initializes the status JLabel
        scoreLabel = scoreInit; // initializes the score JLabel

        /*
         * Listens for key presses. Updates the model, then updates the game
         * board based off of the updated model.
         */
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    game.down();
                } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                    game.up();
                } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    game.left();
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    game.right();
                }
                scoreLabel.setText("Score: " + game.getScore());
                updateStatus(); // updates the status JLabel
                repaint(); // repaints the game board
            }
        });
    }

    /**
     * (Re-)sets the game to its initial state.
     */
    public void reset() {
        game.reset();
        game.resume();
        status.setText("Game is Running");
        scoreLabel.setText("Score: " + game.getScore());
        repaint();

        // Makes sure this component has keyboard/mouse focus
        requestFocusInWindow();
    }

    /**
     * Updates the JLabel to reflect the current state of the game.
     */
    private void updateStatus() {
        int gameStatus = game.checkGameStatus();
        if (gameStatus == 1) {
            status.setText("You Win");
        } else if (gameStatus == 0) {
            status.setText("You Lose");
        } else {
            status.setText("Game is Running");
        }
    }

    public void resumeGame() {
        game.resume();
        updateStatus();
        scoreLabel.setText("Score: " + game.getScore());
        repaint();
        requestFocusInWindow();
    }

    public void undo() {
        game.undo();
        updateStatus();
        scoreLabel.setText("Score: " + game.getScore());
        repaint();
        requestFocusInWindow();
    }

    /**
     * Draws the game board.
     *
     * There are many ways to draw a game board. This approach
     * will not be sufficient for most games, because it is not
     * modular. All of the logic for drawing the game board is
     * in this method, and it does not take advantage of helper
     * methods. Consider breaking up your paintComponent logic
     * into multiple methods or classes, like Mushroom of Doom.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draws board grid
        g.setColor(Color.BLACK);
        g.drawLine(0, 0, 0, 400);
        g.drawLine(0, 0, 400, 0);
        g.drawLine(400, 400, 0, 400);
        g.drawLine(400, 400, 400, 0);

        // Draws the squares in the board grid
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int state = game.getCell(i, j);
                String stateLabel = "" + state;
                if (state == 0) {
                    g.setColor(new Color(235, 232, 228));
                } else if (state == 2) {
                    g.setColor(new Color(237, 223, 204));
                } else if (state == 4) {
                    g.setColor(new Color(237, 213, 180));
                } else if (state == 8) {
                    g.setColor(new Color(235, 181, 108));
                } else if (state == 16) {
                    g.setColor(new Color(240, 138, 74));
                } else if (state == 32) {
                    g.setColor(new Color(250, 127, 105));
                } else if (state == 64) {
                    g.setColor(new Color(217, 68, 41));
                } else if (state == 128) {
                    g.setColor(new Color(255, 240, 102));
                } else if (state == 256) {
                    g.setColor(new Color(240, 210, 90));
                } else if (state == 512) {
                    g.setColor(new Color(227, 185, 34));
                } else if (state == 1024) {
                    g.setColor(new Color(226, 180, 15));
                } else if (state == 2048) {
                    g.setColor(new Color(224, 173, 4));
                }
                g.fillRect(100 * j, 100 * i, 90, 90);
                g.setColor(Color.BLACK);
                if (state != 0) {
                    g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 24));
                    g.drawString(stateLabel, 100 * j + 34, 100 * i + 47);
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
