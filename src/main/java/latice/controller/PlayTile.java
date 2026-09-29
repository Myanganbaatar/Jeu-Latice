package latice.controller;

import java.util.Scanner;

import latice.Board.Board;
import latice.model.Game;
import latice.model.Player;
import latice.model.Tile;
import latice.rules.Referee;
import latice.util.InvalidInputException;

public class PlayTile {
	public void playTile(Scanner scanner, Player currentPlayer, Board board, Referee referee, Game game, boolean isFirstMove) {
	    try {
	        // Get tile index from player
	        int tileIndex = InvalidInputException.readIntWithException(
	            scanner,
	            "\nEnter the index of the tile to play (1-" + currentPlayer.getRack().getTiles().size() + "): ",
	            1,
	            currentPlayer.getRack().getTiles().size()
	        ) - 1; // Convert to 0-based index

	        Tile tile = currentPlayer.getRack().getTiles().get(tileIndex);

	        // Get row and column from player
	        int row = InvalidInputException.readIntWithException(scanner, "Enter row (1-9): ", 1, 9) - 1;
	        int col = InvalidInputException.readIntWithException(scanner, "Enter column (1-9): ", 1, 9) - 1;

	        // Validate move
	        boolean validMove = referee.isPlacementValid(board, row, col, tile, isFirstMove);

	        if (validMove) {
	            board.placeTile(row, col, tile);
	            currentPlayer.getRack().removeTile(tile);
	            currentPlayer.incrementTilesPlaced();

	            int points = referee.calculateScore(board, row, col, tile);
	            currentPlayer.addScore(points);
	            System.out.println("✅ Valid move! Points earned: " + points);

	            Tile newTile = game.drawTile();
	            if (newTile != null) {
	                currentPlayer.getRack().addTile(newTile);
	                System.out.println("🟡 New tile added to your rack.");
	            } else {
	                System.out.println("⚠️ Deck is empty, no new tile.");
	            }
	        } else {
	            System.out.println("⛔ Invalid move: illegal placement.");
	            if (isFirstMove) {
	                System.out.println("👉 Reminder: the first move must be at row 5, column 5!");
	            }
	            playTile(scanner, currentPlayer, board, referee, game, isFirstMove);
	        }
	    } catch (InvalidInputException e) {
	        System.out.println("❌ Error: " + e.getMessage());
	        // la ligne invalide est deja consommee par readIntWithException
	        playTile(scanner, currentPlayer, board, referee, game, isFirstMove); // Retry
	    }
	}

}
