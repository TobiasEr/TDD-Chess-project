package ax.ha.tdd.chess.engine;

import ax.ha.tdd.chess.engine.pieces.*;

public class GameImpl implements Game {

    ChessboardImpl board = ChessboardImpl.startingBoard();
    boolean isNewGame = true;
    String lastMoveResult;
    Color currentPlayer = Color.WHITE;
    boolean pawnChanging = false;
    ChessPiece sourcePiece;
    boolean gameOver = false;

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
        Square source;
        Square destination;

        if (gameOver) {
            resetGame(move);
            return;
        }

        if (pawnChanging) {
            if (changePawn(move)) {
                lastMoveResult = sourcePiece.getColor() + " " + sourcePiece.getType() + " changed! " +
                        "It's " + currentPlayer + "'s turn now.";
            }
            return;
        }

        try {
            String[] moves = move.split("-");
            source = new Square(moves[0]);
            destination = new Square(moves[1]);
        } catch (IllegalArgumentException e) {
            lastMoveResult = "Illegal input or out of bounds.";
            return;
        }

        sourcePiece = board.getPieceAt(source);

        if (sourcePiece == null) {
            lastMoveResult = "Illegal move. No piece at " + source.toAlgebraic();
            return;
        }
        if (sourcePiece.getColor() != currentPlayer) {
            lastMoveResult = "Illegal move. It's " + currentPlayer + "'s turn.";
            return;
        }

        movingPiece(source, destination);

        isNewGame = false;
        System.out.println("Player tried to perform move: " + move);
    }

    private void resetGame(String input) {
        lastMoveResult = "Game is over! Type 'reset' to restart the game.";
        if (input.equals("reset")) {
            board = ChessboardImpl.startingBoard();
            gameOver = false;
            currentPlayer = Color.WHITE;
            pawnChanging = false;
            isNewGame = true;
            sourcePiece = null;
        }
    }

    private void movingPiece(Square pieceLocation, Square destination) {
        if (sourcePiece.canMove(board, destination)) {
            board.increaseMovesMade();
            sourcePiece.setLocation(destination);
            board.addPiece(sourcePiece);
            board.removePieceAt(pieceLocation);

            if (sourcePiece.getType() == PieceType.PAWN) {
                if ((sourcePiece.getColor() == Color.WHITE && sourcePiece.getLocation().getY() == 0) ||
                        (sourcePiece.getColor() == Color.BLACK && sourcePiece.getLocation().getY() == 7)) {
                    pawnChanging = true;
                    lastMoveResult = sourcePiece.getColor() + " " + sourcePiece.getType() + " can change type!" +
                            " Type Q for Queen, K for Knight, B for Bishop, R for Rook or N for no change.";
                    return;
                }
            }

            changePlayerTurn();

            lastMoveResult = "Player moved " + sourcePiece.getType() +
                    " from " + pieceLocation.toAlgebraic() + " to " + destination.toAlgebraic();

            ChessPiece opponentKing = board.getOpponentKing(sourcePiece.getColor());

            if (board.isKingCheckmate(sourcePiece, opponentKing)) {
                lastMoveResult += ". " + opponentKing.getColor() + " is checkmate! " +
                        sourcePiece.getColor() + " won the match!";
                gameOver = true;
            } else if (board.isKingChecked(sourcePiece, opponentKing)) {
                lastMoveResult += ". " + opponentKing.getColor() + " is checked!";
            }
        } else {
            lastMoveResult = "Illegal move was tried.";
        }
    }

    private void changePlayerTurn() {
        if (currentPlayer == Color.WHITE) {
            currentPlayer = Color.BLACK;
        } else {
            currentPlayer = Color.WHITE;
        }
    }

    private boolean changePawn(String input) {
        switch (input) {
            case "Q" -> {
                Queen queen = new Queen(sourcePiece.getColor(), sourcePiece.getLocation());
                board.addPiece(queen);
                pawnChanging = false;
                changePlayerTurn();
                return true;
            }
            case "K" -> {
                Knight knight = new Knight(sourcePiece.getColor(), sourcePiece.getLocation());
                board.addPiece(knight);
                pawnChanging = false;
                changePlayerTurn();
                return true;
            }
            case "B" -> {
                Bishop bishop = new Bishop(sourcePiece.getColor(), sourcePiece.getLocation());
                board.addPiece(bishop);
                pawnChanging = false;
                changePlayerTurn();
                return true;
            }
            case "R" -> {
                Rook rook = new Rook(sourcePiece.getColor(), sourcePiece.getLocation());
                board.addPiece(rook);
                pawnChanging = false;
                changePlayerTurn();
                return true;
            }
            case "N" -> {
                pawnChanging = false;
                changePlayerTurn();
                return true;
            }
            default -> {
                lastMoveResult =
                        "Wrong input. Type Q for Queen, K for Knight, B for Bishop, R for Rook or N for no change.";
                return false;
            }
        }
    }
}
