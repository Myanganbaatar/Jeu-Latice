package latice.application;

import latice.Board.Board;
import latice.model.Game;

public class LaticeConsoleApplication {
    public static void main(String[] args) {
        System.out.println("=== JEU LATICE - VERSION 1 ===");
        System.out.println("Initialisation du jeu...\n");

        Game game = new Game();
        game.initializeGame();

        System.out.println("État initial des joueurs:");
        game.displayPlayersRacks();
        
        
       Board board = new Board(9);
       board.displayBoard();
        
        
    }
}