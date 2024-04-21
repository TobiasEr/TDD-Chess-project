package ax.ha.tdd.chess.engine;

public enum Color {
    WHITE("W"),
    BLACK("B");

    private final String symbol;

    Color(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}
