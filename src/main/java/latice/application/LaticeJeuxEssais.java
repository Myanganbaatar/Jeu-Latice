package latice.application;

import latice.game.Game;
import latice.game.Player;
import latice.model.Color;
import latice.model.Deck;
import latice.model.Shape;
import latice.model.Tile;

public class LaticeJeuxEssais {
    public static void main(String[] args) {
        System.out.println("=== Jeux d'essais pour la Version 1 ===");
        testDeckCreation();
        testTileDistribution();
        testPlayerRacks();
    }

    private static void testDeckCreation() {
        System.out.println("\nTest 1: Création du deck");
        Deck deck = new Deck();
        System.out.println("Nombre de tuiles créées: " + deck.getTotalTiles());
    }

    private static void testTileDistribution() {
        System.out.println("\nTest 2: Distribution des tuiles");
        Game game = new Game();
        game.initializeGame();
        game.displayPlayersRacks();
    }

    private static void testPlayerRacks() {
        System.out.println("\nTest 3: Vérification des racks");
        Player player = new Player("Testeur");
        player.addToPool(new Tile(Color.RED, Shape.FLOWER));
        player.addToPool(new Tile(Color.GREEN, Shape.BIRD));
        player.displayRack();
    }
}
