package latice.rules;
import java.util.List;

import latice.Board.Board;
import latice.Board.CaseType;
import latice.model.Player;
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

	    // Doit avoir au moins une tuile adjacente (même ligne/colonne)
	    boolean hasAdjacent = false;
	    int[][] directions = {
	        {-1, 0}, {1, 0}, {0, -1}, {0, 1}
	    };

	    // Vérifier chaque direction
	    for (int[] dir : directions) {
	        int r = row + dir[0];
	        int c = col + dir[1];

	        if (r >= 0 && r < 9 && c >= 0 && c < 9 && board.hasTile(r, c)) {
	            hasAdjacent = true;
	            break;
	        }
	    }
	    
	    if (!hasAdjacent) return false;

	    // Maintenant vérifier la compatibilité avec toutes les tuiles adjacentes
	    for (int[] dir : directions) {
	        int r = row + dir[0];
	        int c = col + dir[1];

	        if (r >= 0 && r < 9 && c >= 0 && c < 9 && board.hasTile(r, c)) {
	            Tile neighbor = board.getTile(r, c);
	            // Doit correspondre soit en couleur soit en forme avec chaque voisin
	            if (neighbor.getColor() != tile.getColor() && neighbor.getShape() != tile.getShape()) {
	                return false;
	            }
	        }
	    }

	    return true;
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

        int score = 0;
        if (matchingSides == 2) score = 1;      // 1 demi-pierre = 1 point
        else if (matchingSides == 3) score = 2; // 1 pierre = 2 points
        else if (matchingSides == 4) score = 4; // 2 pierres = 4 points

        // Bonus de 2 points pour les cases soleil
        if (board.getCase(row, col).getType() == CaseType.SUN) {
            score += 2; 
        }

        return score;
    }
    
    public boolean isGameOver(Board board, List<Player> players) {
        return players.stream().allMatch(p -> p.getRack().isEmpty());
    }
    
    public Player getWinner(List<Player> players) {
        int maxTiles = -1;
        Player winner = null;
        boolean tie = false;

        // Trouver le nombre maximum de tuiles posées
        for (Player p : players) {
            if (p.getTilesPlaced() > maxTiles) {
                maxTiles = p.getTilesPlaced();
                winner = p;
                tie = false;
            } else if (p.getTilesPlaced() == maxTiles) {
                tie = true;
            }
        }

        // S'il y a une égalité (match nul), retourner null
        if (tie) {
            return null;
        }

        // Sinon, renvoyer le gagnant
        return winner;
    }
}