package ax.ha.tdd.chess.engine;

import ax.ha.tdd.chess.engine.pieces.ChessPiece;

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
        try {
            String[] moves = move.split("-");
            Square source = new Square(moves[0]);
            Square destination = new Square(moves[1]);
            ChessPiece sourcePiece = board.getPieceAt(source);

            if (sourcePiece == null) {
                lastMoveResult = "Illegal move. No piece at " + source.toAlgebraic();
                return;
            }
            if (sourcePiece.getColor() != currentPlayer) {
                lastMoveResult = "Illegal move. It's " + currentPlayer + "'s turn.";
                return;
            }

            if (sourcePiece.canMove(board, destination)) {
                sourcePiece.setLocation(destination);
                board.addPiece(sourcePiece);
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

        isNewGame = false;
        System.out.println("Player tried to perform move: " + move);
    }
}
