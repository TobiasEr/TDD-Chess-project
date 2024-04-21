package ax.ha.tdd.chess.web;

import ax.ha.tdd.chess.engine.Color;

public class ColorView {

    private final Color color;

    public ColorView(Color color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return switch (color) {
            case WHITE -> "white";
            case BLACK -> "black";
        };
    }
}
