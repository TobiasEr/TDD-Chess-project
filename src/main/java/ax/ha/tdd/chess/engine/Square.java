package ax.ha.tdd.chess.engine;

import java.util.Objects;

public class Square {
    private final int x;
    private final int y;

    public Square(int x, int y) {
        if (x < 0 || x > 7) {
            throw new IllegalArgumentException("x " + x + " is not valid");
        }
        if (y < 0 || y > 7) {
            throw new IllegalArgumentException("y " + y + " is not valid");
        }

        this.x = x;
        this.y = y;
    }

    // In case you want to use Algebraic notation when writing tests instead
    public Square(String algebraic) {
        char letter = algebraic.charAt(0);
        int number = Character.digit(algebraic.charAt(1), 10);

        if (((int)letter) < ((int)'a') || ((int)letter) > ((int)'h')) {
            throw new IllegalArgumentException("Letter " + letter + " is not valid");
        }
        if (number < 1 || number > 8) {
            throw new IllegalArgumentException("Number " + number + " is not valid");
        }
        this.x = ((int)letter) - ((int)'a');
        this.y = 8 - number;
    }

    public String toAlgebraic() {
        return String.format("%c%d", 'a'+x, 8-y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Square that = (Square) o;
        return x == that.x &&
                y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return toAlgebraic() + "(" + x + "," + y + ")";
    }
}
