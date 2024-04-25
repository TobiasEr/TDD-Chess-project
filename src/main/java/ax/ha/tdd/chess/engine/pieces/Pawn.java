package ax.ha.tdd.chess.engine.pieces;

import ax.ha.tdd.chess.engine.Chessboard;
import ax.ha.tdd.chess.engine.Square;
import ax.ha.tdd.chess.engine.Color;

public class Pawn extends ChessPieceBase implements ChessPiece{

    public Pawn(Color player, Square location) {
        super(PieceType.PAWN, player, location);
    }

    @Override
    public boolean canMove(Chessboard chessboard, Square destination) {
        //TODO here goes move logic for pawns
        int deltaY = destination.getY() - location.getY();
        int deltaX = Math.abs(destination.getX() - location.getX());

        ChessPiece destinationPiece = chessboard.getPieceAt(destination);
        if (sameColorOrKingAtDestination(destinationPiece)) {
            return false; // Cannot capture own piece
        }

        // Determine valid movement based on pawn color
        if (this.color == Color.BLACK) {
            if (deltaY == 1 && deltaX == 1 && destinationPiece != null) {
                return true; // Capture move for black pawn
            }
            if (deltaY == 1 && deltaX == 0 && destinationPiece == null) {
                return true; // Forward move for black pawn
            }
            if (this.location.getY() == 1 && deltaY == 2 && deltaX == 0 && destinationPiece == null) {
                return chessboard.getPieceAt(new Square(this.location.getX(), 2)) == null;
            }
        } else if (this.color == Color.WHITE) {
            if (deltaY == -1 && deltaX == 1 && destinationPiece != null) {
                return true; // Capture move for white pawn
            }
            if (deltaY == -1 && deltaX == 0 && destinationPiece == null) {
                return true; // Forward move for white pawn
            }
            if (this.location.getY() == 6 && deltaY == -2 && deltaX == 0 && destinationPiece == null) {
                return chessboard.getPieceAt(new Square(this.location.getX(), 5)) == null;
            }
        }

        return false;
    }
}
