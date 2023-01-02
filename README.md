=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 1200 Game Project README
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=


=: Core Concepts :=


  1. 2D Arrays. I use 2D arrays to make the game board. 2048 is played on a 4x4 game board,
     so I use an integer 2D array to store the values at each index of the board. The 2D array
     stores integers that represent the values at that index on the board. The 2D arrays are also
     stored in a linked list to keep track of all past moves.


  2. Collections. I use two linked lists in my implementation: one to keep track of the moves that
     are made and one to keep track of the score after each move. It is appropriate to use a linked
     list here so that the user can easily undo their moves and retrace their steps. Each time the user
     hits the undo button, the last element of the linked lists are removed and the current state of the
     board and the current game score is changed to the previous values.

  3. File I/O. I use File I/O to pause and resume the game. Every time a move is made, the state of the board
     and the score are written to the save file. Then, when the user leaves the game and returns, the save file
     contains the most recent move, which is then read and allows the game to resume from where it left off.


  4. JUnit Testing. I test that the board is updated correctly, that a move in a specific direction orients the
     blocks in the desired position, that the game ends when the board becomes filled, and that my undo and reset
     functions behave appropriately. This allowed me to troubleshoot and also make sure that there were no bugs in
     my code.


=: My Implementation :=


  I have three classes, Game2048, GameBoard, and RunGame2048. Game2048 contains the bulk of my functionality.
  I have private variables including the board, the score, a collection of the past moves, and a collection
  of the past scores. I have functions that make moves on the board, which modifies the board, the score, and both
  collections. I also have functions that undo, reset the board, read from and write to a file to save the game state,
  and appropriately updates the game state. Then, in my GameBoard class, I create my actual board that shows the game
  being played. It updates and displays the status and the score. It also creates the visuals for the board and has
  functions that modify the board with key presses. The RunGame2048 class creates the instruction window and creates
  the actual window for the game. It is where I display the status and score, along with where I have the buttons
  to undo and reset the game.

  When I was implementing my game, I struggled with encapsulating the undo function. I would find that my
  moves linked list would improperly store the data, and it took me a long time to realize that when I inserted
  boards into the linked list of boards, I had to make a copy of the board instead of directly inserting the board
  into the linked list. Along with that, I had an issue where invalid moves led to new numbers being added to the
  board. For examples, if all the squares were against the bottom floor and the player clicked the down key, the
  squares wouldn't move but a new square would be added to the board, which is not how the game is meant to be played.
  I also had an issue with the random function, which led to tiles sometimes not being added to the board. I was able
  to fix this by using math.floor, which helped me better control the range of values.

    My design does a good job of encapsulating the private state. You are not able to modify the values of the board
    except for within my testing. Whenever I store the board state, I always make a copy of it and simply assign the
    values over instead of referencing the object. I separated my game so that I have one class which controls most of
    the internal algorithmic changes, one class that creates the board to interact with the user, and one class that
    displays the entire product to the user. I think that the functionality is well separated among the different
    classes. If given the chance, I would like to make the user interface a bit more formal and realistic. The visual
    aspect of my game, while pretty similar to the actual game, could use some improvements, such as more accurate
    colors and font.


=: External Resources :=


https://docs.oracle.com/javase/7/docs/api/javax/swing/JOptionPane.html
