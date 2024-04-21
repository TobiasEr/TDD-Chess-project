package ax.ha.tdd.chess.engine;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SquareTests {

    @Test
    public void givenSameArgumentsExpectEquality() {
        Assertions.assertEquals(new Square(0, 7), new Square(0, 7));
    }

    @Test
    public void givenAlgebraicNotationExpectMatchingCoordinates() {
        Assertions.assertEquals(new Square("a1"), new Square(0, 7));
        Assertions.assertEquals(new Square("f3"), new Square(5, 5));
        Assertions.assertEquals(new Square("a8"), new Square(0, 0));
        Assertions.assertEquals(new Square("g8"), new Square(6, 0));
    }

    @Test
    public void givenBadAlgebraicNotationExpectIllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Square("A1"));

        Assertions.assertThrows(IllegalArgumentException.class, () -> new Square("I1"));

        Assertions.assertThrows(IllegalArgumentException.class, () -> new Square("a0"));

        Assertions.assertThrows(IllegalArgumentException.class, () -> new Square("a9"));
    }


    @Test
    public void givenBadIndexesExpectIllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Square(-1, 1));

        Assertions.assertThrows(IllegalArgumentException.class, () -> new Square(0, -1));

        Assertions.assertThrows(IllegalArgumentException.class, () -> new Square(8, 0));

        Assertions.assertThrows(IllegalArgumentException.class, () -> new Square(0, 8));
    }
}