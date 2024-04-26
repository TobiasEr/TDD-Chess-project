package ax.ha.tdd.chess.engine;

import ax.ha.tdd.chess.engine.pieces.King;
import ax.ha.tdd.chess.engine.pieces.Knight;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class KnightTests {

    @Test
    public void testKnightAllPossibleMoves() {
        Chessboard chessboard = new ChessboardImpl();
        Knight knight = new Knight(Color.WHITE, new Square("e4"));
        chessboard.addPiece(knight);

        assertTrue(knight.canMove(chessboard, new Square("c5")));
        assertTrue(knight.canMove(chessboard, new Square("c3")));
        assertTrue(knight.canMove(chessboard, new Square("d6")));
        assertTrue(knight.canMove(chessboard, new Square("f6")));
        assertTrue(knight.canMove(chessboard, new Square("g5")));
        assertTrue(knight.canMove(chessboard, new Square("g3")));
        assertTrue(knight.canMove(chessboard, new Square("f2")));
        assertTrue(knight.canMove(chessboard, new Square("d2")));
    }

    @Test
    public void testKnightIllegalMove() {
        Chessboard chessboard = new ChessboardImpl();
        Knight knight = new Knight(Color.WHITE, new Square("e4"));
        chessboard.addPiece(knight);

        assertFalse(knight.canMove(chessboard, new Square("f5")));
    }

    @Test
    public void testKnightMoveOverOtherPieceShouldBeAllowed() {
        Chessboard chessboard = new ChessboardImpl();
        Knight knightMoving = new Knight(Color.WHITE, new Square("e4"));
        Knight knightStanding = new Knight(Color.WHITE, new Square("e5"));
        chessboard.addPiece(knightMoving);
        chessboard.addPiece(knightStanding);

        assertTrue(knightMoving.canMove(chessboard, new Square("d6")));
    }

    @Test
    public void testKnightMoveToSpotOccupiedBySamePlayerShouldBeIllegal() {
        Chessboard chessboard = new ChessboardImpl();
        Knight knightMoving = new Knight(Color.WHITE, new Square("e4"));
        Knight knightStanding = new Knight(Color.WHITE, new Square("d6"));
        chessboard.addPiece(knightMoving);
        chessboard.addPiece(knightStanding);

        assertFalse(knightMoving.canMove(chessboard, new Square("d6")));
    }
    @Test
    public void testKnightCanCapturePieceOnCaptureMove() {
        Chessboard chessboard = new ChessboardImpl();
        Knight knightMoving = new Knight(Color.WHITE, new Square("e4"));
        Knight knightStanding = new Knight(Color.BLACK, new Square("d6"));
        chessboard.addPiece(knightMoving);
        chessboard.addPiece(knightStanding);

        assertTrue(knightMoving.canMove(chessboard, new Square("d6")));
    }
    @Test
    public void testKnightCanNotCaptureKingOnCaptureMove() {
        Chessboard chessboard = new ChessboardImpl();
        Knight knightMoving = new Knight(Color.WHITE, new Square("e4"));
        King kingStanding = new King(Color.BLACK, new Square("d6"));
        chessboard.addPiece(knightMoving);
        chessboard.addPiece(kingStanding);

        assertFalse(knightMoving.canMove(chessboard, new Square("d6")));
    }

}
