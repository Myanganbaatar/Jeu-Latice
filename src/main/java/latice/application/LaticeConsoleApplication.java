package latice.application;

import latice.Board.Board;
import latice.model.Game;
import latice.model.Player;

public class LaticeConsoleApplication {
    public static void main(String[] args) {
        System.out.println("=== JEU LATICE - VERSION 1 ===");
        System.out.println("Initialisation du jeu...\n");

        Game game = new Game();
        game.initializeGame();

        System.out.println("État initial des joueurs:");
        game.displayPlayersRacks();
        
        
        
       System.out.println("=== JEU LATICE - VERSION 2 ===");
       Board board = new Board(9);
       board.displayBoard();
        
       System.out.println("=== JEU LATICE - VERSION 4 ===");
       Player currentPlayer = game.getCurrentPlayer();
       
       board.displayBoard();
       
       System.out.println("Starting Player (choix aleatoire): " + currentPlayer.getName());
       
       
       
    }
}