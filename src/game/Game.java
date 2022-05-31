package game;

import javafx.util.Pair;

import java.util.Map;

public class Game {

    public final int[] board;
    public int startingPlayer, currentPlayer;

    public Game() {
        board = new int[9];
        startingPlayer = 1;
        currentPlayer = 1;
    }
    
    public Game(int[] board) {
        this.board = board;
        startingPlayer = 1;
        currentPlayer = 1;
    }

    public int aiMove() {
        Minimax minimax = new Minimax(board.clone(), currentPlayer);
        System.out.println(minimax.getMoves());
        return minimax.bestMove();
    }

    public boolean playMove(int player, int index) {
        if (isValid(index) && currentPlayer == player) {
            board[index] = player;

            currentPlayer = player % 2 + 1;
            return true;
        }
        return false;
    }

    /**
     * Checks the condition of the game
     * @return id of player if player win, 3 for draw, 0 for nothing
     */
    public Pair<Integer, Integer> checkGameCondition() {
        int[] b = board;
        
        //Horizontal wins
        if (b[0] == b[1] && b[1] == b[2] && b[0] != 0) return new Pair<>(b[0],0);
        if (b[3] == b[4] && b[4] == b[5] && b[3] != 0) return new Pair<>(b[3],1);
        if (b[6] == b[7] && b[7] == b[8] && b[6] != 0) return new Pair<>(b[6],2);

        //Vertical wins
        if (b[0] == b[3] && b[3] == b[6] && b[0] != 0) return new Pair<>(b[0],3);
        if (b[1] == b[4] && b[4] == b[7] && b[1] != 0) return new Pair<>(b[1],4);
        if (b[2] == b[5] && b[5] == b[8] && b[2] != 0) return new Pair<>(b[2],5);
        
        //Diagonal wins
        if (b[0] == b[4] && b[4] == b[8] && b[0] != 0) return new Pair<>(b[0],6);
        if (b[2] == b[4] && b[4] == b[6] && b[2] != 0) return new Pair<>(b[2],7);
        
        //Draw
        if (b[0] != 0 && b[1] != 0 && b[2] != 0 && b[3] != 0 && b[4] != 0 && b[5] != 0 && b[6] != 0 && b[7] != 0 && b[8] != 0) return new Pair<>(3,0);
        
        return new Pair<>(0,0);
    }

    public String boardString() {
        return String.format("%d | %d | %d\n%d | %d | %d\n%d | %d | %d\n", board[0], board[1], board[2], board[3],
                board[4], board[5], board[6], board[7], board[8]);
    }

    public boolean isValid(int index) {
        return board[index] == 0;
    }

    public void reset() {
        startingPlayer = startingPlayer % 2 + 1;
        currentPlayer = startingPlayer;

        for (int i = 0;i<9;i++) board[i] = 0;
    }

    public Map<Integer, Integer> getMoves() {
        Minimax minimax = new Minimax(this.board, this.currentPlayer);
        return null;
    }
}
