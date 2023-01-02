package org.cis1200.Game2048;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * You can use this file (and others) to test your
 * implementation.
 */

public class Game2048Test {

    // Test to see if constructor initializes game properly
    @Test
    public void testConstructor() {
        Game2048 testGame = new Game2048();
        int[][] inputArray = new int[4][4];
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                inputArray[i][j] = 0;
            }
        }
        inputArray[0][0] = 2;
        testGame.setNewGame(inputArray);
        assertEquals(0, testGame.getScore());
        assertEquals(2, testGame.getCell(0, 0));
        assertEquals(1, testGame.getMoves().size());
        assertEquals(1, testGame.getScores().size());
    }

    // Tests to see if the reset function properly resets the game
    @Test
    public void testReset() {
        Game2048 testGame = new Game2048();
        testGame.reset();
        int counter = 0;
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                if (testGame.getCell(i, j) != 0) {
                    assertTrue(testGame.getCell(i, j) == 2 || testGame.getCell(i, j) == 4);
                    ++counter;
                }
            }
        }
        assertEquals(2, counter);
        assertEquals(0, testGame.getScore());
        assertEquals(1, testGame.getMoves().size());
        assertEquals(1, testGame.getScores().size());
    }

    // Tests to see if add random number properly adds a random number
    @Test
    public void testAddRandomNumber() {
        Game2048 testGame = new Game2048();
        testGame.reset();
        testGame.addRandomNumber();
        int counter = 0;
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                if (testGame.getCell(i, j) != 0) {
                    assertTrue(testGame.getCell(i, j) == 2 || testGame.getCell(i, j) == 4);
                    ++counter;
                }
            }
        }
        assertEquals(3, counter);
        assertEquals(0, testGame.getScore());
        assertEquals(1, testGame.getMoves().size());
        assertEquals(1, testGame.getScores().size());
    }

    // Tests to see if move down properly adds a number if the move is valid
    @Test
    public void testMoveDown() {
        Game2048 testGame = new Game2048();
        int[][] inputArray = new int[4][4];
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                inputArray[i][j] = 0;
            }
        }
        inputArray[0][0] = 2;
        testGame.setNewGame(inputArray);
        testGame.down();
        assertEquals(2, testGame.getCell(3, 0));
        int counter = 0;
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                if (testGame.getCell(i, j) != 0) {
                    ++counter;
                }
            }
        }
        assertEquals(2, counter);
        assertEquals(2, testGame.getMoves().size());
        assertEquals(2, testGame.getScores().size());
    }

    // Tests to see if move right properly adds a number if the move is valid
    @Test
    public void testMoveRight() {
        Game2048 testGame = new Game2048();
        int[][] inputArray = new int[4][4];
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                inputArray[i][j] = 0;
            }
        }
        inputArray[0][0] = 2;
        testGame.setNewGame(inputArray);
        testGame.right();
        assertEquals(2, testGame.getCell(0, 3));
        int counter = 0;
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                if (testGame.getCell(i, j) != 0) {
                    ++counter;
                }
            }
        }
        assertEquals(2, counter);
        assertEquals(2, testGame.getMoves().size());
        assertEquals(2, testGame.getScores().size());
    }

    // Tests to see if move up properly adds a number if the move is valid
    @Test
    public void testMoveUp() {
        Game2048 testGame = new Game2048();
        int[][] inputArray = new int[4][4];
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                inputArray[i][j] = 0;
            }
        }
        inputArray[2][2] = 2;
        testGame.setNewGame(inputArray);
        testGame.up();
        assertEquals(2, testGame.getCell(0, 2));
        int counter = 0;
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                if (testGame.getCell(i, j) != 0) {
                    ++counter;
                }
            }
        }
        assertEquals(2, counter);
        assertEquals(2, testGame.getMoves().size());
        assertEquals(2, testGame.getScores().size());
    }

    // Tests to see if move left properly adds a number if the move is valid
    @Test
    public void testMoveLeft() {
        Game2048 testGame = new Game2048();
        int[][] inputArray = new int[4][4];
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                inputArray[i][j] = 0;
            }
        }
        inputArray[2][2] = 2;
        testGame.setNewGame(inputArray);
        testGame.left();
        assertEquals(2, testGame.getCell(2, 0));
        int counter = 0;
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                if (testGame.getCell(i, j) != 0) {
                    ++counter;
                }
            }
        }
        assertEquals(2, counter);
        assertEquals(2, testGame.getMoves().size());
        assertEquals(2, testGame.getScores().size());
    }

    // Tests to make sure that an invalid move doesn't modify the board
    @Test
    public void testMoveDownInvalidMove() {
        Game2048 testGame = new Game2048();
        int[][] inputArray = new int[4][4];
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                inputArray[i][j] = 0;
            }
        }
        inputArray[3][0] = 2;
        testGame.setNewGame(inputArray);
        testGame.down();
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                assertEquals(testGame.getCell(i, j), testGame.getMoves().peekLast()[i][j]);
            }
        }
        assertEquals(0, testGame.getScore());
        assertEquals(1, testGame.getMoves().size());
        assertEquals(1, testGame.getScores().size());
    }

    // Tests to make sure that undo returns the board to its original state
    @Test
    public void testUndo() {
        Game2048 testGame = new Game2048();
        int[][] inputArray = new int[4][4];
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                inputArray[i][j] = 0;
            }
        }
        inputArray[0][0] = 2;
        testGame.setNewGame(inputArray);
        testGame.down();
        assertEquals(2, testGame.getMoves().size());
        assertEquals(2, testGame.getScores().size());
        testGame.right();
        assertEquals(3, testGame.getMoves().size());
        assertEquals(3, testGame.getScores().size());
        testGame.undo();
        testGame.undo();
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                assertEquals(testGame.getCell(i, j), testGame.getMoves().peekLast()[i][j]);
            }
        }
        assertEquals(0, testGame.getScore());
        assertEquals(1, testGame.getMoves().size());
        assertEquals(1, testGame.getScores().size());
    }

    // Test to make sure that the game state is accurately reported
    @Test
    public void testCheckUnfinishedGame() {
        Game2048 testGame = new Game2048();
        int[][] inputArray = new int[4][4];
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                inputArray[i][j] = 0;
            }
        }
        inputArray[0][0] = 2;
        testGame.setNewGame(inputArray);
        assertEquals(2, testGame.checkGameStatus());
    }

    // Tests to make sure game status is correct when game is won
    @Test
    public void testCheckWonGame() {
        Game2048 testGame = new Game2048();
        int[][] inputArray = new int[4][4];
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                inputArray[i][j] = 0;
            }
        }
        inputArray[0][0] = 2048;
        testGame.setNewGame(inputArray);
        assertEquals(1, testGame.checkGameStatus());
    }

    // Tests to make sure game status is correct when game is lost
    @Test
    public void testCheckLostGame() {
        Game2048 testGame = new Game2048();
        int[][] inputArray = new int[4][4];
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                inputArray[i][j] = (i % 2 == j % 2) ? 2 : 4;
            }
        }
        testGame.setNewGame(inputArray);
        testGame.printGameState();
        assertEquals(0, testGame.checkGameStatus());
    }

}
