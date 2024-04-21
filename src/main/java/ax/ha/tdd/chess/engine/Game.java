package ax.ha.tdd.chess.engine;

public interface Game {

    /**
     * Returns which player's turn it is
     */
    Color getPlayerToMove();

    /**
     * Current board state
     */
    Chessboard getBoard();

    /**
     * Returns a string that describes the result of the last move to be displayed to the user.
     * e.g. "Illegal move: Pawn to e5" or "Knight g to f6", or something simpler.
     */
    String getLastMoveResult();

    /**
     * Take a string representing the next move,
     * and apply it to the board. Do nothing if the move is illegal
     */
    void move(String move);

}
