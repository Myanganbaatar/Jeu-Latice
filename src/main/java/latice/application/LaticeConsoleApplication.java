package latice.application;

import latice.game.Game;

public class LaticeConsoleApplication {
    public static void main(String[] args) {
        System.out.println("=== JEU LATICE - VERSION 1 ===");
        System.out.println("Initialisation du jeu...\n");

        Game game = new Game();
        game.initializeGame();

        System.out.println("État initial des joueurs:");
        game.displayPlayersRacks();
    }
}