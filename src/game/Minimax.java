package game;

import javafx.util.Pair;

import java.util.*;

public class Minimax {

    public static final int depth = Integer.MAX_VALUE;
    public static final int WIN = Integer.MAX_VALUE;
    public static final int LOSE = Integer.MIN_VALUE;
    public final int DRAW = 0;

    private int[] board;
    private int currentPlayer;

    public Minimax(int[] board, int currentPlayer) {
        this.board = board;
        this.currentPlayer = currentPlayer;

    }

    public int bestMove() {
        int index = -1;
        double max = Integer.MIN_VALUE;

        for (Map.Entry<Integer, Double> entry : getMoves().entrySet()) {
            if (entry.getValue() > max) {
                index = entry.getKey();
                max = entry.getValue();
            }
        }
        return index;
    }

    private int bestMove(int[] board, int currentPlayer) {
        int index = -1;
        double max = Integer.MIN_VALUE;

        Map<Integer, Double> moves = new HashMap<>();
        for (int i : possibleIndexes(board)) {
            moves.put(i, getScore(board.clone(), i, 0, currentPlayer));
        }

        for (Map.Entry<Integer, Double> entry : moves.entrySet()) {
            if (entry.getValue() > max) {
                index = entry.getKey();
                max = entry.getValue();
            }
        }
        return index;
    }

    public Map<Integer, Double> getMoves() {
        Map<Integer, Double> moves = new HashMap<>();
        for (int index : possibleIndexes(board)) {
            moves.put(index, getScore(board.clone(), index, 0, currentPlayer));
        }
        return moves;
    }

    public double getScore(int[] board, int move, int depth, int player) {
        double divide =  Math.pow(10D, depth);
        double scoreWin = WIN / divide;
        double scoreLose = LOSE / divide;

        board[move] = player;

        int result = checkGameCondition(board);

//        String prefix = "";
//
//        for (int i = 0;i<depth;i++) prefix += "__ ";
//
//        System.out.println(prefix + "Board: " + Arrays.toString(board) + ", Result: " + result + ", Player: " + player + ", Move:" + move  +
//                " " + scoreWin);

        if (result == player) return scoreWin;
        player = player % 2 + 1;
        if (result == player) return scoreLose;
        if (result == 3) return DRAW;

        double score = 0;
        for (int m : possibleIndexes(board)) {
            if (player == currentPlayer) score += getScore(board.clone(), m, depth + 1,  player);
            else score -= getScore(board.clone(), m, depth + 1,  player);
        }

        //FIX adding logic

        return score;
    }

    public int checkGameCondition(int[] b) {
        //Horizontal wins
        if (b[0] == b[1] && b[1] == b[2] && b[0] != 0) return b[0];
        if (b[3] == b[4] && b[4] == b[5] && b[3] != 0) return b[3];
        if (b[6] == b[7] && b[7] == b[8] && b[6] != 0) return b[6];

        //Vertical wins
        if (b[0] == b[3] && b[3] == b[6] && b[0] != 0) return b[0];
        if (b[1] == b[4] && b[4] == b[7] && b[1] != 0) return b[1];
        if (b[2] == b[5] && b[5] == b[8] && b[2] != 0) return b[2];

        //Diagonal wins
        if (b[0] == b[4] && b[4] == b[8] && b[0] != 0) return b[0];
        if (b[2] == b[4] && b[4] == b[6] && b[2] != 0) return b[2];

        //Draw
        if (b[0] != 0 && b[1] != 0 && b[2] != 0 && b[3] != 0 && b[4] != 0 && b[5] != 0 && b[6] != 0 && b[7] != 0 && b[8] != 0) return 3;

        return 0;
    }

    public List<Integer> possibleIndexes(int[] b) {
        List<Integer> moves = new ArrayList<>();
        for (int i = 0;i<b.length;i++) if (b[i] == 0) moves.add(i);
        return moves;
    }
}
