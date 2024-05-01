package ax.ha.tdd.chess.engine;

import ax.ha.tdd.chess.engine.pieces.ChessPiece;

public interface Chessboard extends Iterable<ChessPiece[]>{

    ChessPiece getPieceAt(final Square square);
    void addPiece(final ChessPiece chessPiece);
    void removePieceAt(final Square square);
    boolean isKingChecked(final ChessPiece attackingPiece, final ChessPiece opponentKing);
    ChessPiece getOpponentKing(final Color color);
    boolean isKingCheckmate(final ChessPiece attackingPiece, final ChessPiece opponentKing);
    void increaseMovesMade();
    int getMovesMade();
}
