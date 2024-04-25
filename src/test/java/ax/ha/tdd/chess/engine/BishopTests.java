package ax.ha.tdd.chess.engine;

import ax.ha.tdd.chess.engine.pieces.Bishop;
import ax.ha.tdd.chess.engine.pieces.King;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BishopTests {

    @Test
    public void testBishopDiagonalMoveShouldBeAllowed() {
        Chessboard chessboard = new ChessboardImpl();
        Bishop bishopMoveUpRight = new Bishop(Color.WHITE, new Square("e5"));
        Bishop bishopMoveDownRight = new Bishop(Color.WHITE, new Square("e4"));
        Bishop bishopMoveDownLeft = new Bishop(Color.WHITE, new Square("d4"));
        Bishop bishopMoveUpLeft = new Bishop(Color.WHITE, new Square("d5"));
        chessboard.addPiece(bishopMoveUpRight);
        chessboard.addPiece(bishopMoveDownRight);
        chessboard.addPiece(bishopMoveDownLeft);
        chessboard.addPiece(bishopMoveUpLeft);

        assertTrue(bishopMoveUpRight.canMove(chessboard, new Square("h8")));
        assertTrue(bishopMoveDownRight.canMove(chessboard, new Square("h1")));
        assertTrue(bishopMoveDownLeft.canMove(chessboard, new Square("a1")));
        assertTrue(bishopMoveUpLeft.canMove(chessboard, new Square("a8")));
    }

    @Test
    public void testBishopVerticalMoveShouldBeIllegal() {
        Chessboard chessboard = new ChessboardImpl();
        Bishop bishopMovingUp = new Bishop(Color.WHITE, new Square("d1"));
        Bishop bishopMovingDown = new Bishop(Color.WHITE, new Square("e8"));
        chessboard.addPiece(bishopMovingUp);
        chessboard.addPiece(bishopMovingDown);

        assertFalse(bishopMovingUp.canMove(chessboard, new Square("d8")));
        assertFalse(bishopMovingDown.canMove(chessboard, new Square("e1")));
    }

    @Test
    public void testBishopHorizontalMoveShouldBeIllegal() {
        Chessboard chessboard = new ChessboardImpl();
        Bishop bishopMovingLeft = new Bishop(Color.WHITE, new Square("h6"));
        Bishop bishopMovingRight = new Bishop(Color.WHITE, new Square("a2"));
        chessboard.addPiece(bishopMovingLeft);
        chessboard.addPiece(bishopMovingRight);

        assertFalse(bishopMovingLeft.canMove(chessboard, new Square("a6")));
        assertFalse(bishopMovingRight.canMove(chessboard, new Square("h2")));
    }

    @Test
    public void testBishopMoveOverAnotherPieceShouldBeIllegal() {
        Chessboard chessboard = new ChessboardImpl();
        Bishop bishopMoving = new Bishop(Color.WHITE, new Square("a2"));
        Bishop bishopBlocking = new Bishop(Color.WHITE, new Square("c4"));
        chessboard.addPiece(bishopMoving);
        chessboard.addPiece(bishopBlocking);

        assertFalse(bishopMoving.canMove(chessboard, new Square("e6")));
    }

    @Test
    public void testBishopMoveToSpotOccupiedBySamePlayerShouldBeIllegal() {
        Chessboard chessboard = new ChessboardImpl();
        Bishop bishopMoving = new Bishop(Color.WHITE, new Square("a2"));
        Bishop bishopBlocking = new Bishop(Color.WHITE, new Square("c4"));
        chessboard.addPiece(bishopMoving);
        chessboard.addPiece(bishopBlocking);

        assertFalse(bishopMoving.canMove(chessboard, new Square("c4")));
    }

    @Test
    public void testBishopCanCapturePieceOnCaptureMove() {
        Chessboard chessboard = new ChessboardImpl();
        Bishop bishopMoving = new Bishop(Color.WHITE, new Square("a2"));
        Bishop bishopToCapture = new Bishop(Color.BLACK, new Square("c4"));
        chessboard.addPiece(bishopMoving);
        chessboard.addPiece(bishopToCapture);

        assertTrue(bishopMoving.canMove(chessboard, new Square("c4")));
    }

    @Test
    public void testBishopCanNotCaptureKing() {
        Chessboard chessboard = new ChessboardImpl();
        Bishop bishopMoving = new Bishop(Color.WHITE, new Square("a2"));
        King kingToCapture = new King(Color.BLACK, new Square("c4"));
        chessboard.addPiece(bishopMoving);
        chessboard.addPiece(kingToCapture);

        assertFalse(bishopMoving.canMove(chessboard, new Square("c4")));
    }
}
