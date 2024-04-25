package ax.ha.tdd.chess.engine.pieces;

import ax.ha.tdd.chess.engine.Chessboard;
import ax.ha.tdd.chess.engine.Color;
import ax.ha.tdd.chess.engine.Square;

public class Bishop extends ChessPieceBase implements ChessPiece {

    public Bishop(Color player, Square location) {
        super(PieceType.BISHOP, player, location);
    }
    @Override
    public boolean canMove(Chessboard chessboard, Square destination) {
        int deltaY = destination.getY() - location.getY();
        int deltaX = destination.getX() - location.getX();

        ChessPiece destinationPiece = chessboard.getPieceAt(destination);
        if (sameColorOrKingAtDestination(destinationPiece)) {
            return false; // Cannot capture own piece
        }
        if (Math.abs(deltaX) != Math.abs(deltaY)) {
            return false;
        }

        int stepX = (deltaX > 0) ? 1 : -1;
        int stepY = (deltaY > 0) ? 1 : -1;

        int posX = location.getX() + stepX;
        int posY = location.getY() + stepY;

        while (posX != destination.getX() && posY != destination.getY()) {
            if (chessboard.getPieceAt(new Square(posX, posY)) != null) {
                return false;
            }
            posX += stepX;
            posY += stepY;
        }

        return true;
    }
}
