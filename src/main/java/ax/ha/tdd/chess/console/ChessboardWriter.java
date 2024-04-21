package ax.ha.tdd.chess.console;

import ax.ha.tdd.chess.engine.Chessboard;
import ax.ha.tdd.chess.engine.Square;
import ax.ha.tdd.chess.engine.pieces.ChessPiece;

public class ChessboardWriter {

    public String print(final Chessboard chessboard) {
        final StringBuilder stringBuilder = new StringBuilder();
        for (int y = 0; y < 8 ; y++) {
            for (int x = 0; x < 8 ; x++) {
                final ChessPiece chessPiece = chessboard.getPieceAt(new Square(x, y));
                if (chessPiece == null) {
                    stringBuilder.append(" * ");
                }
                else {
                    stringBuilder.append(chessPiece.getColor().getSymbol())
                            .append("-")
                            .append(chessPiece.getSymbol());
                }

                if (x < 7) {
                    stringBuilder.append("  ");
                }
            }
            stringBuilder.append("\n");
            if (y < 7) {
                stringBuilder.append("\n");
            }
        }
        return stringBuilder.toString();
    }
}
