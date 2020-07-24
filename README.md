# Games:

## 2D-Tic Tac Toe
```
/**
 * Represents a game state with a 4x4 board
 *
 * Cells are numbered as follows:
 *
 *    col 0  1  2  3  
 * row  ---------------
 *  0  |  0  1  2  3  |  0
 *  1  |  4  5  6  7  |  1
 *  2  |  8  9  10 11 |  2
 *  3  | 12  13 14 15 |  3
 *      ---------------
 *        0  1  2  3
 
 *
 * The staring board looks like this:
 *
 *    col 0  1  2  3  
 * row  ---------------
 *  0  |  .  .  .  .  |  0
 *  1  |  .  .  .  .  |  1
 *  2  |  .  .  .  .  |  2
 *  3  |  .  .  .  .  |  3
 *      ---------------
 *        0  1  2  3
 * 
 * O moves first.
 */
 ```
 

## 3D-Tic Tac Toe

```
/**
 * Represents a game state with a 4x4 board
 *
 * Cells are numbered as follows:
 *			lay 0						lay 1						lay 2						lay 3			
 *    col 0  1  2  3  		    col 0  1  2  3  		    col 0  1  2  3  		    col 0  1  2  3  		
 * row  ---------------		 row  ---------------		 row  ---------------		 row  ---------------		
 *  0  |  0  1  2  3  |  0	  0  | 16  17 18 19 |  0	  0  | 32  33 34 35 |  0	  0  | 48  49 50 51 |  0	
 *  1  |  4  5  6  7  |  1	  1  | 20  21 22 23 |  1	  1  | 36  37 38 39 |  1	  1  | 52  53 54 55 |  1	
 *  2  |  8  9  10 11 |  2	  2  | 24  25 26 27 |  2	  2  | 40  41 42 43 |  2	  2  | 56  57 58 59 |  2	
 *  3  | 12  13 14 15 |  3	  3  | 28  29 30 31 |  3	  3  | 44  45 46 47 |  3	  3  | 60  61 62 63 |  3	
 *      ---------------		      ---------------		      ---------------		      ---------------		
 *        0  1  2  3		        0  1  2  3		            0  1  2  3		            0  1  2  3		    
 
 *
 * The staring board looks like this:
 *			lay 0           			lay 1						lay 2						lay 3
 *    col 0  1  2  3            col 0  1  2  3              col 0  1  2  3              col 0  1  2  3  
 * row  ---------------      row  ---------------        row  ---------------        row  ---------------
 *  0  |  .  .  .  .  |  0    0  |  .  .  .  .  |  0      0  |  .  .  .  .  |  0      0  |  .  .  .  .  |  0
 *  1  |  .  .  .  .  |  1	  1  |  .  .  .  .  |  1	  1  |  .  .  .  .  |  1	  1  |  .  .  .  .  |  1	
 *  2  |  .  .  .  .  |  2    2  |  .  .  .  .  |  2      2  |  .  .  .  .  |  2      2  |  .  .  .  .  |  2
 *  3  |  .  .  .  .  |  3    3  |  .  .  .  .  |  3      3  |  .  .  .  .  |  3      3  |  .  .  .  .  |  3
 *      ---------------           ---------------             ---------------             ---------------
 *        0  1  2  3                0  1  2  3                  0  1  2  3                  0  1  2  3
 * 
 * O moves first.
 */
 ```

## Checkers:



> Describe the possible states, initial state, transition function.

**agents** : Black agent, White agent.

**Possible States**: Any 8 x 8 checkerboard = 64 squares (32 black, 32 white) from where only the 32 black squares are habitable, and there are at most 12 checkers of each color.

**Initial State**: The standard starting board is with 12 checkers of each color positioned in opposite sides of the board.
``` 
/**
 * Represents a game state with a 8x8 board
 *
 * Cells are numbered as follows:
 *
 *    col 0  1  2  3  4  5  6  7
 * row  -------------------------
 *  0  |     0     1     2     3 |  0
 *  1  |  4     5     6     7    |  1
 *  2  |     8     9    10    11 |  2
 *  3  | 12    13    14    15    |  3
 *  4  |    16    17    18    19 |  4
 *  5  | 20    21    22    23    |  5
 *  6  |    24    25    26    27 |  6
 *  7  | 28    29    30    31    |  7
 *      -------------------------
 *        0  1  2  3  4  5  6  7
 *
 * The starting board looks like this:
 *
 *    col 0  1  2  3  4  5  6  7
 * row  -------------------------
 *  0  |    rr    rr    rr    rr |  0
 *  1  | rr    rr    rr    rr    |  1
 *  2  |    rr    rr    rr    rr |  2
 *  3  | ..    ..    ..    ..    |  3
 *  4  |    ..    ..    ..    .. |  4
 *  5  | ww    ww    ww    ww    |  5
 *  6  |    ww    ww    ww    ww |  6
 *  7  | ww    ww    ww    ww    |  7
 *      -------------------------
 *        0  1  2  3  4  5  6  7
 *
 * The red player starts from the top of the board (row 0,1,2)
 * The white player starts from the bottom of the board (row 5,6,7),
 * Red moves first.
 */
 ```
