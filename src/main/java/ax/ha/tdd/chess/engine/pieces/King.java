package ax.ha.tdd.chess.engine.pieces;

import ax.ha.tdd.chess.engine.Chessboard;
import ax.ha.tdd.chess.engine.Color;
import ax.ha.tdd.chess.engine.Square;

import java.util.ArrayList;
import java.util.List;

public class King extends ChessPieceBase implements ChessPiece{

    public King(Color player, Square location) {
        super(PieceType.KING, player, location);
    }

    @Override
    public boolean canMove(Chessboard chessboard, Square destination) {
        int deltaY = Math.abs(destination.getY() - location.getY());
        int deltaX = Math.abs(destination.getX() - location.getX());

        ChessPiece destinationPiece = chessboard.getPieceAt(destination);
        if (sameColorOrKingAtDestination(destinationPiece)) {
            return false;
        }

        if (!(deltaY == 0 && deltaX == 1 || deltaY == 1 && deltaX == 0 || deltaY == 1 && deltaX == 1)) {
            return false;
        }

        for (ChessPiece[] row : chessboard) {
            for (ChessPiece piece : row) {
                if (piece != null && !piece.equals(this)) {
                    // Will only check pieces of the other color.
                    if (piece.getColor() != color) {
                        if (piece.getType() == PieceType.PAWN) {
                            // If the piece is a pawn then it places a Knight piece (can be any piece) on the
                            // destination square. This is to check if a pawn can attack on that square.
                            chessboard.addPiece(new Knight(color, destination));
                        } else {
                            // If it's any other piece then it removes the piece at the destination.
                            // Because if there is a piece standing there that the king can attack then the piece that
                            // this currently checks can not move there, therefore the square needs to be empty.
                            chessboard.removePieceAt(destination);
                        }

                        if (piece.getType() == PieceType.KING) {
                            // This is to avoid using canMove on a king piece to avoid going into an infinite loop.
                            int deltaKingX = Math.abs(piece.getLocation().getX() - destination.getX());
                            int deltaKingY = Math.abs(piece.getLocation().getY() - destination.getY());
                            if ((deltaKingY == 1 && deltaKingX == 0) ||
                                    (deltaKingY == 0 && deltaKingX == 1) ||
                                    (deltaKingY == 1 && deltaKingX == 1)) {
                                return false;
                            }
                        } else {
                            chessboard.removePieceAt(location);
                            if (piece.canMove(chessboard, destination)) {
                                if (destinationPiece != null) {
                                    chessboard.addPiece(destinationPiece);
                                } else {
                                    chessboard.removePieceAt(destination);
                                }
                                chessboard.addPiece(this);
                                return false;
                            }
                            chessboard.addPiece(this);
                        }

                        if (destinationPiece != null) {
                            chessboard.addPiece(destinationPiece);
                        } else {
                            chessboard.removePieceAt(destination);
                        }
                    }
                }
            }
        }

        return true;
    }

    @Override
    public List<Square> getPossibleMoves(Chessboard chessboard) {
        List<Square> possibleMoves = new ArrayList<>();
        Square destination;

        int[] values = {-1, 0, 1};
        for (int i: values) {
            for (int j: values) {
                if (j != 0 && i != 0) {
                    try {
                        destination = new Square(location.getX() + i, location.getY() + j);
                        if (canMove(chessboard, destination)) {
                            possibleMoves.add(destination);
                        }
                    } catch (IllegalArgumentException ignore){}
                }
            }
        }
        return possibleMoves;
    }
}
