package ax.ha.tdd.chess.engine;

public class GameImpl implements Game{

    final ChessboardImpl board = ChessboardImpl.startingBoard();

    boolean isNewGame = true;

    @Override
    public Color getPlayerToMove() {
        //TODO this should reflect the current state.
        return Color.WHITE;
    }

    @Override
    public Chessboard getBoard() {
        return board;
    }

    @Override
    public String getLastMoveResult() {
        //TODO this should be used to show the player what happened
        //Illegal move, correct move, e2 moved to e4 etc. up to you!
        if (isNewGame) {
            return "Game hasn't begun";
        }
        return "Last move was successful (default reply, change this)";
    }

    @Override
    public void move(String move) {
        //TODO this should trigger your move logic.
        //1. Parse the source and destination of the input "move"

        //2. Check if the piece is allowed to move to the destination

        //3. If so, update board (and last move message), otherwise only update last move message to show that an illegal move was tried

        isNewGame = false;
        System.out.println("Player tried to perform move: " + move);
    }
}
