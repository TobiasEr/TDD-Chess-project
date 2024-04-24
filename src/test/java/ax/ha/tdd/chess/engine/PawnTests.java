package ax.ha.tdd.chess.engine;

import ax.ha.tdd.chess.console.ChessboardWriter;
import ax.ha.tdd.chess.engine.pieces.Pawn;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PawnTests {

    @Test
    public void testMovePawnBackwardsShouldBeIllegal() {
        //Arrange
        Chessboard chessboard = new ChessboardImpl();
        Pawn pawn = new Pawn(Color.WHITE, new Square("e2"));
        chessboard.addPiece(pawn);

        //Assert
        assertFalse(pawn.canMove(chessboard, new Square("e1")));

        //For debugging, you can print the board to console, or if you want
        //to implement a command line interface for the game
        System.out.println(new ChessboardWriter().print(chessboard));
    }

    @Test
    public void testMovePawnOneStepForward() {
        Chessboard chessboard = new ChessboardImpl();
        Pawn pawn = new Pawn(Color.WHITE, new Square("f3"));
        chessboard.addPiece(pawn);

        assertTrue(pawn.canMove(chessboard, new Square("f4")));
    }

    @Test
    public void testMovePawnTwoStepsFromStartingPoint() {
        Chessboard chessboard = new ChessboardImpl();
        Pawn pawn = new Pawn(Color.WHITE, new Square("e2"));
        chessboard.addPiece(pawn);

        assertTrue(pawn.canMove(chessboard, new Square("e4")));
    }

    @Test
    public void testMovePawnTwoStepsNotFromStartingPointShouldBeIllegal() {
        Chessboard chessboard = new ChessboardImpl();
        Pawn pawn = new Pawn(Color.WHITE, new Square("e4"));
        chessboard.addPiece(pawn);

        assertFalse(pawn.canMove(chessboard, new Square("e6")));
    }

    @Test
    public void testMovePawnHorizontallyShouldBeIllegal() {
        Chessboard chessboard = new ChessboardImpl();
        Pawn pawn = new Pawn(Color.WHITE, new Square("e4"));
        chessboard.addPiece(pawn);

        assertFalse(pawn.canMove(chessboard, new Square("d4")));
    }

    @Test
    public void testMoveToSpotOccupiedBySamePlayerShouldBeIllegal() {
        Chessboard chessboard = new ChessboardImpl();
        Pawn firstPawn = new Pawn(Color.WHITE, new Square("e4"));
        Pawn secondPawn = new Pawn(Color.WHITE, new Square("e5"));
        chessboard.addPiece(firstPawn);
        chessboard.addPiece(secondPawn);

        assertFalse(firstPawn.canMove(chessboard, new Square("e5")));
    }

    @Test
    public void testCanNotMovePastPieceOnInitialTwoStepMove() {
        Chessboard chessboard = new ChessboardImpl();
        Pawn firstPawn = new Pawn(Color.WHITE, new Square("e2"));
        Pawn secondPawn = new Pawn(Color.WHITE, new Square("e3"));
        chessboard.addPiece(firstPawn);
        chessboard.addPiece(secondPawn);

        assertFalse(firstPawn.canMove(chessboard, new Square("e4")));
    }

    @Test
    public void testCanCapturePieceOnCaptureMove() {
        Chessboard chessboard = new ChessboardImpl();
        Pawn firstPawn = new Pawn(Color.WHITE, new Square("d4"));
        Pawn secondPawn = new Pawn(Color.BLACK, new Square("c5"));
        chessboard.addPiece(firstPawn);
        chessboard.addPiece(secondPawn);

        assertTrue(firstPawn.canMove(chessboard, new Square("c5")));
    }

    @Test
    public void testCanNotCaptureSameColorPieceOnCaptureMove() {
        Chessboard chessboard = new ChessboardImpl();
        Pawn firstPawn = new Pawn(Color.WHITE, new Square("d4"));
        Pawn secondPawn = new Pawn(Color.WHITE, new Square("c5"));
        chessboard.addPiece(firstPawn);
        chessboard.addPiece(secondPawn);

        assertFalse(firstPawn.canMove(chessboard, new Square("c5")));
    }
}
