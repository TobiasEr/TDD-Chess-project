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
        if (sameColorAtDestination(destinationPiece)) {
            return false; // Cannot capture own piece
        }

        if (!(deltaY == 0 && Math.abs(deltaX) > 0) && !(Math.abs(deltaY) > 0 && deltaX == 0)) {
            return false;
        }

        if (Math.abs(deltaY) > 0) {
            if (deltaY > 1) {
                for (int i= location.getY()+1; i < destination.getY(); i++) {
                    if (chessboard.getPieceAt(new Square(location.getX(), i)) != null) {
                        return false;
                    }
                }
            } else if (deltaY < -1) {
                for (int i= location.getY()-1; i > destination.getY(); i--) {
                    if (chessboard.getPieceAt(new Square(location.getX(), i)) != null) {
                        return false;
                    }
                }
            }
            return true;
        }

        if (Math.abs(deltaX) > 0) {
            if (deltaX > 1) {
                for (int i= location.getX()+1; i < destination.getX(); i++) {
                    if (chessboard.getPieceAt(new Square(i, location.getY())) != null) {
                        return false;
                    }
                }
            } else if (deltaX < -1) {
                for (int i= location.getX()-1; i > destination.getX(); i--) {
                    if (chessboard.getPieceAt(new Square(i, location.getY())) != null) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }
}
