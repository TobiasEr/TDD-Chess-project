package ax.ha.tdd.chess.engine.pieces;

import ax.ha.tdd.chess.engine.Chessboard;
import ax.ha.tdd.chess.engine.Square;
import ax.ha.tdd.chess.engine.Color;

public interface ChessPiece {
    /**
     * The graphical symbol to use for display of this piece
     */
    String getSymbol();

    /**
     * Is it a rook, pawn, queen, etc?
     */
    PieceType getType();

    /**
     * White or black?
     */
    Color getColor();

    /**
     * On what square is this piece?
     */
    Square getLocation();

    /**
     * Can the piece move to the destination square on this chessboard?
     */
    boolean canMove(Chessboard chessboard, Square destination);
}
