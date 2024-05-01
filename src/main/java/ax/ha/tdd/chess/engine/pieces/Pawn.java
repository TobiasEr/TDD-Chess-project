package ax.ha.tdd.chess.engine.pieces;

import ax.ha.tdd.chess.engine.Chessboard;
import ax.ha.tdd.chess.engine.Square;
import ax.ha.tdd.chess.engine.Color;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends ChessPieceBase implements ChessPiece{

    protected int passantMoveNbr;

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
            if (deltaY == 1 && deltaX == 1) {
                if (checkPassant(chessboard, destination)) {
                    return true;
                }
                if (destinationPiece != null) {
                    return true; // Capture move for black pawn
                }
            }
            if (deltaY == 1 && deltaX == 0 && destinationPiece == null) {
                return true; // Forward move for black pawn
            }
            if (this.location.getY() == 1 && deltaY == 2 && deltaX == 0 && destinationPiece == null) {
                if (chessboard.getPieceAt(new Square(this.location.getX(), 2)) == null) {
                    passantMoveNbr = chessboard.getMovesMade();
                    return true;
                }
            }
        } else if (this.color == Color.WHITE) {
            if (deltaY == -1 && deltaX == 1) {
                if (checkPassant(chessboard, destination)) {
                    return true;
                }
                if (destinationPiece != null) {
                    return true; // Capture move for white pawn
                }
            }
            if (deltaY == -1 && deltaX == 0 && destinationPiece == null) {
                return true; // Forward move for white pawn
            }
            if (this.location.getY() == 6 && deltaY == -2 && deltaX == 0 && destinationPiece == null) {
                if (chessboard.getPieceAt(new Square(this.location.getX(), 5)) == null) {
                    passantMoveNbr = chessboard.getMovesMade();
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkPassant(Chessboard chessboard, Square destination) {
        if (color == Color.BLACK && location.getY() != 4) {
            return false;
        } else if (color == Color.WHITE && location.getY() != 3) {
            return false;
        }
        ChessPiece passantPiece = chessboard.getPieceAt(new Square(destination.getX(), location.getY()));
        if (passantPiece != null && passantPiece.getType() == PieceType.PAWN && passantPiece.getColor() != color) {
            Pawn passantPawn = (Pawn) chessboard.getPieceAt(new Square(destination.getX(), location.getY()));
            if (passantPawn.passantMoveNbr == chessboard.getMovesMade()-1) {
                chessboard.removePieceAt(new Square(destination.getX(), location.getY()));
                return true;
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
}
