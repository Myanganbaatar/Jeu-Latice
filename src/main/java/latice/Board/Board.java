package latice.Board;

public class Board {
    private final int size;
    private final BoardCase[][] grid;

    public Board(int size) {
        this.size = size;
        this.grid = new BoardCase[size][size];
        initBoard();
    }

    private void initBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[i][j] = new BoardCase(CaseType.NORMAL);
            }
        }

        // Cases Soleil
        int[][] soleilCoords = {
            {0,0}, {0,4}, {0,8},
            {1,1}, {1,7},
            {2,2}, {2,6},
            {4,0}, {4,8},
            {6,2}, {6,6},
            {7,1}, {7,7},
            {8,0}, {8,4}, {8,8}
        };
        for (int[] pos : soleilCoords) {
            grid[pos[0]][pos[1]] = new BoardCase(CaseType.SOLEIL);
        }

        // Case Lune
        grid[4][4] = new BoardCase(CaseType.LUNE);
    }


    

}
