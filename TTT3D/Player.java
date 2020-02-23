import java.lang.reflect.Array;
import java.util.*;

public class Player {

   private static final int[][] winningCombinations =
           {
                   { 0, 1, 2, 3 },
                   { 4, 5, 6, 7 },
                   { 8, 9, 10, 11 },
                   { 12, 13, 14, 15 },
                   { 16, 17, 18, 19 },
                   { 20, 21, 22, 23 },
                   { 24, 25, 26, 27 },
                   { 28, 29, 30, 31 },
                   { 32, 33, 34, 35 },
                   { 36, 37, 38, 39 },
                   { 40, 41, 42, 43 },
                   { 44, 45, 46, 47 },
                   { 48, 49, 50, 51 },
                   { 52, 53, 54, 55 },
                   { 56, 57, 58, 59 },
                   { 60, 61, 62, 63 },
                   { 0, 4, 8, 12 },
                   { 1, 5, 9, 13 },
                   { 2, 6, 10, 14 },
                   { 3, 7, 11, 15 },
                   { 16, 20, 24, 28 },
                   { 17, 21, 25, 29 },
                   { 18, 22, 26, 30 },
                   { 19, 23, 27, 31 },
                   { 32, 36, 40, 44 },
                   { 33, 37, 41, 45 },
                   { 34, 38, 42, 46 },
                   { 35, 39, 43, 47 },
                   { 48, 52, 56, 60 },
                   { 49, 53, 57, 61 },
                   { 50, 54, 58, 62 },
                   { 51, 55, 59, 63 },
                   { 3, 6, 9, 12 },
                   { 19, 22, 25, 28 },
                   { 35, 38, 41, 44 },
                   { 51, 54, 57, 60 },
                   { 0, 5, 10, 15 },
                   { 16, 21, 26, 31 },
                   { 32, 37, 42, 47 },
                   { 48, 53, 58, 63 },
                   { 0, 17, 34, 51 },
                   { 4, 21, 38, 55 },
                   { 8, 25, 42, 59 },
                   { 12, 29, 46, 63 },
                   { 3, 18, 33, 48 },
                   { 7, 22, 37, 52 },
                   { 11, 26, 41, 56 },
                   { 15, 30, 45, 60 },
                   { 0, 16, 32, 48 },
                   { 1, 17, 33, 49 },
                   { 2, 18, 34, 50 },
                   { 3, 19, 35, 51 },
                   { 4, 20, 36, 52 },
                   { 5, 21, 37, 53 },
                   { 6, 22, 38, 54 },
                   { 7, 23, 39, 55 },
                   { 8, 24, 40, 56 },
                   { 9, 25, 41, 57 },
                   { 10, 26, 42, 58 },
                   { 11, 27, 43, 59 },
                   { 12, 28, 44, 60 },
                   { 13, 29, 45, 61 },
                   { 14, 30, 46, 62 },
                   { 15, 31, 47, 63 },
                   { 0, 20, 40, 60 },
                   { 1, 21, 41, 61 },
                   { 2, 22, 42, 62 },
                   { 3, 23, 43, 63 },
                   { 12, 24, 36, 48 },
                   { 13, 25, 37, 49 },
                   { 14, 26, 38, 50 },
                   { 15, 27, 39, 51 },
                   { 51, 38, 25, 12 },
                   { 48, 37, 26, 15 },
                   { 3, 22, 41, 60 },
                   { 0, 21, 42, 63 },

           };
   private int[][] POINTS = {
           {0,     -10,    -100,   -1000,  -10000},
           {10,      0,       0,       0,       0},
           {100,     0,       0,       0,       0},
           {1000,    0,       0,       0,       0},
           {10000,   0,       0,       0,       0}
   };
   private int MAX;
   private int MIN;

   public GameState play(final GameState gameState, final Deadline deadline) {
      Vector<GameState> nextStates = new Vector<GameState>();
      gameState.findPossibleMoves(nextStates);

      if (nextStates.size() == 0) return new GameState(gameState, new Move());
      if (nextStates.size() == 1) return nextStates.get(0);

      int alpha = Integer.MIN_VALUE;
      int beta = Integer.MAX_VALUE;
      int maxDepth = 1;

      MAX = gameState.getNextPlayer();
      if (MAX != Constants.CELL_X){ MIN = Constants.CELL_X;}
      else{MIN = Constants.CELL_O;};

      List<Integer> bestNextState = alphaBeta(gameState, maxDepth, alpha, beta, MAX);

      return nextStates.get(bestNextState.get(1));
   }

   private List<Integer> alphaBeta(GameState gameState, int depth, int alpha, int beta, int player) {
      Vector<GameState> nextStates = new Vector<GameState>();
      gameState.findPossibleMoves(nextStates);
      int v;
      int bestV;
      int id = 0;

      List<Integer> results = new ArrayList<>();
      if (nextStates.size() == 0 || depth == 0){bestV = eval(gameState);
      } else {
         if (player == MAX) { // MAX
            bestV = Integer.MIN_VALUE; // worst case performance for alpha -infinity
            for (int i = 0; i < nextStates.size(); i++) { // for each child of node
               v = alphaBeta(nextStates.get(i), depth - 1, alpha, beta, MIN).get(0);
               if (v > bestV) {
                  bestV = v;
                  id = i;
               }
               alpha = Math.max(alpha, bestV);
               if (beta <= alpha) {
                  break;// beta prune
               }
            }
         } else { //MIN
            bestV = Integer.MAX_VALUE; // worst case performance for beta +infinity
            for (int i = 0; i < nextStates.size(); i++) {
               v = alphaBeta(nextStates.get(i), depth - 1, alpha, beta, MAX).get(0);
               if (v < bestV) {
                  bestV = v;
                  id = i;
               }
               beta = Math.min(beta, bestV);
               if (beta <= alpha) {
                  break; // alpha prune
               }
            }
         }
      }
      results.add(bestV);
      results.add(id);
      return results;
   }

   private int eval(GameState gameState) {
      int currentMaximumUtility = 0;
      for (int i = 0; i < winningCombinations.length; i++) {
         int xCount = 0;
         int oCount = 0;

         for (int j = 0; j < winningCombinations[i].length; j++) {
            if (gameState.at(winningCombinations[i][j]) == MAX) {
               xCount++;
            } else if (gameState.at(winningCombinations[i][j]) == MIN){
               oCount++;
            }
         }
         currentMaximumUtility += POINTS[xCount][oCount];
      }
      return currentMaximumUtility;
   }

}
