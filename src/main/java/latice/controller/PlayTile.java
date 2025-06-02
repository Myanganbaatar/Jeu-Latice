package latice.controller;

import java.util.Scanner;

import latice.Board.Board;
import latice.model.Game;
import latice.model.Player;
import latice.model.Tile;
import latice.rules.Referee;

public class PlayTile {
	public  void playTile(Scanner scanner, Player currentPlayer, Board board, Referee referee, Game game, boolean isFirstMove) {
        int tileIndex = -1;
        while (tileIndex < 0 || tileIndex >= currentPlayer.getRack().getTiles().size()) {
            System.out.print("\nEnter the index of the tile to play (1-" + currentPlayer.getRack().getTiles().size() + "): ");
            tileIndex = scanner.nextInt() - 1;
        }
        Tile tile = currentPlayer.getRack().getTiles().get(tileIndex);

        int row = -1, col = -1;
        while (row < 0 || row >= 9) {
            System.out.print("Enter row (1-9): ");
            row = scanner.nextInt() - 1;
        }
        while (col < 0 || col >= 9) {
            System.out.print("Enter column (1-9): ");
            col = scanner.nextInt() - 1;
        }

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
    }

}
