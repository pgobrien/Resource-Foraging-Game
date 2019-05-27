ASSIGNMENT 4 README

Team 03: Yellow
Members:
Holly Zhang (zhanghol)
Patrick O'Brien (pgobiren)
Jacob Beauchamp (jacobeau)


Startup:
--------
To start the game:

1. In the commandline the model must be started first:
[username]$ java -Djava.security.policy=permit Model

2. Then input an integer 1 - 99

Board is initiated with two shrouds of resource. First with input, Second with 100 - input.

3. On another terminal instance a client must be created (up to four clients can connect):
[username]$ java Runner

-------------------------
Changes From A03 Program:
-------------------------

Display of Resources:
---------------------
Originally we had no resources in a cell display as white. When a resource was grown in a cell, it would get darker in red.
However, in a04 we changed it so that if there was 1 resource the cell would be displayed as black. Then from the black color 
it would gradually become more red a more resources grew. We changed it to this color scheme so it would be easier to detect 
when a resource was grown because the light red from a03 was diffifult to see with a white background. Included SimpleFactory Pattern.

Inclusion of GameController Class:
----------------------------------
This class controls the creation of the two shrouds of resources based on player input for n%/100-n%

Player Resource Interaction:
----------------------------
In order for a player to get resources, the player must press the key Space key to eat/pick up the resource. Our design choice for
player interaction reflects this. Multiple players may enter the same cell. Whichever player presses the space key firsts eats the
resource. This also ensures that players cannot stand on a cell and wait for resources to grow for collection.

Player Player Interaction:
--------------------------
Players can go in the same cell and see each other. Since resource collection is based on pressing the space bar, there is no need to 
block another player from entering a cell.

Game Over:
After 7 minutes the game stop updating the board and print "Game Over"

Player Controls (using keys):
----------------------------
LEFT, RIGHT, UP, DOWN = Arrow Keys

EAT RESOURCES = Space key

CHECK PLAYER SCORES = S key

-----------------------------
Changes From A03 UML Diagram:
-----------------------------
The most notable changes from the previous UML Diagram is the inclusion of two new classes called GameController and SimpleCellFactory.
The other change made was the data type for the cells instance variable in the Grid, View, and Model classes. Originally cells was 
represented as a 2D array of integers. We changed it into a 2D array of Cell objects since it would allow us to access other information
from the cell such as the number of resources in it and its position in the game. Furthermore, the previous group was converting cells
from int[][] to Cell[][] which caused the program to slow down. Since they were mostly converting int[][] to Cell[][], we decided it
would be better to just start out with Cell[][] and directly work from there.

---------------------
Design Patterns Used:
---------------------

=============
Proxy Pattern
=============
- For use to connect Model to CV
- Implemented with Java RMI through the ModelInterface, Model, and Runner class 

======================
Simple Factory Pattern
======================
- Used for creation of the Cell class as the grid is being populated
- Implemented through the GameController, SimpleCellFactory, and Cell classes

=================
Observer Pattern:
=================
- Used to communicate from Controller to View for updating Cell grid and player information.
- Used to communicate keystrokes to model using Swing built in Listeners
- The KeyListener is implemented in the Grid class

==================
Singleton Pattern:
==================
- Used to maintain a single instance of the Model to prevent multiple grids and update conflicts

