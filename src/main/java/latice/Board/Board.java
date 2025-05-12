package latice.Board;

public class Board {
    private final int size;
    private final BoardCase[][] grid;

    public Board(int size) {
        this.size = size;
        this.grid = new BoardCase[size][size];
        
    }
}
