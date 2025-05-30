package latice.model;

public class Tile {
    private final Color color;
    private final Shape shape;

    public Tile(Color color, Shape shape) {
        this.color = color;
        this.shape = shape;
    }

    @Override
    public String toString() {
        return color.getAnsiCode() + shape.getSymbol() + " (" + shape + ")" + ANSI_RESET;
    }

    private static final String ANSI_RESET = "\u001B[0m";

    public Color getColor() {
        return color;
    }

    public Shape getShape() {
        return shape;
    }
    
    public String toColoredSymbol() {
        return color.getAnsiCode() + shape.getSymbol() + ANSI_RESET;
    }
}