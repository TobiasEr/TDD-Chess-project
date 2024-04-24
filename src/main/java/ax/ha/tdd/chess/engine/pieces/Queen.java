package ax.ha.tdd.chess.engine.pieces;

import ax.ha.tdd.chess.engine.Chessboard;
import ax.ha.tdd.chess.engine.Color;
import ax.ha.tdd.chess.engine.Square;

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
}
