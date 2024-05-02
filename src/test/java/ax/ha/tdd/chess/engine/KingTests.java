package ax.ha.tdd.chess.engine;

import ax.ha.tdd.chess.console.ChessboardWriter;
import ax.ha.tdd.chess.engine.pieces.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class KingTests {

    @Test
    public void testKingMoveOneStepAllDirectionsShouldBeAllowed() {
        Chessboard chessboard = new ChessboardImpl();
        King king = new King(Color.WHITE, new Square("e4"));
        chessboard.addPiece(king);

        assertTrue(king.canMove(chessboard, new Square("d5")));
        assertTrue(king.canMove(chessboard, new Square("e5")));
        assertTrue(king.canMove(chessboard, new Square("f5")));
        assertTrue(king.canMove(chessboard, new Square("f4")));
        assertTrue(king.canMove(chessboard, new Square("f3")));
        assertTrue(king.canMove(chessboard, new Square("e3")));
        assertTrue(king.canMove(chessboard, new Square("d3")));
        assertTrue(king.canMove(chessboard, new Square("d4")));
    }

    @Test
    public void testKingIllegalMove() {
        Chessboard chessboard = new ChessboardImpl();
        King king = new King(Color.WHITE, new Square("e4"));
        chessboard.addPiece(king);

        assertFalse(king.canMove(chessboard, new Square("d6")));
    }

    @Test
    public void testKingCanNotMoveToDangerousSpot() {
        Chessboard chessboard = new ChessboardImpl();
        King king = new King(Color.WHITE, new Square("e4"));
        Pawn pawn = new Pawn(Color.BLACK, new Square("c6"));
        chessboard.addPiece(king);
        chessboard.addPiece(pawn);

        assertFalse(king.canMove(chessboard, new Square("d5")));
    }

    @Test
    public void testKingCanNotCaptureOnDangerousSpot() {
        Chessboard chessboard = new ChessboardImpl();
        King king = new King(Color.WHITE, new Square("e6"));
        Pawn firstPawn = new Pawn(Color.BLACK, new Square("c6"));
        Pawn secondPawn = new Pawn(Color.BLACK, new Square("d5"));
        chessboard.addPiece(king);
        chessboard.addPiece(firstPawn);
        chessboard.addPiece(secondPawn);

        assertFalse(king.canMove(chessboard, new Square("d5")));
    }

    @Test
    public void testKingCanCapture() {
        Chessboard chessboard = new ChessboardImpl();
        King king = new King(Color.WHITE, new Square("e4"));
        Pawn pawn = new Pawn(Color.BLACK, new Square("d5"));
        chessboard.addPiece(king);
        chessboard.addPiece(pawn);

        assertTrue(king.canMove(chessboard, new Square("d5")));
    }

    @Test
    public void testKingMoveToSpotOccupiedBySamePlayerShouldBeIllegal() {
        Chessboard chessboard = new ChessboardImpl();
        King king = new King(Color.WHITE, new Square("e4"));
        Pawn pawn = new Pawn(Color.WHITE, new Square("d5"));
        chessboard.addPiece(king);
        chessboard.addPiece(pawn);

        assertFalse(king.canMove(chessboard, new Square("d5")));
    }

    @Test
    public void testKingCanNotCaptureKingOnCaptureMove() {
        Chessboard chessboard = new ChessboardImpl();
        King firstKing = new King(Color.WHITE, new Square("e4"));
        King secondKing = new King(Color.BLACK, new Square("d5"));
        chessboard.addPiece(firstKing);
        chessboard.addPiece(secondKing);

        assertFalse(firstKing.canMove(chessboard, new Square("d5")));
    }

    @Test
    public void testKingCanNotGoCloseToKing() {
        Chessboard chessboard = new ChessboardImpl();
        King firstKing = new King(Color.WHITE, new Square("e4"));
        King secondKing = new King(Color.BLACK, new Square("e6"));
        chessboard.addPiece(firstKing);
        chessboard.addPiece(secondKing);

        assertFalse(firstKing.canMove(chessboard, new Square("e5")));
    }

    @Test
    public void testKingCastling() {
        Chessboard chessboard = new ChessboardImpl();
        King whiteKing = new King(Color.WHITE, new Square("e1"));
        Rook whiteRook = new Rook(Color.WHITE, new Square("a1"));
        King blackKing = new King(Color.BLACK, new Square("e8"));
        Rook blackRook = new Rook(Color.BLACK, new Square("h8"));
        chessboard.addPiece(whiteKing);
        chessboard.addPiece(whiteRook);
        chessboard.addPiece(blackKing);
        chessboard.addPiece(blackRook);

        System.out.println(new ChessboardWriter().print(chessboard));

        assertTrue(whiteKing.canMove(chessboard, new Square("c1")));
        assertTrue(blackKing.canMove(chessboard, new Square("g8")));
    }

    @Test
    public void kingCastlingWhenKingIsCheckedShouldBeIllegal() {
        Chessboard chessboard = new ChessboardImpl();
        King whiteKing = new King(Color.WHITE, new Square("e1"));
        Rook whiteRook = new Rook(Color.WHITE, new Square("a1"));
        Rook blackRook = new Rook(Color.BLACK, new Square("e8"));
        chessboard.addPiece(whiteKing);
        chessboard.addPiece(whiteRook);
        chessboard.addPiece(blackRook);
        whiteKing.setPieceChecked(true);

        assertFalse(whiteKing.canMove(chessboard, new Square("c1")));
    }

    @Test
    public void kingCastlingWhenDestinationDangerousShouldBeIllegal() {
        Chessboard chessboard = new ChessboardImpl();
        King whiteKing = new King(Color.WHITE, new Square("e1"));
        Rook whiteRook = new Rook(Color.WHITE, new Square("a1"));
        Rook blackRook = new Rook(Color.BLACK, new Square("d8"));
        chessboard.addPiece(whiteKing);
        chessboard.addPiece(whiteRook);
        chessboard.addPiece(blackRook);

        assertFalse(whiteKing.canMove(chessboard, new Square("c1")));
    }

    @Test
    public void kingCastlingWhenRookHasMovedShouldBeIllegal() {
        Chessboard chessboard = new ChessboardImpl();
        King whiteKing = new King(Color.WHITE, new Square("e1"));
        Rook whiteRook = new Rook(Color.WHITE, new Square("a1"));
        chessboard.addPiece(whiteKing);
        chessboard.addPiece(whiteRook);

        // Simulates moving twice
        if (whiteRook.canMove(chessboard, new Square("a3")))
            whiteRook.canMove(chessboard, new Square("a1"));

        assertFalse(whiteKing.canMove(chessboard, new Square("c1")));
    }

    @Test
    public void kingCastlingWhenKingHasMovedShouldBeIllegal() {
        Chessboard chessboard = new ChessboardImpl();
        King whiteKing = new King(Color.WHITE, new Square("e1"));
        Rook whiteRook = new Rook(Color.WHITE, new Square("a1"));
        chessboard.addPiece(whiteKing);
        chessboard.addPiece(whiteRook);

        // Simulates moving twice
        if (whiteKing.canMove(chessboard, new Square("e2")))
            whiteKing.canMove(chessboard, new Square("e1"));

        assertFalse(whiteKing.canMove(chessboard, new Square("c1")));
    }

    @Test
    public void testIsKingCheckedByRook() {
        Chessboard chessboard = new ChessboardImpl();
        King king = new King(Color.WHITE, new Square("e4"));
        Rook rook = new Rook(Color.BLACK, new Square("b4"));
        chessboard.addPiece(king);
        chessboard.addPiece(rook);

        assertTrue(king.isKingChecked(chessboard));
    }

    @Test
    public void testIsKingCheckedByKing() {
        Chessboard chessboard = new ChessboardImpl();
        King whiteKing = new King(Color.WHITE, new Square("e4"));
        King blackKing = new King(Color.BLACK, new Square("d5"));
        chessboard.addPiece(whiteKing);
        chessboard.addPiece(blackKing);

        assertTrue(whiteKing.isKingChecked(chessboard));
    }

    @Test
    public void testIsKingCheckedByPawn() {
        Chessboard chessboard = new ChessboardImpl();
        King king = new King(Color.WHITE, new Square("e4"));
        Pawn pawn = new Pawn(Color.BLACK, new Square("d5"));
        chessboard.addPiece(king);
        chessboard.addPiece(pawn);

        assertTrue(king.isKingChecked(chessboard));
    }

    @Test
    public void testIsKingNotChecked() {
        Chessboard chessboard = new ChessboardImpl();
        King king = new King(Color.WHITE, new Square("e4"));
        Pawn pawn = new Pawn(Color.BLACK, new Square("d6"));
        chessboard.addPiece(king);
        chessboard.addPiece(pawn);

        assertFalse(king.isKingChecked(chessboard));
    }

    @Test
    public void testRookCheckmate() {
        Chessboard chessboard = new ChessboardImpl();
        King king = new King(Color.WHITE, new Square("c8"));
        Rook firstRook = new Rook(Color.BLACK, new Square("g8"));
        Rook secondRook = new Rook(Color.BLACK, new Square("h7"));
        Pawn pawn = new Pawn(Color.BLACK, new Square("e2"));
        chessboard.addPiece(king);
        chessboard.addPiece(firstRook);
        chessboard.addPiece(secondRook);
        chessboard.addPiece(pawn);

        System.out.println(new ChessboardWriter().print(chessboard));

        assertFalse(king.canMove(chessboard, new Square("c8")));
        assertTrue(king.isKingCheckmate(chessboard));

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

        System.out.println(new ChessboardWriter().print(chessboard));

        assertFalse(king.isKingCheckmate(chessboard));
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

        assertTrue(whiteKing.isKingCheckmate(chessboard));
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

        assertTrue(whiteKing.isKingCheckmate(chessboard));
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

        System.out.println(new ChessboardWriter().print(chessboard));

        assertTrue(whiteKing.isKingCheckmate(chessboard));
    }

    @Test
    public void testTwoMoveFoolsMateCheckmate() {
        Chessboard chessboard = ChessboardImpl.startingBoard();
        King whiteKing = (King) chessboard.getPieceAt(new Square("e1"));
        Pawn blackPawn = new Pawn(Color.BLACK, new Square("e6"));
        Pawn WhitePawnOne = new Pawn(Color.WHITE, new Square("f3"));
        Pawn WhitePawnTwo = new Pawn(Color.WHITE, new Square("g4"));
        Queen blackQueen = new Queen(Color.BLACK, new Square("h4"));
        chessboard.removePieceAt(new Square("f2"));
        chessboard.removePieceAt(new Square("g2"));
        chessboard.removePieceAt(new Square("d8"));
        chessboard.removePieceAt(new Square("e7"));
        chessboard.addPiece(blackPawn);
        chessboard.addPiece(WhitePawnOne);
        chessboard.addPiece(WhitePawnTwo);
        chessboard.addPiece(blackQueen);

        System.out.println(new ChessboardWriter().print(chessboard));

        assertTrue(whiteKing.isKingCheckmate(chessboard));
    }
}
