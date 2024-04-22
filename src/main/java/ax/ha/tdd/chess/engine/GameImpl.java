package ax.ha.tdd.chess.engine;

import ax.ha.tdd.chess.engine.pieces.ChessPiece;
import ax.ha.tdd.chess.engine.pieces.ChessPieceBase;

public class GameImpl implements Game{

    final ChessboardImpl board = ChessboardImpl.startingBoard();

    boolean isNewGame = true;
    String lastMoveResult;
    Color currentPlayer = Color.WHITE;

    @Override
    public Color getPlayerToMove() {
        //TODO this should reflect the current state.
        return currentPlayer;
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
        return lastMoveResult;
    }

    @Override
    public void move(String move) {
        //TODO this should trigger your move logic.
        //1. Parse the source and destination of the input "move"
        try {
            String[] moves = move.split("-");
            Square source = new Square(moves[0]);
            Square destination = new Square(moves[1]);
            //2. Check if the piece is allowed to move to the destination
            if (board.getPieceAt(source) == null) {
                lastMoveResult = "Illegal move. No piece at " + source.toAlgebraic();
                return;
            }
            if (board.getPieceAt(source).getColor() != currentPlayer) {
                lastMoveResult = "Illegal move. It's " + currentPlayer + "'s turn.";
                return;
            }

            if (board.getPieceAt(source).canMove(board, destination)) {
                board.getPieceAt(source).setLocation(destination);
                board.addPiece(board.getPieceAt(source));
                board.removePieceAt(source);

                if (currentPlayer == Color.WHITE) {
                    currentPlayer = Color.BLACK;
                } else {
                    currentPlayer = Color.WHITE;
                }

                lastMoveResult = "Player moved piece from " + source.toAlgebraic() + " to " + destination.toAlgebraic();
            } else {
                lastMoveResult = "Illegal move was tried.";
            }
        } catch (IllegalArgumentException e) {
            lastMoveResult = "Illegal input or out of bounds.";
        }

        //3. If so, update board (and last move message), otherwise only update last move message to show that an illegal move was tried

        isNewGame = false;
        System.out.println("Player tried to perform move: " + move);
    }
}
