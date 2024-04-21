package ax.ha.tdd.chess.engine;

import ax.ha.tdd.chess.engine.pieces.ChessPiece;
import ax.ha.tdd.chess.engine.pieces.ChessPieceStub;
import ax.ha.tdd.chess.engine.pieces.PieceType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ChessboardTests {

    @Test
    public void testNewChessboardIsEmpty() {
        final Chessboard chessboard = new ChessboardImpl();
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                Assertions.assertNull(chessboard.getPieceAt(new Square(x, y)));
            }
        }
    }

    @Test
    public void testStartingBoardWhitePiecesInTheRightSpot() {
        final Chessboard chessboard = ChessboardImpl.startingBoard();
        for (int x = 0; x < 8; x++) {
            for (int y = 6; y < 8; y++) {
                Assertions.assertEquals(Color.WHITE, chessboard.getPieceAt(new Square(x, y)).getColor());
            }
        }
    }

    @Test
    public void testStartingBoardBlackPiecesInTheRightSpot() {
        final Chessboard chessboard = ChessboardImpl.startingBoard();
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 2; y++) {
                Assertions.assertEquals(Color.BLACK, chessboard.getPieceAt(new Square(x, y)).getColor());
            }
        }
    }

    @Test
    public void testStartingBoardPawnsInTheRightSpot() {
        final Chessboard chessboard = ChessboardImpl.startingBoard();
        for (int x = 0; x < 8; x++) {
            Assertions.assertEquals("P", chessboard.getPieceAt(new Square(x, 1)).getSymbol());
            Assertions.assertEquals("P", chessboard.getPieceAt(new Square(x, 6)).getSymbol());
        }
    }

    @Test
    public void testStartingBoardRooksInTheRightSpot() {
        final Chessboard chessboard = ChessboardImpl.startingBoard();
        Assertions.assertEquals("R", chessboard.getPieceAt(new Square(0, 0)).getSymbol());
        Assertions.assertEquals("R", chessboard.getPieceAt(new Square(7, 0)).getSymbol());
        Assertions.assertEquals("R", chessboard.getPieceAt(new Square(0, 7)).getSymbol());
        Assertions.assertEquals("R", chessboard.getPieceAt(new Square(7, 7)).getSymbol());
    }

    @Test
    public void testStartingBoardKnightsInTheRightSpot() {
        final Chessboard chessboard = ChessboardImpl.startingBoard();
        Assertions.assertEquals("k", chessboard.getPieceAt(new Square(1, 0)).getSymbol());
        Assertions.assertEquals("k", chessboard.getPieceAt(new Square(6, 0)).getSymbol());
        Assertions.assertEquals("k", chessboard.getPieceAt(new Square(1, 7)).getSymbol());
        Assertions.assertEquals("k", chessboard.getPieceAt(new Square(6, 7)).getSymbol());
    }

    @Test
    public void testStartingBoardBishopsInTheRightSpot() {
        final Chessboard chessboard = ChessboardImpl.startingBoard();
        Assertions.assertEquals("B", chessboard.getPieceAt(new Square(2, 0)).getSymbol());
        Assertions.assertEquals("B", chessboard.getPieceAt(new Square(5, 0)).getSymbol());
        Assertions.assertEquals("B", chessboard.getPieceAt(new Square(2, 7)).getSymbol());
        Assertions.assertEquals("B", chessboard.getPieceAt(new Square(5, 7)).getSymbol());
    }

    @Test
    public void testStartingBoardKingsInTheRightSpot() {
        final Chessboard chessboard = ChessboardImpl.startingBoard();
        Assertions.assertEquals(new ChessPieceStub(PieceType.KING, Color.BLACK), chessboard.getPieceAt(new Square(4, 0)));
        Assertions.assertEquals(new ChessPieceStub(PieceType.KING, Color.WHITE), chessboard.getPieceAt(new Square(4, 7)));
    }

    @Test
    public void testStartingBoardQueensInTheRightSpot() {
        final Chessboard chessboard = ChessboardImpl.startingBoard();
        Assertions.assertEquals(new ChessPieceStub(PieceType.QUEEN, Color.BLACK), chessboard.getPieceAt(new Square(3, 0)));
        Assertions.assertEquals(new ChessPieceStub(PieceType.QUEEN, Color.WHITE), chessboard.getPieceAt(new Square(3, 7)));
    }

    @Test
    public void testChessboardIteratesByRows(){
        final Chessboard chessboard = ChessboardImpl.startingBoard();

        Iterator<ChessPiece[]> iterator = chessboard.iterator();
        ChessPiece[] row8 = iterator.next();
        assertTrue(Arrays.stream(row8).allMatch(Objects::nonNull));
        ChessPiece[] row7 = iterator.next();
        assertTrue(Arrays.stream(row7).allMatch(Objects::nonNull));

        for (int i = 0 ; i < 4 ; i++){
            ChessPiece[] middleRow = iterator.next();
            assertTrue(Arrays.stream(middleRow).allMatch(Objects::isNull));
        }

        ChessPiece[] row2 = iterator.next();
        assertTrue(Arrays.stream(row2).allMatch(Objects::nonNull));
        ChessPiece[] row1 = iterator.next();
        assertTrue(Arrays.stream(row1).allMatch(Objects::nonNull));
    }
}
