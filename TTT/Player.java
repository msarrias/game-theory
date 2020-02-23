import java.util.*;

public class Player {

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
        int maxDepth = 5;

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

    private int eval(GameState gameState){

        int xRowSum;
        int oRowSum;
        int xColSum;
        int oColSum;
        int rowScore = 0;
        int colScore = 0;
        int diagScore = 0;
        int xMainDiagSum = 0;
        int oMainDiagSum = 0;
        int xAntiDiagSum = 0;
        int oAntiDiagSum = 0;

        for(int i = 0; i < 4; i++){ // iterate over rows
            if(gameState.at(i,i) == MAX) xMainDiagSum++;
            else if(gameState.at(i,i) == MIN) oMainDiagSum++;
            if(gameState.at(3-i,i) == MAX) xAntiDiagSum++;
            else if(gameState.at(3-i,i) == MIN) oAntiDiagSum++;
            // we set the variables to zero so we can start
            // with a new row/column
            xRowSum = 0;
            oRowSum = 0;
            xColSum = 0;
            oColSum = 0;
            for(int j = 0; j < 4; j++){  // iterate over columns
                if(gameState.at(i,j) == MAX) xRowSum++;
                else if(gameState.at(i,j) == MIN) oRowSum++;
                if(gameState.at(j,i) == MAX) xColSum++;
                else if(gameState.at(j,i) == MIN) oColSum++;
            }

            rowScore += POINTS[xRowSum][oRowSum];
            colScore += POINTS[xColSum][oColSum];
        }
        diagScore += POINTS[xMainDiagSum][oMainDiagSum]+ POINTS[xAntiDiagSum][oAntiDiagSum];
        return rowScore + colScore + diagScore;
    }

}