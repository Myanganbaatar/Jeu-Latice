package latice.Board;

import latice.model.Tile;

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
        int[][] sunCoords = {
            {0,0}, {0,4}, {0,8},
            {1,1}, {1,7},
            {2,2}, {2,6},
            {4,0}, {4,8},
            {6,2}, {6,6},
            {7,1}, {7,7},
            {8,0}, {8,4}, {8,8}
        };
        for (int[] pos : sunCoords) {
            grid[pos[0]][pos[1]] = new BoardCase(CaseType.SUN);
        }

        // Case Lune
        grid[4][4] = new BoardCase(CaseType.MOON);
    }
    
    


    public void displayBoard() {
        System.out.print("\n    ");
        for (int j = 1; j <= size; j++) {
            System.out.print(j + "   ");
        }
        System.out.println();
        System.out.println("   " + "-".repeat(size * 4 + 1));

        for (int i = 0; i < size; i++) {
            System.out.print((i + 1) + " |");
            for (int j = 0; j < size; j++) {
                if (hasTile(i, j)) {
                    Tile t = getTile(i, j);
                    System.out.print(t.toColoredSymbol() + " |");
                } else {
                    System.out.print("   |");
                }
            }
            System.out.println();
            System.out.println("   " + "-".repeat(size * 4 + 1));
        }
    }
    
    public BoardCase getCase(int row, int col) {
        if (row >= 0 && row < size && col >= 0 && col < size) {
            return grid[row][col];
        }
        throw new IndexOutOfBoundsException("Position out of board bounds");
    }
    
    
    public boolean isSunCase(int row, int col) {
        return getCase(row, col).getType() == CaseType.SUN;
    }
    
    private Tile[][] placedTiles = new Tile[9][9];

    public boolean hasTile(int row, int col) {
        return placedTiles[row][col] != null;
    }

    public Tile getTile(int row, int col) {
        return placedTiles[row][col];
    }
    
    public void placeTile(int row, int col, Tile tile) {
        placedTiles[row][col] = tile;
    }

}
