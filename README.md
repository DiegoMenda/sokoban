
## Sokoban: A Classic Puzzle Game in Java

This repository contains the source code for a Sokoban game implemented in Java. Sokoban is a puzzle game where the goal is to move boxes to their target positions using a character.

**Features:**

* **Graphical Interface:** The game features a simple and user-friendly graphical interface.
* **Multiple Levels:** The game includes several levels of increasing difficulty.
* **Game Mechanics:**
    * The player controls a character that can move in four directions.
    * The player can push boxes, but not pull them.
    * The goal is to move all the boxes to their target positions, marked with an asterisk.
* **Scoring System:** The game keeps track of the moves made and the score obtained.
* **Save and Load:** The game allows saving and loading game progress in XML files.
* **Undo Moves:** The player can undo the last moves made.

**How to Play:**

1. **Compile the code:** Use Maven to compile the source code with "`mvn exec:java`"
2. **Run the game:** Execute the `GameController` class to start the game.
3. **Controls:**
    * **W:** Move up
    * **S:** Move down
    * **A:** Move left
    * **D:** Move right
    * **Z:** Undo the last move
4. **Save the game:** Click the "`Save the game`" button to save the game progress.
5. **Load the game:** Click the "`Open a saved game`" button to load a saved game.
6. **Reset Game:** Click the "`Restart level`" button to restart the actual level.
7. **New game:** Click the "`Start a new game`" button to restart the game from the beginning.


**Code Structure:**

* **`controller`:** Contains the game logic and interaction with the graphical interface.
* **`model`:** Contains the classes that represent the game elements, such as the character, boxes, walls, and goals.
* **`view`:** Contains the classes that represent the game's graphical interface.

**Requirements:**

* Java Development Kit (JDK) 1.8 or higher
* Maven

**Installation:**

1. Clone this repository.
2. Run `mvn clean install` to compile the code.
3. Run `mvn exec:java` to start the game.
