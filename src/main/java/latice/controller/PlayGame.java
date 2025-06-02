package latice.controller;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

import latice.Board.Board;
import latice.model.Game;
import latice.model.Player;
import latice.rules.Referee;

public class PlayGame {
	public  void playGame(Scanner scanner) {
        System.out.println("=== Latice Game - Version 7 (console) ===\n");

        Game game = new Game();
        game.initializeGame();
        PlayTile play = new PlayTile();

        List<Player> players = game.getPlayers();
        Board board = new Board(9);
        Referee referee = new Referee();

        int currentPlayerIndex = new Random().nextInt(players.size());
        Player currentPlayer = players.get(currentPlayerIndex);

        boolean isFirstMove = true;
        int turns = 0;
        int cycles = 0;

        System.out.println("\n👉 First move: row 5, column 5 (center of the moon)\n");

        while (true) {
            System.out.println("\n🎲 Current player: " + currentPlayer.getName());
            System.out.println("Score: " + currentPlayer.getScore());
            System.out.println("Tiles placed: " + currentPlayer.getTilesPlaced());
            System.out.println("Extra actions: " + currentPlayer.getExtraActions());
            System.out.println("Cycle: " + cycles);
            currentPlayer.displayRack();

            board.displayBoard();

            System.out.println("\n💡 Action (1=Play tile | 2=Pass turn | 3=Exchange all tiles)");
            System.out.print("Your choice: ");
            int action = scanner.nextInt();

            if (action == 1) {
            	play.playTile(scanner, currentPlayer, board, referee, game, isFirstMove);
                isFirstMove = false;

                // Boucle pour acheter des actions supplémentaires tant que le joueur le veut et a 2 points
                while (currentPlayer.getScore() >= 2) {
                    if (askToBuyExtraAction(scanner)) {
                        if (currentPlayer.spendPoints(2)) {
                            System.out.println("💰 Extra action purchased! Play again immediately.");

                            // Jouer immédiatement après l’achat
                            System.out.println("\n🎲 Current player: " + currentPlayer.getName());
                            System.out.println("Score: " + currentPlayer.getScore());
                            System.out.println("Tiles placed: " + currentPlayer.getTilesPlaced());
                            System.out.println("Cycle: " + cycles);
                            currentPlayer.displayRack();
                            board.displayBoard();

                            System.out.println("\n💡 Action (1=Play tile | 2=Pass turn | 3=Exchange all tiles)");
                            System.out.print("Your choice: ");
                            int extraActionChoice = scanner.nextInt();

                            if (extraActionChoice == 1) {
                                play.playTile(scanner, currentPlayer, board, referee, game, false);
                            } else if (extraActionChoice == 2 || extraActionChoice == 3) {
                                System.out.println("🔄 Passing turn directly to the next player.");
                                break;
                            } else {
                                System.out.println("⛔ Invalid action. Turn passed to the next player.");
                                break;
                            }
                        } else {
                            System.out.println("⛔ Not enough points to buy an extra action.");
                            break;
                        }
                    } else {
                        break;
                    }
                }

            } else if (action == 2) {
                System.out.println("🔄 " + currentPlayer.getName() + " passed their turn.");

            } else if (action == 3) {
                currentPlayer.exchangeRack(game.getDeck());
                System.out.println("♻️ All tiles exchanged. Turn passed to the next player.");

            } else {
                System.out.println("⛔ Invalid action. Please try again!");
                continue;
            }

            // Compter les tours et cycles
            turns++;
            if (turns % 2 == 0) {
                cycles++;
            }

            if (cycles >= 10 || referee.isGameOver(board, players)) {
                System.out.println("\n🔔 Game over!");
                announceWinner(players, referee);
                break;
            }

            currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
            currentPlayer = players.get(currentPlayerIndex);
        }
    }
	
	

}
