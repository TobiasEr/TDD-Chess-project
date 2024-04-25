package ax.ha.tdd.chess.engine.pieces;

import ax.ha.tdd.chess.engine.Chessboard;
import ax.ha.tdd.chess.engine.Color;
import ax.ha.tdd.chess.engine.Square;

public class Rook extends ChessPieceBase implements ChessPiece {

    public Rook(Color player, Square location) {
        super(PieceType.ROOK, player, location);
    }
    @Override
    public boolean canMove(Chessboard chessboard, Square destination) {
        int deltaY = destination.getY() - location.getY();
        int deltaX = destination.getX() - location.getX();

        ChessPiece destinationPiece = chessboard.getPieceAt(destination);
        if (sameColorOrKingAtDestination(destinationPiece)) {
            return false; // Cannot capture own piece
        }

        if (!(deltaY == 0 && Math.abs(deltaX) > 0) && !(Math.abs(deltaY) > 0 && deltaX == 0)) {
            return false;
        }

        int step;
        if (deltaX == 0) {
            step = (deltaY > 0) ? 1 : -1;
            for (int i= location.getY() + step; i != destination.getY(); i += step) {
                if (chessboard.getPieceAt(new Square(location.getX(), i)) != null) {
                    return false;
                }
            }
        } else {
            step = (deltaX > 0) ? 1 : -1;
            for (int i = location.getX() + step; i != destination.getX(); i += step) {
                if (chessboard.getPieceAt(new Square(i, location.getY())) != null) {
                    return false;
                }
            }
        }
        return true;
    }
}
