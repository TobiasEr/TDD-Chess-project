package ax.ha.tdd.chess.engine;

import ax.ha.tdd.chess.console.ChessboardWriter;
import ax.ha.tdd.chess.engine.pieces.ChessPiece;
import ax.ha.tdd.chess.engine.pieces.Pawn;
import ax.ha.tdd.chess.engine.pieces.PieceType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class GameTests {

    @AfterEach
    public void tearDown() {
        System.setOut(System.out);
        System.setIn(System.in);
    }

    @Test
    public void testMoveOpponentsPieceShouldBeIllegal() {
        //Arrange
        Game game = new GameImpl();
        assertEquals(Color.WHITE, game.getPlayerToMove());

        //Act
        game.move("e7-e6");

        //Assert
        assertEquals(Color.WHITE, game.getPlayerToMove());
        ChessPiece piece = game.getBoard().getPieceAt(new Square("e7"));
        assertEquals(Color.BLACK, piece.getColor());
        assertEquals(PieceType.PAWN, piece.getType());


        //For debugging, you can print the board to console, or if you want
        //to implement a command line interface for the game
        System.out.println(new ChessboardWriter().print(game.getBoard()));
    }

    @Test
    public void testThatPieceIsActuallyMovedOnChessboardAfterMove() {
        Game game = new GameImpl();

        ChessPiece movingPiece = game.getBoard().getPieceAt(new Square("e2"));

        game.move("e2-e4");

        assertEquals(game.getBoard().getPieceAt(new Square("e4")), movingPiece);
        assertNull(game.getBoard().getPieceAt(new Square("e2")));

        //For debugging, you can print the board to console, or if you want
        //to implement a command line interface for the game
        System.out.println(new ChessboardWriter().print(game.getBoard()));
    }

    @Test
    public void testThatPieceIsNotMovedOnChessboardAfterIllegalMove() {
        Game game = new GameImpl();

        ChessPiece sourcePiece = game.getBoard().getPieceAt(new Square("e2"));

        game.move("e2-e5");

        assertEquals(game.getBoard().getPieceAt(new Square("e2")), sourcePiece);
        assertNull(game.getBoard().getPieceAt(new Square("e5")));

        //For debugging, you can print the board to console, or if you want
        //to implement a command line interface for the game
        System.out.println(new ChessboardWriter().print(game.getBoard()));
    }

    @Test
    public void testMessageAfterSuccessfulMove() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        System.setOut(new PrintStream(outputStream));

        String input = "" + System.lineSeparator();
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Game game = new GameImpl();
        game.move("e2-e4");

        String expectedOutput = "Player tried to perform move: e2-e4";
        assertEquals(expectedOutput, outputStream.toString().trim());
        assertEquals(game.getLastMoveResult(), "Player moved PAWN from e2 to e4");
    }

    @Test
    public void testChangingPawn() {
        Game game = new GameImpl();

        // Emptying board to make the test easier to implement.
        for (ChessPiece[] row: game.getBoard()) {
            for (ChessPiece piece: row) {
                if (piece != null)
                    game.getBoard().removePieceAt(piece.getLocation());
            }
        }

        Pawn changingPawn = new Pawn(Color.WHITE, new Square("f7"));
        game.getBoard().addPiece(changingPawn);

        game.move("f7-f8");
        game.move("Q");

        ChessPiece piece = game.getBoard().getPieceAt(new Square("f8"));
        assertEquals(Color.WHITE, piece.getColor());
        assertEquals(PieceType.QUEEN, piece.getType());

        System.out.println(new ChessboardWriter().print(game.getBoard()));
    }

    @Test
    public void testKingAndRookHasMovedAfterCastling() {
        Game game = new GameImpl();

        game.getBoard().removePieceAt(new Square("b1"));
        game.getBoard().removePieceAt(new Square("c1"));
        game.getBoard().removePieceAt(new Square("d1"));

        ChessPiece whiteRook = game.getBoard().getPieceAt(new Square("a1"));
        ChessPiece whiteKing = game.getBoard().getPieceAt(new Square("e1"));

        game.move("e1-c1");

        assertEquals(game.getBoard().getPieceAt(new Square("c1")), whiteKing);
        assertEquals(game.getBoard().getPieceAt(new Square("d1")), whiteRook);
        assertNull(game.getBoard().getPieceAt(new Square("e1")));
        assertNull(game.getBoard().getPieceAt(new Square("a1")));
    }
}
