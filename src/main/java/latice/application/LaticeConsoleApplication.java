package latice.application;

import latice.Board.Board;
import latice.model.Game;
import latice.model.Player;

public class LaticeConsoleApplication {
    public static void main(String[] args) {
        System.out.println("=== Latice Game - Version 5 ===\n");

        Game game = new Game();
        game.initializeGame();
        game.startGame(); 
    }
}

