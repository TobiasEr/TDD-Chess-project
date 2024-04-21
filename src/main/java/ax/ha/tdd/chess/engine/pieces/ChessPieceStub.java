package ax.ha.tdd.chess.engine.pieces;

import ax.ha.tdd.chess.engine.Chessboard;
import ax.ha.tdd.chess.engine.Square;
import ax.ha.tdd.chess.engine.Color;

/**
 *  Stub class in order to output a correct chessboard.
 *  This class should be deleted after implementations are done.
 */
public class ChessPieceStub extends ChessPieceBase {

    public ChessPieceStub(final PieceType symbol, final Color player) {
        this(symbol, player, new Square(0,0));
    }

    public ChessPieceStub(final PieceType symbol,
                          final Color player,
                          final Square location) {
        super(symbol, player, location);
    }

    @Override
    public boolean canMove(Chessboard chessboard, Square destination) {
        throw new UnsupportedOperationException("canMove has/will not be implemented in stub class");
    }
}
