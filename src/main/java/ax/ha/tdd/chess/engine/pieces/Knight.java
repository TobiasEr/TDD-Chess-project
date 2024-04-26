package ax.ha.tdd.chess.engine.pieces;

import ax.ha.tdd.chess.engine.Chessboard;
import ax.ha.tdd.chess.engine.Color;
import ax.ha.tdd.chess.engine.Square;

public class Knight extends ChessPieceBase implements ChessPiece {

    int[][] possibleMoves = {{-2,-1},{-2,1},{-1,-2},{1,-2},
            {2,-1},{2,1},{1,2},{-1,2}};

    public Knight(Color player, Square location) {
        super(PieceType.KNIGHT, player, location);
    }

    @Override
    public boolean canMove(Chessboard chessboard, Square destination) {
        ChessPiece destinationPiece = chessboard.getPieceAt(destination);
        if (sameColorOrKingAtDestination(destinationPiece)) {
            return false; // Cannot capture own piece
        }

        for (int[] moves : possibleMoves) {
            try {
                Square square = new Square(location.getX() + moves[0], location.getY() + moves[1]);
                if (square.equals(destination)) {
                    return true;
                }
            } catch (IllegalArgumentException ignored) {}
        }

        return false;
    }
}
