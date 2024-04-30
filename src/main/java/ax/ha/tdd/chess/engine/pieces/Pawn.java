package ax.ha.tdd.chess.engine.pieces;

import ax.ha.tdd.chess.engine.Chessboard;
import ax.ha.tdd.chess.engine.Square;
import ax.ha.tdd.chess.engine.Color;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends ChessPieceBase implements ChessPiece{

    public Pawn(Color player, Square location) {
        super(PieceType.PAWN, player, location);
    }

    @Override
    public boolean canMove(Chessboard chessboard, Square destination) {
        int deltaY = destination.getY() - location.getY();
        int deltaX = Math.abs(destination.getX() - location.getX());

        ChessPiece destinationPiece = chessboard.getPieceAt(destination);
        if (sameColorOrKingAtDestination(destinationPiece)) {
            return false;
        }

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

    @Override
    public List<Square> getPossibleMoves(Chessboard chessboard) {
        List<Square> possibleMoves = new ArrayList<>();
        int forwardDirection = (color == Color.BLACK) ? 1 : -1;

        // Moving forward
        addForwardMoves(chessboard, possibleMoves, forwardDirection);

        // Diagonal capture
        addDiagonalMoves(chessboard, possibleMoves, forwardDirection);

        return possibleMoves;
    }

    private void addForwardMoves(Chessboard chessboard, List<Square> possibleMoves, int forwardDirection) {
        int maxSteps = (location.getY() == (color == Color.BLACK ? 1 : 6)) ? 2 : 1;
        for (int i = 1; i <= maxSteps; i++) {
            try {
                Square destination = new Square(location.getX(), location.getY() + forwardDirection * i);
                if (canMove(chessboard, destination)) {
                    possibleMoves.add(destination);
                } else {
                    break;
                }
            } catch (IllegalArgumentException ignore) {}
        }
    }

    private void addDiagonalMoves(Chessboard chessboard, List<Square> possibleMoves, int forwardDirection) {
        int[] deltas = { -1, 1 };
        for (int delta : deltas) {
            try {
                Square destination = new Square(location.getX() + delta, location.getY() + forwardDirection);
                if (canMove(chessboard, destination) && chessboard.getPieceAt(destination) != null &&
                        chessboard.getPieceAt(destination).getColor() != color) {
                    possibleMoves.add(destination);
                }
            } catch (IllegalArgumentException ignore) {}
        }
    }

    /*@Override
    public List<Square> getPossibleMoves(Chessboard chessboard) {
        List<Square> possibleMoves = new ArrayList<>();
        Square destination;
        if (color == Color.BLACK) {
            if (location.getY() == 1) {
                for (int i = 1; i<3; i++) {
                    destination = new Square(location.getX(), location.getY()+i);
                    if (canMove(chessboard, destination)) {
                        possibleMoves.add(destination);
                    }
                }
            } else {
                destination = new Square(location.getX(), location.getY()+1);
                if (canMove(chessboard, destination)) {
                    possibleMoves.add(destination);
                }
            }
            if (canMove(chessboard, new Square(location.getX()+1, location.getY()+1))) {
                possibleMoves.add(new Square(location.getX()+1, location.getY()+1));
            } else if (canMove(chessboard, new Square(location.getX()-1, location.getY()+1))) {
                possibleMoves.add(new Square(location.getX()-1, location.getY()+1));
            }
        } else {
            if (location.getY() == 6) {
                for (int i = 1; i<3; i++) {
                    destination = new Square(location.getX(), location.getY()-i);
                    if (canMove(chessboard, destination)) {
                        possibleMoves.add(destination);
                    }
                }
            } else {
                destination = new Square(location.getX(), location.getY()-1);
                if (canMove(chessboard, destination)) {
                    possibleMoves.add(destination);
                }
            }
            if (canMove(chessboard, new Square(location.getX()+1, location.getY()-1))) {
                possibleMoves.add(new Square(location.getX()+1, location.getY()-1));
            } else if (canMove(chessboard, new Square(location.getX()-1, location.getY()-1))) {
                possibleMoves.add(new Square(location.getX()-1, location.getY()-1));
            }
        }
        return possibleMoves;
    }*/
}
