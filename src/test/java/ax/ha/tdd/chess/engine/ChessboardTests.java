package ax.ha.tdd.chess.engine;

import ax.ha.tdd.chess.console.ChessboardWriter;
import ax.ha.tdd.chess.engine.pieces.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

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
                assertEquals(Color.WHITE, chessboard.getPieceAt(new Square(x, y)).getColor());
            }
        }
    }

    @Test
    public void testStartingBoardBlackPiecesInTheRightSpot() {
        final Chessboard chessboard = ChessboardImpl.startingBoard();
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 2; y++) {
                assertEquals(Color.BLACK, chessboard.getPieceAt(new Square(x, y)).getColor());
            }
        }
    }

    @Test
    public void testStartingBoardPawnsInTheRightSpot() {
        final Chessboard chessboard = ChessboardImpl.startingBoard();
        for (int x = 0; x < 8; x++) {
            assertEquals("P", chessboard.getPieceAt(new Square(x, 1)).getSymbol());
            assertEquals("P", chessboard.getPieceAt(new Square(x, 6)).getSymbol());
        }
    }

    @Test
    public void testStartingBoardRooksInTheRightSpot() {
        final Chessboard chessboard = ChessboardImpl.startingBoard();
        assertEquals("R", chessboard.getPieceAt(new Square(0, 0)).getSymbol());
        assertEquals("R", chessboard.getPieceAt(new Square(7, 0)).getSymbol());
        assertEquals("R", chessboard.getPieceAt(new Square(0, 7)).getSymbol());
        assertEquals("R", chessboard.getPieceAt(new Square(7, 7)).getSymbol());
    }

    @Test
    public void testStartingBoardKnightsInTheRightSpot() {
        final Chessboard chessboard = ChessboardImpl.startingBoard();
        assertEquals("k", chessboard.getPieceAt(new Square(1, 0)).getSymbol());
        assertEquals("k", chessboard.getPieceAt(new Square(6, 0)).getSymbol());
        assertEquals("k", chessboard.getPieceAt(new Square(1, 7)).getSymbol());
        assertEquals("k", chessboard.getPieceAt(new Square(6, 7)).getSymbol());
    }

    @Test
    public void testStartingBoardBishopsInTheRightSpot() {
        final Chessboard chessboard = ChessboardImpl.startingBoard();
        assertEquals("B", chessboard.getPieceAt(new Square(2, 0)).getSymbol());
        assertEquals("B", chessboard.getPieceAt(new Square(5, 0)).getSymbol());
        assertEquals("B", chessboard.getPieceAt(new Square(2, 7)).getSymbol());
        assertEquals("B", chessboard.getPieceAt(new Square(5, 7)).getSymbol());
    }

    @Test
    public void testStartingBoardKingsInTheRightSpot() {
        final Chessboard chessboard = ChessboardImpl.startingBoard();
        assertEquals("K", chessboard.getPieceAt(new Square(4, 0)).getSymbol());
        assertEquals("K", chessboard.getPieceAt(new Square(4, 7)).getSymbol());
    }

    @Test
    public void testStartingBoardQueensInTheRightSpot() {
        final Chessboard chessboard = ChessboardImpl.startingBoard();
        assertEquals("Q", chessboard.getPieceAt(new Square(3, 0)).getSymbol());
        assertEquals("Q", chessboard.getPieceAt(new Square(3, 7)).getSymbol());
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

    @Test
    public void testGetOpponentKing() {
        Chessboard chessboard = new ChessboardImpl();
        King king = new King(Color.WHITE, new Square("e4"));
        King opponentKing = new King(Color.BLACK, new Square("h2"));
        chessboard.addPiece(king);
        chessboard.addPiece(opponentKing);
        ChessPiece opponentKingFetched = chessboard.getOpponentKing(king.getColor());

        assertSame(opponentKingFetched.getType(), PieceType.KING);
        assertSame(opponentKingFetched.getColor(), Color.BLACK);
        assertEquals(opponentKingFetched.getLocation(), new Square("h2"));
    }

    @Test
    public void testIsKingChecked() {
        Chessboard chessboard = new ChessboardImpl();
        King king = new King(Color.WHITE, new Square("e4"));
        Rook rook = new Rook(Color.BLACK, new Square("b4"));
        chessboard.addPiece(king);
        chessboard.addPiece(rook);

        assertTrue(chessboard.isKingChecked(rook, king));
    }

    @Test
    public void testRookCheckmate() {
        Chessboard chessboard = new ChessboardImpl();
        King king = new King(Color.WHITE, new Square("c8"));
        Rook firstRook = new Rook(Color.BLACK, new Square("g8"));
        Rook secondRook = new Rook(Color.BLACK, new Square("h7"));
        chessboard.addPiece(king);
        chessboard.addPiece(firstRook);
        chessboard.addPiece(secondRook);

        assertFalse(king.canMove(chessboard, new Square("c8")));
        assertTrue(chessboard.isKingCheckmate(firstRook, king));

        System.out.println(new ChessboardWriter().print(chessboard));
    }

    @Test
    public void testRookCheckmateButWhiteRookCanBlock() {
        Chessboard chessboard = new ChessboardImpl();
        King king = new King(Color.WHITE, new Square("d8"));
        Rook whiteRook = new Rook(Color.WHITE, new Square("e4"));
        Rook firstBlackRook = new Rook(Color.BLACK, new Square("g8"));
        Rook secondBlackRook = new Rook(Color.BLACK, new Square("h7"));
        chessboard.addPiece(king);
        chessboard.addPiece(whiteRook);
        chessboard.addPiece(firstBlackRook);
        chessboard.addPiece(secondBlackRook);

        assertFalse(chessboard.isKingCheckmate(firstBlackRook, king));

        System.out.println(new ChessboardWriter().print(chessboard));
    }

    @Test
    public void testKingQueenCheckmate() {
        Chessboard chessboard = new ChessboardImpl();
        King whiteKing = new King(Color.WHITE, new Square("h2"));
        King blackKing = new King(Color.BLACK, new Square("f2"));
        Queen blackQueen = new Queen(Color.BLACK, new Square("g2"));
        chessboard.addPiece(whiteKing);
        chessboard.addPiece(blackKing);
        chessboard.addPiece(blackQueen);

        assertTrue(chessboard.isKingCheckmate(blackQueen, whiteKing));
    }

    @Test
    public void testKingTwoBishopsCheckmate() {
        Chessboard chessboard = new ChessboardImpl();
        King whiteKing = new King(Color.WHITE, new Square("h1"));
        King blackKing = new King(Color.BLACK, new Square("g3"));
        Bishop blackBishopOne = new Bishop(Color.BLACK, new Square("e3"));
        Bishop blackBishopTwo = new Bishop(Color.BLACK, new Square("d5"));
        chessboard.addPiece(whiteKing);
        chessboard.addPiece(blackKing);
        chessboard.addPiece(blackBishopOne);
        chessboard.addPiece(blackBishopTwo);

        System.out.println(new ChessboardWriter().print(chessboard));

        assertTrue(chessboard.isKingCheckmate(blackBishopTwo, whiteKing));
    }

    @Test
    public void testKingBishopKnightCheckmate() {
        Chessboard chessboard = new ChessboardImpl();
        King whiteKing = new King(Color.WHITE, new Square("a8"));
        King blackKing = new King(Color.BLACK, new Square("b6"));
        Bishop blackBishop = new Bishop(Color.BLACK, new Square("e4"));
        Knight blackKnight = new Knight(Color.BLACK, new Square("a6"));
        chessboard.addPiece(whiteKing);
        chessboard.addPiece(blackKing);
        chessboard.addPiece(blackBishop);
        chessboard.addPiece(blackKnight);

        assertTrue(chessboard.isKingCheckmate(blackBishop, whiteKing));

        System.out.println(new ChessboardWriter().print(chessboard));
    }

    @Test
    public void testTwoMoveFoolsMateCheckmate() {
        Chessboard chessboard = ChessboardImpl.startingBoard();
        ChessPiece whiteKing = chessboard.getPieceAt(new Square("e1"));
        Pawn blackPawn = new Pawn(Color.BLACK, new Square("e6"));
        Pawn WhitePawnOne = new Pawn(Color.BLACK, new Square("f3"));
        Pawn WhitePawnTwo = new Pawn(Color.BLACK, new Square("g4"));
        Queen blackQueen = new Queen(Color.BLACK, new Square("h4"));
        chessboard.removePieceAt(new Square("f2"));
        chessboard.removePieceAt(new Square("g2"));
        chessboard.removePieceAt(new Square("d8"));
        chessboard.removePieceAt(new Square("e7"));
        chessboard.addPiece(blackPawn);
        chessboard.addPiece(WhitePawnOne);
        chessboard.addPiece(WhitePawnTwo);
        chessboard.addPiece(blackQueen);

        assertTrue(chessboard.isKingCheckmate(blackQueen, whiteKing));

        System.out.println(new ChessboardWriter().print(chessboard));
    }
}
