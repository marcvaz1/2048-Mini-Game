package org.cis1200.Game2048;

/*
 * CIS 120 HW09 - Game2048 Demo
 * (c) University of Pennsylvania
 * Created by Bayley Tuch, Sabrina Green, and Nicolas Corona in Fall 2020.
 */

import javax.swing.*;
import java.awt.*;

/**
 * This class sets up the top-level frame and widgets for the GUI.
 *
 * This game adheres to a Model-View-Controller design framework. This
 * framework is very effective for turn-based games. We STRONGLY
 * recommend you review these lecture slides, starting at slide 8,
 * for more details on Model-View-Controller:
 * https://www.seas.upenn.edu/~cis120/current/files/slides/lec37.pdf
 *
 * In a Model-View-Controller framework, Game initializes the view,
 * implements a bit of controller functionality through the reset
 * button, and then instantiates a GameBoard. The GameBoard will
 * handle the rest of the game's view and controller functionality, and
 * it will instantiate a Game2048 object to serve as the game's model.
 */
public class RunGame2048 implements Runnable {
    public void run() {
        // NOTE: the 'final' keyword denotes immutability even for local variables.

        // Top-level frame in which game components live
        final JFrame frame = new JFrame("Game2048");
        frame.setLocation(400, 400);

        String instructionString = "In this game you start with two tiles, each of which\n" +
                "can be either a 2 or a 4. You then use the arrow\n" +
                "keys to shift all of the squares in that direction,\n" +
                "combining the adjacent tiles with the same number to\n" +
                "make a tile with the sum of the number on the two\n" +
                "tiles. Each move adds a new tile, and you win when you \n" +
                "reach the tile number 2048.";
        JOptionPane.showMessageDialog(
                null, instructionString, "Instructions", JOptionPane.INFORMATION_MESSAGE
        );

        // Status panel
        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);
        final JLabel status = new JLabel("Setting up...");
        status_panel.add(status);
        status_panel.add(new JLabel("  |  "));
        final JLabel scoreLabel = new JLabel("Score: 0");
        status_panel.add(scoreLabel);

        // Game board
        final GameBoard board = new GameBoard(status, scoreLabel);
        frame.add(board, BorderLayout.CENTER);

        // Reset button
        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.NORTH);

        // Note here that when we add an action listener to the reset button, we
        // define it as an anonymous inner class that is an instance of
        // ActionListener with its actionPerformed() method overridden. When the
        // button is pressed, actionPerformed() will be called.
        final JButton reset = new JButton("Reset");
        reset.addActionListener(e -> board.reset());
        control_panel.add(reset);

        final JButton undo = new JButton("Undo");
        undo.addActionListener(e -> board.undo());
        control_panel.add(undo);

        // Put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Start the game
        board.resumeGame();
    }
}