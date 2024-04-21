package ax.ha.tdd.chess.console;

import ax.ha.tdd.chess.engine.ChessboardImpl;
import ax.ha.tdd.chess.engine.Square;
import ax.ha.tdd.chess.engine.Color;
import ax.ha.tdd.chess.engine.pieces.ChessPieceStub;
import ax.ha.tdd.chess.engine.pieces.PieceType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ChessboardWriterTests {

    @Test
    public void testPrintEmptyChessboard() {
        final String expectedChessboard =
                """
                         *    *    *    *    *    *    *    *\s

                         *    *    *    *    *    *    *    *\s

                         *    *    *    *    *    *    *    *\s

                         *    *    *    *    *    *    *    *\s

                         *    *    *    *    *    *    *    *\s

                         *    *    *    *    *    *    *    *\s

                         *    *    *    *    *    *    *    *\s

                         *    *    *    *    *    *    *    *\s
                        """;
        Assertions.assertEquals(expectedChessboard, new ChessboardWriter().print(new ChessboardImpl()));
    }

    @Test
    public void testPrintChessboardWithSinglePawn() {
        final String expectedChessboard =
                """
                         *    *    *    *    *    *    *    *\s

                         *    *    *    *    *    *    *    *\s

                         *    *    *    *    *    *    *    *\s

                         *    *    *    *    *    *    *    *\s

                         *    *   B-P   *    *    *    *    *\s

                         *    *    *    *    *    *    *    *\s

                         *    *    *    *    *    *    *    *\s

                         *    *    *    *    *    *    *    *\s
                        """;
        ChessboardImpl chessboard = new ChessboardImpl();
        chessboard.addPiece(new ChessPieceStub(PieceType.PAWN, Color.BLACK, new Square(2, 4)));
        Assertions.assertEquals(expectedChessboard, new ChessboardWriter().print(chessboard));
    }

    @Test
    public void testPrintStartingBoard() {
        final String expectedChessboard =
                """
                        B-R  B-k  B-B  B-Q  B-K  B-B  B-k  B-R

                        B-P  B-P  B-P  B-P  B-P  B-P  B-P  B-P

                         *    *    *    *    *    *    *    *\s

                         *    *    *    *    *    *    *    *\s

                         *    *    *    *    *    *    *    *\s

                         *    *    *    *    *    *    *    *\s

                        W-P  W-P  W-P  W-P  W-P  W-P  W-P  W-P

                        W-R  W-k  W-B  W-Q  W-K  W-B  W-k  W-R
                                """;
        final ChessboardImpl chessboard = ChessboardImpl.startingBoard();

        Assertions.assertEquals(expectedChessboard, new ChessboardWriter().print(chessboard));
    }
}
