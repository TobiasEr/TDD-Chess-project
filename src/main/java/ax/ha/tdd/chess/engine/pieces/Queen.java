package ax.ha.tdd.chess.engine.pieces;

import ax.ha.tdd.chess.engine.Chessboard;
import ax.ha.tdd.chess.engine.Color;
import ax.ha.tdd.chess.engine.Square;

import java.util.ArrayList;
import java.util.List;

public class Queen extends ChessPieceBase implements ChessPiece {

    public Queen(Color player, Square location) {
        super(PieceType.QUEEN, player, location);
    }

    @Override
    public boolean canMove(Chessboard chessboard, Square destination) {
        Bishop bishop = new Bishop(color, location);
        Rook rook = new Rook(color, location);

        return bishop.canMove(chessboard, destination) || rook.canMove(chessboard, destination);
    }

    @Override
    public List<Square> getPossibleMoves(Chessboard chessboard) {
        List<Square> possibleMoves = new ArrayList<>();
        Bishop bishop = new Bishop(color, location);
        Rook rook = new Rook(color, location);

        possibleMoves.addAll(bishop.getPossibleMoves(chessboard));
        possibleMoves.addAll(rook.getPossibleMoves(chessboard));

        return possibleMoves;
    }
}
