package org.cis1200.Game2048;

import java.util.*;
import java.nio.*;
import java.io.*;

/**
 * CIS 120 HW09 - Game2048 Demo
 * (c) University of Pennsylvania
 * Created by Bayley Tuch, Sabrina Green, and Nicolas Corona in Fall 2020.
 */

/**
 * This class is a model for Game2048.
 * 
 * This game adheres to a Model-View-Controller design framework.
 * This framework is very effective for turn-based games. We
 * STRONGLY recommend you review these lecture slides, starting at
 * slide 8, for more details on Model-View-Controller:
 * https://www.seas.upenn.edu/~cis120/current/files/slides/lec36.pdf
 * 
 * This model is completely independent of the view and controller.
 * This is in keeping with the concept of modularity! We can play
 * the whole game from start to finish without ever drawing anything
 * on a screen or instantiating a Java Swing object.
 * 
 * Run this file to see the main method play a game of Game2048,
 * visualized with Strings printed to the console.
 */
public class Game2048 {

    private int[][] board;
    private int gameScore;
    private final File saveFile = new File("SaveFile");
    private LinkedList<int[][]> moves;
    private LinkedList<Integer> scores;

    /**
     * Constructor sets up game state.
     */
    public Game2048() {
        board = new int[4][4];
        moves = new LinkedList<>();
        scores = new LinkedList<>();
        resume();
    }

    /**
     * The addRandomNumber method adds a new square onto the board. The square
     * can be either a 2 or a 4 and this is randomly selected. The position of the
     * square is also randomly selected among the empty squares on the board.
     *
     */
    public void addRandomNumber() {
        int counter = 0;
        for (int[] row : board) {
            for (int element : row) {
                if (element == 0) {
                    ++counter;
                }
            }
        }
        if (counter == 0) {
            return;
        }
        int indexToAdd = (int) Math.floor(Math.random() * counter);
        counter = 0;
        int numberToAdd;
        if (Math.round(Math.random() * 10) < 5) {
            numberToAdd = 2;
        } else {
            numberToAdd = 4;
        }
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                if (board[i][j] == 0) {
                    if (counter == indexToAdd) {
                        board[i][j] = numberToAdd;
                        return;
                    }
                    ++counter;
                }
            }
        }
    }

    /**
     * The pause method allows the player to stop the game and save their progress
     * in a file.
     * The method saves the game data into a file that can be accessed later to
     * return to the game.
     *
     */
    private void pause() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(saveFile, false));
            bw.write("" + gameScore);
            bw.newLine();
            for (int[] row : board) {
                for (int element : row) {
                    bw.write(element + " ");
                    bw.flush();
                }
                bw.newLine();
                bw.flush();
            }
            bw.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * The resume method allows the player to continue the game after pausing.
     * The method takes the game data from a save file and reinitializes the board;
     *
     */
    public void resume() {
        String tempLine;
        String[] tempIndexString;
        if (!saveFile.exists()) {
            try {
                saveFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            reset();
        }
        try {
            FileReader fr = new FileReader(saveFile);
            BufferedReader br = new BufferedReader(fr);
            tempLine = br.readLine();
            gameScore = Integer.parseInt(tempLine);
            for (int i = 0; i < 4; ++i) {
                tempLine = br.readLine();
                tempIndexString = tempLine.split(" ");
                for (int j = 0; j < 4; ++j) {
                    board[i][j] = Integer.parseInt(tempIndexString[j]);
                }
            }
            br.close();
            moves.clear();
            scores.clear();
            int[][] newMove = new int[4][4];
            for (int i = 0; i < 4; ++i) {
                System.arraycopy(board[i], 0, newMove[i], 0, 4);
            }
            moves.add(newMove);
            scores.add(getScore());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * The undo method allows the player to return to the previous move.
     * The method uses the moves linked list to find the previous move;
     * It also removes the last step from the linked list so that the last move
     * is "undone"
     *
     */
    public void undo() {
        if (moves.size() > 1) {
            moves.removeLast();
            scores.removeLast();
            int[][] newMove = new int[4][4];
            for (int i = 0; i < 4; ++i) {
                for (int j = 0; j < 4; ++j) {
                    newMove[i][j] = moves.peekLast()[i][j];
                }
            }
            gameScore = scores.peekLast();
            board = newMove;
            pause();
        }
    }

    /**
     * The shiftDown method is a helper function for down. It shifts all of the
     * filled squares as far down as they can go.
     *
     */
    private void shiftDown() {
        int rangeCheck;
        for (int i = 2; i >= 0; --i) {
            for (int j = 0; j < 4; ++j) {
                rangeCheck = i;
                if (board[i][j] != 0) {
                    while (rangeCheck + 1 < 4) {
                        if (board[rangeCheck + 1][j] == 0) {
                            board[rangeCheck + 1][j] = board[rangeCheck][j];
                            board[rangeCheck][j] = 0;
                        }
                        ++rangeCheck;
                    }
                }
            }
        }
    }

    /**
     * The down method allows the player to make their move by pressing the down
     * key.
     * The method shifts all the tiles as far down as they can go and combines any
     * tiles that are the same number.
     *
     */
    public void down() {
        shiftDown();
        for (int i = 3; i > 0; --i) {
            for (int j = 0; j < 4; ++j) {
                if (board[i][j] == board[i - 1][j]) {
                    gameScore += 2 * board[i][j];
                    board[i][j] = 2 * board[i][j];
                    board[i - 1][j] = 0;
                }
            }
        }
        shiftDown();
        boolean isValidMove = false;

        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                if (board[i][j] != moves.peekLast()[i][j]) {
                    isValidMove = true;
                    break;
                }
            }
        }
        if (isValidMove) {
            addRandomNumber();
            int[][] newMove = new int[4][4];
            for (int i = 0; i < 4; ++i) {
                for (int j = 0; j < 4; ++j) {
                    newMove[i][j] = board[i][j];
                }
            }
            moves.add(newMove);
            scores.add(gameScore);
            pause();
        }

    }

    /**
     * The shiftRight method is a helper function for right. It shifts all of the
     * filled squares as far to the right as they can go.
     *
     */
    private void shiftRight() {
        int rangeCheck;
        for (int j = 2; j >= 0; --j) {
            for (int i = 0; i < 4; ++i) {
                rangeCheck = j;
                if (board[i][j] != 0) {
                    while (rangeCheck + 1 < 4) {
                        if (board[i][rangeCheck + 1] == 0) {
                            board[i][rangeCheck + 1] = board[i][rangeCheck];
                            board[i][rangeCheck] = 0;
                        }
                        ++rangeCheck;
                    }
                }
            }
        }
    }

    /**
     * The right method allows the player to make their move by pressing the right
     * key.
     * The method shifts all the tiles as far to the right as they can go and
     * combines any
     * tiles that are the same number.
     *
     */
    public void right() {
        shiftRight();
        for (int j = 3; j > 0; --j) {
            for (int i = 0; i < 4; ++i) {
                if (board[i][j] == board[i][j - 1]) {
                    gameScore += 2 * board[i][j];
                    board[i][j] = 2 * board[i][j];
                    board[i][j - 1] = 0;
                }
            }
        }
        shiftRight();
        boolean isValidMove = false;

        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                if (board[i][j] != moves.peekLast()[i][j]) {
                    isValidMove = true;
                    break;
                }
            }
        }
        if (isValidMove) {
            addRandomNumber();
            int[][] newMove = new int[4][4];
            for (int i = 0; i < 4; ++i) {
                for (int j = 0; j < 4; ++j) {
                    newMove[i][j] = board[i][j];
                }
            }
            moves.add(newMove);
            scores.add(gameScore);
            pause();
        }
    }

    /**
     * The shiftLeft method is a helper function for left. It shifts all of the
     * filled squares as far to the left as they can go.
     *
     */
    private void shiftLeft() {
        int rangeCheck;
        for (int j = 1; j < 4; ++j) {
            for (int i = 0; i < 4; ++i) {
                rangeCheck = j;
                if (board[i][j] != 0) {
                    while (rangeCheck - 1 >= 0) {
                        if (board[i][rangeCheck - 1] == 0) {
                            board[i][rangeCheck - 1] = board[i][rangeCheck];
                            board[i][rangeCheck] = 0;
                        }
                        --rangeCheck;
                    }
                }
            }
        }
    }

    /**
     * The left method allows the player to make their move by pressing the left
     * key.
     * The method shifts all the tiles as far to the left as they can go and
     * combines any
     * tiles that are the same number.
     *
     */
    public void left() {
        shiftLeft();
        for (int j = 0; j < 3; ++j) {
            for (int i = 0; i < 4; ++i) {
                if (board[i][j] == board[i][j + 1]) {
                    gameScore += 2 * board[i][j];
                    board[i][j] = 2 * board[i][j];
                    board[i][j + 1] = 0;
                }
            }
        }
        shiftLeft();
        boolean isValidMove = false;

        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                if (board[i][j] != moves.peekLast()[i][j]) {
                    isValidMove = true;
                    break;
                }
            }
        }
        if (isValidMove) {
            addRandomNumber();
            int[][] newMove = new int[4][4];
            for (int i = 0; i < 4; ++i) {
                for (int j = 0; j < 4; ++j) {
                    newMove[i][j] = board[i][j];
                }
            }
            moves.add(newMove);
            scores.add(gameScore);
            pause();
        }
    }

    /**
     * The shiftUp method is a helper function for up. It shifts all of the
     * filled squares as far up as they can go.
     *
     */
    private void shiftUp() {
        int rangeCheck;
        for (int i = 1; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                rangeCheck = i;
                if (board[i][j] != 0) {
                    while (rangeCheck - 1 >= 0) {
                        if (board[rangeCheck - 1][j] == 0) {
                            board[rangeCheck - 1][j] = board[rangeCheck][j];
                            board[rangeCheck][j] = 0;
                        }
                        --rangeCheck;
                    }
                }
            }
        }
    }

    /**
     * The left method allows the player to make their move by pressing the left
     * key.
     * The method shifts all the tiles as far to the left as they can go and
     * combines any
     * tiles that are the same number.
     *
     */
    public void up() {
        shiftUp();
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 4; ++j) {
                if (board[i][j] == board[i + 1][j]) {
                    gameScore += 2 * board[i][j];
                    board[i][j] = 2 * board[i][j];
                    board[i + 1][j] = 0;
                }
            }
        }
        shiftUp();
        boolean isValidMove = false;

        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                if (board[i][j] != moves.peekLast()[i][j]) {
                    isValidMove = true;
                    break;
                }
            }
        }
        if (isValidMove) {
            addRandomNumber();
            int[][] newMove = new int[4][4];
            for (int i = 0; i < 4; ++i) {
                for (int j = 0; j < 4; ++j) {
                    newMove[i][j] = board[i][j];
                }
            }
            moves.add(newMove);
            scores.add(gameScore);
            pause();
        }
    }

    /**
     * checkWinner checks whether the game has reached a win condition.
     * checkWinner looks for if the number 2048 is present or if the game has ended
     *
     * @return 0 if the game is lost, 1 if the game is won, and 2 if the game is not
     *         over
     */
    public int checkGameStatus() {
        boolean finished = true;
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                if (board[i][j] == 2048) {
                    return 1;
                }
                if (board[i][j] == 0) {
                    finished = false;
                }
                if (i < 3) {
                    if (board[i][j] == board[i + 1][j]) {
                        finished = false;
                    }
                }
                if (i > 0) {
                    if (board[i][j] == board[i - 1][j]) {
                        finished = false;
                    }
                }
                if (j < 3) {
                    if (board[i][j] == board[i][j + 1]) {
                        finished = false;
                    }
                }
                if (j > 0) {
                    if (board[i][j] == board[i][j - 1]) {
                        finished = false;
                    }
                }
            }
        }
        if (finished) {
            return 0;
        } else {
            return 2;
        }
    }

    /**
     * printGameState prints the current game state
     * for debugging.
     */
    public void printGameState() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.print(board[i][j]);
                if (j < 3) {
                    System.out.print(" | ");
                }
            }
            if (i < 3) {
                System.out.println("\n----------------");
            }
        }
        System.out.println(" ");
        if (checkGameStatus() == 1) {
            System.out.println("Winner!");
        } else if (checkGameStatus() == 0) {
            System.out.println("Loser!");
        } else {
            System.out.println("Game Not Over");
        }
    }

    /**
     * reset (re-)sets the game state to start a new game.
     */
    public void reset() {
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                board[i][j] = 0;
            }
        }
        addRandomNumber();
        addRandomNumber();
        pause();
        int[][] newMove = new int[4][4];
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                newMove[i][j] = board[i][j];
            }
        }
        gameScore = 0;
        moves.clear();
        moves.add(newMove);
        scores.clear();
        scores.add(0);
        pause();
    }

    /**
     * Getter method for all private variables; all used for testing
     */

    public int getCell(int x, int y) {
        return board[x][y];
    }

    public int getScore() {
        return gameScore;
    }

    public void setNewGame(int[][] inpBoard) {
        board = inpBoard;
        moves.clear();
        int[][] newMove = new int[4][4];
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                newMove[i][j] = inpBoard[i][j];
            }
        }
        moves.add(newMove);
        gameScore = 0;
        scores.clear();
        scores.add(0);
        pause();
    }

    public LinkedList<Integer> getScores() {
        return scores;
    }

    public LinkedList<int[][]> getMoves() {
        return moves;
    }

    /**
     * This main method illustrates how the model is completely independent of
     * the view and controller. We can play the game from start to finish
     * without ever creating a Java Swing object.
     *
     * This is modularity in action, and modularity is the bedrock of the
     * Model-View-Controller design framework.
     *
     * Run this file to see the output of this method in your console.
     */
    public static void main(String[] args) {
        Game2048 t = new Game2048();
        t.printGameState();
        while (t.checkGameStatus() == 2) {
            t.down();
            t.printGameState();

            t.right();
            t.printGameState();

            t.left();
            t.printGameState();

            t.up();
            t.printGameState();
        }

        System.out.println();
        System.out.println();
    }
}
