package ax.ha.tdd.chess.engine.pieces;

import ax.ha.tdd.chess.engine.Chessboard;
import ax.ha.tdd.chess.engine.Color;
import ax.ha.tdd.chess.engine.Square;

import java.util.ArrayList;
import java.util.List;

public class King extends ChessPieceBase implements ChessPiece {

    public King(Color player, Square location) {
        super(PieceType.KING, player, location);
    }

    @Override
    public boolean canMove(Chessboard chessboard, Square destination) {
        int deltaY = Math.abs(destination.getY() - location.getY());
        int deltaX = destination.getX() - location.getX();

        ChessPiece destinationPiece = chessboard.getPieceAt(destination);
        if (sameColorOrKingAtDestination(destinationPiece)) {
            return false;
        }

        if (castling(chessboard, deltaX, deltaY)) {
            return true;
        }

        deltaX = Math.abs(deltaX);
        if (!(deltaY == 0 && deltaX == 1 || deltaY == 1 && deltaX == 0 || deltaY == 1 && deltaX == 1)) {
            return false;
        }

        if (isDestinationSafe(chessboard, destination)) {
            hasPieceMoved = true;
            return true;
        }
        return false;
    }

    public boolean isKingChecked(Chessboard chessboard) {
        boolean kingIsChecked = false;

        chessboard.addPiece(new Knight(color, location));
        for (ChessPiece[] row : chessboard) {
            for (ChessPiece piece : row) {
                if (piece != null && piece.getColor() != color && piece.canMove(chessboard, location)) {
                    kingIsChecked = true;
                }
            }
        }

        chessboard.addPiece(this);

        return kingIsChecked;
    }

    public boolean isKingCheckmate(Chessboard chessboard) {
        if (!isKingChecked(chessboard)) {
            return false;
        }

        if (!getPossibleMoves(chessboard).isEmpty()) {
            return false;
        }

        for (ChessPiece[] row : chessboard) {
            for (ChessPiece piece : row) {
                if (piece != null && !piece.equals(this) && piece.getColor() == color) {
                    List<Square> possibleMoves = piece.getPossibleMoves(chessboard);
                    if (possibleMoves.size() > 0) {
                        for (Square move : possibleMoves) {
                            // Moves the piece and saves the piece at the destination to be able to revert the move.
                            ChessPiece destinationPiece = chessboard.getPieceAt(move);
                            Square srcLocation = piece.getLocation();
                            chessboard.movePiece(piece, move);

                            // If the king is not checked after the move then it is not a checkmate.
                            if (!isKingChecked(chessboard)) {
                                // Moves the pieces back to their original spots to revert the board.
                                chessboard.movePiece(piece, srcLocation);
                                if (destinationPiece != null) {
                                    chessboard.addPiece(destinationPiece);
                                }

                                return false;
                            }
                            chessboard.movePiece(piece, srcLocation);
                            if (destinationPiece != null) {
                                chessboard.addPiece(destinationPiece);
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    private boolean isDestinationSafe(Chessboard chessboard, Square destination) {
        // Moves the king to the destination and looks if it is checked.
        Square srcLocation = location;
        ChessPiece destinationPiece = chessboard.getPieceAt(destination);
        chessboard.movePiece(this, destination);

        //If it is checked in the destination spot it is not a safe move.
        if (isKingChecked(chessboard)) {
            // Reverts the pieces to their places.
            chessboard.movePiece(this, srcLocation);
            if (destinationPiece != null)
                chessboard.addPiece(destinationPiece);

            return false;
        }
        chessboard.movePiece(this, srcLocation);
        if (destinationPiece != null)
            chessboard.addPiece(destinationPiece);

        return true;
    }

    private boolean castling(Chessboard chessboard, int deltaX, int deltaY) {
        if (!hasPieceMoved && !isChecked && deltaY == 0) {
            if (deltaX == -2) {
                ChessPiece rook = chessboard.getPieceAt(new Square(0, location.getY()));
                // Checks that the rook hasn't moved yet and that all squares between the Rook and the king are empty.
                if (rook != null && !rook.getHasPieceMoved() &&
                        chessboard.getPieceAt(new Square(1, location.getY())) == null &&
                        chessboard.getPieceAt(new Square(2, location.getY())) == null &&
                        chessboard.getPieceAt(new Square(3, location.getY())) == null) {
                    // If the destination and the square between is safe then the castling move is made.
                    if (isDestinationSafe(chessboard, new Square(2, location.getY())) &&
                                  isDestinationSafe(chessboard, new Square(3, location.getY()))) {
                        rook.setLocation(new Square(3, location.getY()));
                        chessboard.addPiece(rook);
                        chessboard.removePieceAt(new Square(0, location.getY()));
                        return true;
                    }
                }
            } else if (deltaX == 2) {
                ChessPiece rook = chessboard.getPieceAt(new Square(7, location.getY()));
                // Checks that the rook hasn't moved yet and that all squares between the Rook and the king are empty.
                if (rook != null && !rook.getHasPieceMoved() &&
                        chessboard.getPieceAt(new Square(5, location.getY())) == null &&
                        chessboard.getPieceAt(new Square(6, location.getY())) == null) {
                    // If the destination and the square between is safe then the castling move is made.
                    if (isDestinationSafe(chessboard, new Square(5, location.getY())) &&
                            isDestinationSafe(chessboard, new Square(6, location.getY()))) {
                        rook.setLocation(new Square(5, location.getY()));
                        chessboard.addPiece(rook);
                        chessboard.removePieceAt(new Square(0, location.getY()));
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public List<Square> getPossibleMoves(Chessboard chessboard) {
        List<Square> possibleMoves = new ArrayList<>();
        Square destination;

        int[] values = {-1, 0, 1};
        for (int i : values) {
            for (int j : values) {
                if (!(j == 0 && i == 0)) {
                    try {
                        destination = new Square(location.getX() + i, location.getY() + j);
                        if (canMove(chessboard, destination)) {
                            possibleMoves.add(destination);
                        }
                    } catch (IllegalArgumentException ignore) {
                    }
                }
            }
        }
        return possibleMoves;
    }
}
