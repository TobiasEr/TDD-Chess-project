package ax.ha.tdd.chess.engine.pieces;

import ax.ha.tdd.chess.engine.Chessboard;
import ax.ha.tdd.chess.engine.Color;
import ax.ha.tdd.chess.engine.Square;

import java.util.ArrayList;
import java.util.List;

public class Knight extends ChessPieceBase implements ChessPiece {

    int[][] movePatterns = {{-2,-1},{-2,1},{-1,-2},{1,-2},
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

        for (int[] moves : movePatterns) {
            try {
                Square square = new Square(location.getX() + moves[0], location.getY() + moves[1]);
                if (square.equals(destination)) {
                    return true;
                }
            } catch (IllegalArgumentException ignored) {}
        }

        return false;
    }

    @Override
    public List<Square> getPossibleMoves(Chessboard chessboard) {
        List<Square> possibleMoves = new ArrayList<>();
        for (int[] moves : movePatterns) {
            try {
                Square square = new Square(location.getX() + moves[0], location.getY() + moves[1]);
                if (canMove(chessboard, square)) {
                    possibleMoves.add(square);
                }
            } catch (IllegalArgumentException ignored) {}
        }
        return possibleMoves;
    }
}
