package ax.ha.tdd.chess.engine;

import ax.ha.tdd.chess.engine.pieces.King;
import ax.ha.tdd.chess.engine.pieces.Pawn;
import ax.ha.tdd.chess.engine.pieces.Rook;
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

        assertTrue(whiteKing.canMove(chessboard, new Square("c1")));
        assertTrue(blackKing.canMove(chessboard, new Square("g8")));
    }

    @Test
    public void kingCastlingWhenKingIsCheckShouldBeIllegal() {
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
}
