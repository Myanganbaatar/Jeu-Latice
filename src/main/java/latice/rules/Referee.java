package latice.rules;
import latice.Board.Board;
import latice.model.Tile;

public class Referee {

    public boolean isPlacementValid(Board board, int row, int col, Tile tile, boolean isFirstMove) {
        // Hors du plateau
        if (row < 0 || row >= 9 || col < 0 || col >= 9) return false;

        // Case déjà occupée
        if (board.hasTile(row, col)) return false;

        // Premier coup : obligé de poser sur la lune (centre)
        if (isFirstMove) {
            return row == 4 && col == 4;
        }

        // Doit avoir au moins une tuile adjacente même couleur ou forme (pas diagonal)
        int[][] directions = {
            {-1, 0}, {1, 0}, {0, -1}, {0, 1}
        };

        for (int[] dir : directions) {
            int r = row + dir[0];
            int c = col + dir[1];

            if (r >= 0 && r < 9 && c >= 0 && c < 9 && board.hasTile(r, c)) {
                Tile neighbor = board.getTile(r, c);
                if (neighbor.getColor() == tile.getColor() || neighbor.getShape() == tile.getShape()) {
                    return true;
                }
            }
        }

        return false;
    }
    
    public int calculateScore(Board board, int row, int col, Tile tile) {
        int matchingSides = 0;
        int[][] directions = {
            {-1, 0}, // haut
            {1, 0},  // bas
            {0, -1}, // gauche
            {0, 1}   // droite
        };

        for (int[] dir : directions) {
            int r = row + dir[0];
            int c = col + dir[1];

            if (r >= 0 && r < 9 && c >= 0 && c < 9 && board.hasTile(r, c)) {
                Tile neighbor = board.getTile(r, c);
                if (neighbor.getColor() == tile.getColor() || neighbor.getShape() == tile.getShape()) {
                    matchingSides++;
                }
            }
        }

        if (matchingSides == 2) return 1;      // 1 demi-pierre = 1 point
        else if (matchingSides == 3) return 2; // 1 pierre = 2 points
        else if (matchingSides == 4) return 4; // 2 pierres = 4 points
        else return 0;
    }
}