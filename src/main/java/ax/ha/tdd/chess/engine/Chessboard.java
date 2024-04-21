package ax.ha.tdd.chess.engine;

import ax.ha.tdd.chess.engine.pieces.ChessPiece;

public interface Chessboard extends Iterable<ChessPiece[]>{

    ChessPiece getPieceAt(final Square square);

    void addPiece(final ChessPiece chessPiece);

    void removePieceAt(final Square square);
}
