package ax.ha.tdd.chess.engine.pieces;

import ax.ha.tdd.chess.engine.Chessboard;
import ax.ha.tdd.chess.engine.Color;
import ax.ha.tdd.chess.engine.Square;

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

        for (ChessPiece[] row : chessboard) {
            for (ChessPiece piece : row) {
                if (piece != null && piece.getType() != PieceType.KING) {
                    if (piece.getColor() != color) {
                        if (piece.getType() == PieceType.PAWN) {
                            chessboard.addPiece(new Knight(color, destination));
                        } else {
                            chessboard.removePieceAt(destination);
                        }
                        if (piece.canMove(chessboard, destination)) {
                            if (destinationPiece != null) {
                                chessboard.addPiece(destinationPiece);
                            } else {
                                chessboard.removePieceAt(destination);
                            }
                            return false;
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

        return deltaY == 0 && deltaX == 1 || deltaY == 1 && deltaX == 0 || deltaY == 1 && deltaX == 1;
    }
}
