package ax.ha.tdd.chess.engine;

import ax.ha.tdd.chess.engine.pieces.King;
import ax.ha.tdd.chess.engine.pieces.Queen;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class QueenTests {

    @Test
    public void testQueenHorizontalMoveShouldBeAllowed() {
        Chessboard chessboard = new ChessboardImpl();
        Queen queenMovingLeft = new Queen(Color.WHITE, new Square("h5"));
        Queen queenMovingRight = new Queen(Color.WHITE, new Square("a3"));
        chessboard.addPiece(queenMovingLeft);
        chessboard.addPiece(queenMovingRight);

        assertTrue(queenMovingLeft.canMove(chessboard, new Square("a5")));
        assertTrue(queenMovingRight.canMove(chessboard, new Square("h3")));
    }

    @Test
    public void testQueenVerticalMoveShouldBeAllowed() {
        Chessboard chessboard = new ChessboardImpl();
        Queen queenMovingUp = new Queen(Color.WHITE, new Square("d1"));
        Queen queenMovingDown = new Queen(Color.WHITE, new Square("e8"));
        chessboard.addPiece(queenMovingUp);
        chessboard.addPiece(queenMovingDown);

        assertTrue(queenMovingUp.canMove(chessboard, new Square("d8")));
        assertTrue(queenMovingDown.canMove(chessboard, new Square("e1")));
    }

    @Test
    public void testQueenDiagonalMoveShouldBeAllowed() {
        Chessboard chessboard = new ChessboardImpl();
        Queen queenMoveUpRight = new Queen(Color.WHITE, new Square("e5"));
        Queen queenMoveDownRight = new Queen(Color.WHITE, new Square("e4"));
        Queen queenMoveDownLeft = new Queen(Color.WHITE, new Square("d4"));
        Queen queenMoveUpLeft = new Queen(Color.WHITE, new Square("d5"));
        chessboard.addPiece(queenMoveUpRight);
        chessboard.addPiece(queenMoveDownRight);
        chessboard.addPiece(queenMoveDownLeft);
        chessboard.addPiece(queenMoveUpLeft);

        assertTrue(queenMoveUpRight.canMove(chessboard, new Square("h8")));
        assertTrue(queenMoveDownRight.canMove(chessboard, new Square("h1")));
        assertTrue(queenMoveDownLeft.canMove(chessboard, new Square("a1")));
        assertTrue(queenMoveUpLeft.canMove(chessboard, new Square("a8")));
    }

    @Test
    public void testQueenIllegalMove() {
        Chessboard chessboard = new ChessboardImpl();
        Queen queenPiece = new Queen(Color.WHITE, new Square("d1"));
        chessboard.addPiece(queenPiece);

        assertFalse(queenPiece.canMove(chessboard, new Square("f6")));
    }

    @Test
    public void testQueenMoveOverAnotherPieceShouldBeIllegal() {
        Chessboard chessboard = new ChessboardImpl();
        Queen queenMoving = new Queen(Color.WHITE, new Square("a2"));
        Queen queenBlocking = new Queen(Color.WHITE, new Square("c4"));
        chessboard.addPiece(queenMoving);
        chessboard.addPiece(queenBlocking);

        assertFalse(queenMoving.canMove(chessboard, new Square("e6")));
    }

    @Test
    public void testQueenMoveToSpotOccupiedBySamePlayerShouldBeIllegal() {
        Chessboard chessboard = new ChessboardImpl();
        Queen queenMoving = new Queen(Color.WHITE, new Square("a2"));
        Queen queenBlocking = new Queen(Color.WHITE, new Square("c4"));
        chessboard.addPiece(queenMoving);
        chessboard.addPiece(queenBlocking);

        assertFalse(queenMoving.canMove(chessboard, new Square("c4")));
    }

    @Test
    public void testQueenCanCapturePieceOnCaptureMove() {
        Chessboard chessboard = new ChessboardImpl();
        Queen queenMoving = new Queen(Color.WHITE, new Square("a2"));
        Queen queenToCapture = new Queen(Color.BLACK, new Square("c4"));
        chessboard.addPiece(queenMoving);
        chessboard.addPiece(queenToCapture);

        assertTrue(queenMoving.canMove(chessboard, new Square("c4")));
    }

    @Test
    public void testQueenCanNotCaptureKingOnCaptureMove() {
        Chessboard chessboard = new ChessboardImpl();
        Queen queenMoving = new Queen(Color.WHITE, new Square("a2"));
        King kingToCapture = new King(Color.BLACK, new Square("c4"));
        chessboard.addPiece(queenMoving);
        chessboard.addPiece(kingToCapture);

        assertFalse(queenMoving.canMove(chessboard, new Square("c4")));
    }
}
