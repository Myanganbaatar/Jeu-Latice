package latice.Board;

public class BoardCase {
    private final CaseType type;

    public BoardCase(CaseType type) {
        this.type = type;
    }

    public CaseType getType() {
        return type;
    }

    public char displaySymbol() {
        return switch (type) {
            case NORMAL -> '.';
            case SOLEIL -> 'S';
            case LUNE -> 'L';
        };
    }
}
