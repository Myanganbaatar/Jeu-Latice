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
        System.out.println("=== Plateau de jeu ===\n");

        for (int j = 0; j < size; j++) {
            System.out.print("----");
        }
        System.out.println("-");

        for (int i = 0; i < size; i++) {
            // Ligne de contenu
            for (int j = 0; j < size; j++) {
                String content = switch (grid[i][j].getType()) {
                    case NORMAL -> " ";
                    case SUN -> "S";
                    case MOON -> "M";
                };
                System.out.print("| " + content + " ");
            }
            System.out.println("|");

            // Ligne de séparation
            for (int j = 0; j < size; j++) {
                System.out.print("----");
            }
            System.out.println("-");
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


}
