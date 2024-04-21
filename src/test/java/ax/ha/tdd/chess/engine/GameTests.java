package ax.ha.tdd.chess.engine;

import ax.ha.tdd.chess.console.ChessboardWriter;
import ax.ha.tdd.chess.engine.pieces.ChessPiece;
import ax.ha.tdd.chess.engine.pieces.PieceType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameTests {
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
}
