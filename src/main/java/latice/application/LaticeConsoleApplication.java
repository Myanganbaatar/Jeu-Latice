package latice.application;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import latice.controller.PlayGame;

public class LaticeConsoleApplication {
	 
	
	public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
        PlayGame game = new PlayGame();

        do {
            game.playGame(scanner);
        } while (game.askReplay(scanner));

        System.out.println("👋 Thanks for playing! See you soon!");
        scanner.close();
    }
}

