package ax.ha.tdd.chess.engine;

import ax.ha.tdd.chess.engine.pieces.King;
import ax.ha.tdd.chess.engine.pieces.Rook;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RookTests {

    @Test
    public void testRookHorizontalMoveShouldBeAllowed() {
        Chessboard chessboard = new ChessboardImpl();
        Rook rookMovingLeft = new Rook(Color.WHITE, new Square("h5"));
        Rook rookMovingRight = new Rook(Color.WHITE, new Square("a3"));
        chessboard.addPiece(rookMovingLeft);
        chessboard.addPiece(rookMovingRight);

        assertTrue(rookMovingLeft.canMove(chessboard, new Square("a5")));
        assertTrue(rookMovingRight.canMove(chessboard, new Square("h3")));
    }

    @Test
    public void testRookVerticalMoveShouldBeAllowed() {
        Chessboard chessboard = new ChessboardImpl();
        Rook rookMovingUp = new Rook(Color.WHITE, new Square("d1"));
        Rook rookMovingDown = new Rook(Color.WHITE, new Square("e8"));
        chessboard.addPiece(rookMovingUp);
        chessboard.addPiece(rookMovingDown);

        assertTrue(rookMovingUp.canMove(chessboard, new Square("d8")));
        assertTrue(rookMovingDown.canMove(chessboard, new Square("e1")));
    }

    @Test
    public void testRookDiagonalMoveShouldBeIllegal() {
        Chessboard chessboard = new ChessboardImpl();
        Rook rookMoveUpRight = new Rook(Color.WHITE, new Square("e5"));
        Rook rookMoveDownRight = new Rook(Color.WHITE, new Square("e4"));
        Rook rookMoveDownLeft = new Rook(Color.WHITE, new Square("d4"));
        Rook rookMoveUpLeft = new Rook(Color.WHITE, new Square("d5"));
        chessboard.addPiece(rookMoveUpRight);
        chessboard.addPiece(rookMoveDownRight);
        chessboard.addPiece(rookMoveDownLeft);
        chessboard.addPiece(rookMoveUpLeft);

        assertFalse(rookMoveUpRight.canMove(chessboard, new Square("h8")));
        assertFalse(rookMoveDownRight.canMove(chessboard, new Square("h1")));
        assertFalse(rookMoveDownLeft.canMove(chessboard, new Square("a1")));
        assertFalse(rookMoveUpLeft.canMove(chessboard, new Square("a8")));
    }

    @Test
    public void testRookMoveOverAnotherPieceShouldBeIllegal() {
        Chessboard chessboard = new ChessboardImpl();
        Rook rookMoving = new Rook(Color.WHITE, new Square("a4"));
        Rook rookStanding = new Rook(Color.WHITE, new Square("d4"));
        chessboard.addPiece(rookMoving);
        chessboard.addPiece(rookStanding);

        assertFalse(rookMoving.canMove(chessboard, new Square("h4")));
    }

    @Test
    public void testRookMoveToSpotOccupiedBySamePlayerShouldBeIllegal() {
        Chessboard chessboard = new ChessboardImpl();
        Rook firstRook = new Rook(Color.WHITE, new Square("a4"));
        Rook secondRook = new Rook(Color.WHITE, new Square("d4"));
        chessboard.addPiece(firstRook);
        chessboard.addPiece(secondRook);

        assertFalse(firstRook.canMove(chessboard, new Square("d4")));
    }

    @Test
    public void testRookCanCapturePieceOnCaptureMove() {
        Chessboard chessboard = new ChessboardImpl();
        Rook firstRook = new Rook(Color.WHITE, new Square("f2"));
        Rook secondRook = new Rook(Color.BLACK, new Square("f4"));
        chessboard.addPiece(firstRook);
        chessboard.addPiece(secondRook);

        assertTrue(firstRook.canMove(chessboard, new Square("f4")));
    }

    @Test
    public void testRookCanNotCaptureKing() {
        Chessboard chessboard = new ChessboardImpl();
        Rook rookPiece = new Rook(Color.WHITE, new Square("a4"));
        King kingToCapture = new King(Color.WHITE, new Square("d4"));
        chessboard.addPiece(rookPiece);
        chessboard.addPiece(kingToCapture);

        assertFalse(rookPiece.canMove(chessboard, new Square("d4")));
    }
}
