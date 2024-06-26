package ax.ha.tdd.chess.engine.pieces;

import ax.ha.tdd.chess.engine.Chessboard;
import ax.ha.tdd.chess.engine.Color;
import ax.ha.tdd.chess.engine.Square;

import java.util.ArrayList;
import java.util.List;

public class Rook extends ChessPieceBase implements ChessPiece {

    private boolean isPieceMoving = true;

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
        if (isPieceMoving) {
            hasPieceMoved = true;
        }
        return true;
    }

    @Override
    public List<Square> getPossibleMoves(Chessboard chessboard) {
        List<Square> possibleMoves = new ArrayList<>();
        int[] steps = {1, -1};
        isPieceMoving = false;

        for (int step: steps) {
            for (int i = 1; i<8; i++) {
                try {
                    Square destination = new Square(location.getX() + step * i, location.getY());
                    if (canMove(chessboard, destination)) {
                        possibleMoves.add(destination);
                    }
                } catch (IllegalArgumentException ignore) {}
                try {
                    Square destination = new Square(location.getX(), location.getY() + step * i);
                    if (canMove(chessboard, destination)) {
                        possibleMoves.add(destination);
                    }
                } catch (IllegalArgumentException ignore) {}
            }
        }
        isPieceMoving = true;
        return possibleMoves;
    }
}
