# conway-game-of-life

This application allows you to play with the **Game of life of Conway**. It is writen in Scala and Swing.

### Release 1.0.x
* **How to run**
  * **Run from sources**
    To run from sources you need *SBT*. Just go to the main directory and execute the code with SBT:
    
    `sbt run`
    
    All the dependencies will be downloaded and the application will be launched.

  * **Run from executable .jar**
    An executable .jar file is generated according the version of the code. In the [dist](https://github.com/juanitodread/conway-game-of-life/tree/master/dist) directory you can find all the jars generated according to the code level. 

    You just need to run as a common Java Jar.
    
    `java -jar <conways-game-of-life_[version].jar>`
    
    The .jar file includes all the dependencies.

* **How to use**
  The application has a main grid of cells. The cells can have two states alive or dead. You can change their state clicking in the cell.

  When you have a pattern you can start the generations of the cells. You can check the rules for alive or dead of the cells [here](https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life).

  The *Start* button will run the process to start the generation process. This process will stop when the user clicks on the *Stop* button.

  The *Clear* button is just to initialize the grid with all cells in dead state.
