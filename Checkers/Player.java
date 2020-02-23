import java.util.*;

public class Player {

   private HashMap<String, Integer> valueMap;
   private int MAX;
   private int MIN;

   private int[] kingsEval = {
                                8,  8,  8,  8,
                                7,  7,  7,  7,
                                6,  6,  6,  6,
                                5,  5,  5,  5,
                                4,  4,  4,  4,
                                3,  3,  3,  3,
                                2,  2,  2,  2,
                                1,  1,  1,  1
   };
   private int[] positionScores = {
                                3,  3,  3,  3,
                                3,  2,  2,  2,
                                2,  1,  1,  3,
                                3,  1,  1,  2,
                                2,  1,  1,  3,
                                3,  1,  1,  2,
                                2,  2,  2,  3,
                                3,  3,  3,  3
   };
   private int redScore[] = {
                                4,  3,  2,  3,
                                4,  2,  3,  2,
                                4,  2,  3,  4,
                                6,  5,  4,  5,
                                4,  5,  6,  6,
                                6,  5,  6,  6,
                                7,  7,  8,  8,
                                10, 9,  10, 10
   };
   private int whiteScore[] = {
                                10,  10,  9,  10,
                                8,   8,   7,   7,
                                6,   6,   5,   6,
                                6,   6,   5,   4,
                                5,   4,   5,   6,
                                4,   3,   2,   4,
                                2,   3,   2,   4,
                                3,   2,   3,   4
   };

   public Player() {
      valueMap = new HashMap<>();
   }

   public GameState play(final GameState pState, final Deadline pDue) {

      MAX = pState.getNextPlayer();
      MIN = MAX == Constants.CELL_WHITE ? Constants.CELL_RED : Constants.CELL_WHITE;

      Vector<GameState> nextStatesVector = new Vector<GameState>();
      pState.findPossibleMoves(nextStatesVector);

      if (nextStatesVector.size() == 0) return new GameState(pState, new Move());

      int bestMove = 0;
      int maxDepth = 11;
      int value;
      int[] vList = new int[nextStatesVector.size()];

      // iterative deepening:
      for (int depth = 0; depth < maxDepth; depth++){

         int alpha = Integer.MIN_VALUE;
         int beta = Integer.MAX_VALUE;
         int bestScore = Integer.MIN_VALUE;

         for (int i = 0; i < nextStatesVector.size(); i++) {
            GameState newBoard = nextStatesVector.get(i);
            String key = newBoard.toMessage();

            if (valueMap.get(key) != null) {
               value = valueMap.get(key);
            } else {
               value = alphaBeta(newBoard, depth, alpha, beta, MIN);
               if (depth > 6){
                  valueMap.put(key,value); 
               }
            }
            vList[i] = value;
            nextStatesVector = selectionSort(vList, nextStatesVector);
            if (value > bestScore) {
               bestScore = value;
               bestMove = i;
            }
            if (value > alpha) {
               alpha = value;
            }
         }
      }
      return nextStatesVector.get(bestMove);
   }

   private Vector<GameState> selectionSort(int[] vList, Vector<GameState> nextStates) {
      // reference: https://www.geeksforgeeks.org/selection-sort/
      int minIndex, n;
      n = vList.length;
      for (int i = 0; i < n - 1; i++) {
         // Find the minimum element in unsorted array
         minIndex = i;
         for (int j = i + 1; j < n; j++) {
            if (vList[j] > vList[minIndex]) {
               minIndex = j;
            }
         }
         // Swap!
         // --------- Swap Scores ------------------ //
         int swapV = vList[minIndex];
         vList[minIndex] = vList[i];
         vList[i] = swapV;
         // --------- Swap States ------------------ //
         GameState swapState = nextStates.get(minIndex);
         nextStates.set(minIndex, nextStates.get(i));
         nextStates.set(i, swapState);
      }
      return nextStates;
   }

   private int alphaBeta(GameState gameState, int depth, int alpha, int beta, int player) {
      int value;
      Vector<GameState> nextStates = new Vector<>();
      gameState.findPossibleMoves(nextStates);

      if (depth == 0 || gameState.isEOG()) {
         value = eval(gameState);
         return value;
      }
      if (player == MAX) {
         value = Integer.MIN_VALUE;
         for (int i = 0; i < nextStates.size(); i++) {
            value = Math.max(value, alphaBeta(nextStates.get(i), depth - 1, alpha, beta, MIN));
            alpha = Math.max(alpha, value);
            if (beta <= alpha) {
               break;
            }
         }
      } else {
         value = Integer.MAX_VALUE;
         for (int i = 0; i < nextStates.size(); i++) {
            value = Math.min(value, alphaBeta(nextStates.get(i), depth - 1, alpha, beta, MAX));
            beta = Math.min(beta, value);
            if (beta <= alpha) {
               break;
            }
         }
      }
      return value;
   }

   private int eval(GameState state) {

      if (state.isEOG()) {
         if (MAX == Constants.CELL_RED) {
            if (state.isRedWin()) {
               return Integer.MAX_VALUE;
            } else if (state.isWhiteWin()) {
               return Integer.MIN_VALUE;
            } else {
               return 0;
            }
         } else {
            if (state.isRedWin())
               return Integer.MIN_VALUE;
            else if (state.isWhiteWin())
               return Integer.MAX_VALUE;
            else return 0;
         }
      }

      int kingPieceValue = 6;
      int otherPieceValue = 4;
      int kingScore = 0;
      int pieceScore = 0;
      int positionScore = 0;
      int otherColorTurn;
      int [] maxScore;
      int [] minScore;

      int colorTurn;
      for (int i = 0; i < state.NUMBER_OF_SQUARES; i++) {
         if (MAX == Constants.CELL_RED){
            colorTurn = Constants.CELL_RED;
            otherColorTurn = Constants.CELL_WHITE;
            maxScore = redScore;
            minScore = whiteScore;
         } else {
            colorTurn = Constants.CELL_WHITE;
            otherColorTurn = Constants.CELL_RED;
            maxScore = whiteScore;
            minScore = redScore;

         }
         int piece = state.get(i);
         if (piece == colorTurn){
            pieceScore +=  otherPieceValue * 1000;
            positionScore += positionScores[i] +maxScore[i];
         } else if (piece == (colorTurn | Constants.CELL_KING)){
            pieceScore += kingPieceValue * 1000;
            kingScore += kingsEval[i] * 100;
         } else if (piece == otherColorTurn){
            pieceScore -= otherPieceValue * 1000;
            positionScore -= positionScores[i] +minScore[i];
         } else if (piece == (otherColorTurn | Constants.CELL_KING)){
            pieceScore -= kingPieceValue * 1000;
            kingScore -= 9 - kingsEval[i] * 100;
         }
      }
      return kingScore + pieceScore + positionScore;
   }
}
