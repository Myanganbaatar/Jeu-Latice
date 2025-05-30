package latice.application;

import java.util.Scanner;

import latice.Board.Board;
import latice.model.Game;
import latice.model.Player;

public class LaticeConsoleApplication {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Game game = new Game();

        do {
            game.initializeGame();
            game.startGame(scanner);
        } while (game.askReplay(scanner));

        System.out.println("👋 Thanks for playing! See you soon!");
        scanner.close();
    }
}

