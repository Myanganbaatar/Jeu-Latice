package latice.controller;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

import latice.Board.Board;
import latice.model.Game;
import latice.model.Player;
import latice.rules.Referee;
import latice.util.InvalidInputException;

public class PlayGame {
    public void playGame(Scanner scanner) {
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
            try {
                System.out.println("\n🎲 Current player: " + currentPlayer.getName());
                System.out.println("Score: " + currentPlayer.getScore());
                System.out.println("Tiles placed: " + currentPlayer.getTilesPlaced());
                System.out.println("Extra actions: " + currentPlayer.getExtraActions());
                System.out.println("Cycle: " + cycles);
                currentPlayer.displayRack();

                board.displayBoard();

                int action = InvalidInputException.readIntWithException(
                    scanner, 
                    "\n💡 Action (1=Play tile | 2=Pass turn | 3=Exchange all tiles)\nYour choice: ",
                    1, 3
                );

                if (action == 1) {
                    play.playTile(scanner, currentPlayer, board, referee, game, isFirstMove);
                    isFirstMove = false;

                    while (currentPlayer.getScore() >= 2) {
                        if (askToBuyExtraAction(scanner)) {
                            if (currentPlayer.spendPoints(2)) {
                                System.out.println("💰 Extra action purchased! Play again immediately.");

                                System.out.println("\n🎲 Current player: " + currentPlayer.getName());
                                System.out.println("Score: " + currentPlayer.getScore());
                                System.out.println("Tiles placed: " + currentPlayer.getTilesPlaced());
                                System.out.println("Cycle: " + cycles);
                                currentPlayer.displayRack();
                                board.displayBoard();

                                int extraActionChoice = InvalidInputException.readIntWithException(
                                    scanner,
                                    "\n💡 Action (1=Play tile | 2=Pass turn | 3=Exchange all tiles)\nYour choice: ",
                                    1, 3
                                );

                                if (extraActionChoice == 1) {
                                    play.playTile(scanner, currentPlayer, board, referee, game, false);
                                } else if (extraActionChoice == 2 || extraActionChoice == 3) {
                                    System.out.println("🔄 Passing turn directly to the next player.");
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
                }

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

            } catch (InvalidInputException e) {
                System.out.println("⛔ Error: " + e.getMessage());
                // ligne invalide deja consommee par readIntWithException
            }
        }
    }

    public boolean askReplay(Scanner scanner) {
        while (true) {
            try {
                System.out.print("\n🔁 Do you want to play again? (y/n): ");
                String response = scanner.next();
                if (!response.equalsIgnoreCase("y") && !response.equalsIgnoreCase("n")) {
                    throw new InvalidInputException("Please enter 'y' or 'n'");
                }
                return response.equalsIgnoreCase("y");
            } catch (InvalidInputException e) {
                System.out.println("⛔ Error: " + e.getMessage());
                scanner.nextLine(); // Clear the invalid input
            }
        }
    }

    public boolean askToBuyExtraAction(Scanner scanner) {
        while (true) {
            try {
                System.out.print("❓ You have at least 2 points. Do you want to buy an extra action? (y/n): ");
                String response = scanner.next();
                if (!response.equalsIgnoreCase("y") && !response.equalsIgnoreCase("n")) {
                    throw new InvalidInputException("Please enter 'y' or 'n'");
                }
                return response.equalsIgnoreCase("y");
            } catch (InvalidInputException e) {
                System.out.println("⛔ Error: " + e.getMessage());
                scanner.nextLine(); // Clear the invalid input
            }
        }
    }

    public void announceWinner(List<Player> players, Referee referee) {
        System.out.println("\n=== Final result ===");
        for (Player p : players) {
            System.out.println(p.getName() + " - Score: " + p.getScore() + " - Tiles placed: " + p.getTilesPlaced());
        }

        Player winner = referee.getWinner(players);
        if (winner == null) {
            System.out.println("\nDRAW!");
        } else {
            System.out.println("\n🏆 WINNER: " + winner.getName() + " 🏆");
        }
    }
}
