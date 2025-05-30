package latice.Board;

public enum CaseType {
    NORMAL("   "),
    SUN(" S "),
    MOON(" L ");

    private final String symbol;

    CaseType(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}
