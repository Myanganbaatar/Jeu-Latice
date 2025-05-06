package latice.model;

import latice.model.Deck;
import latice.model.Tile;
import java.util.ArrayList;
import java.util.List;

public class Game {
    private final List<Player> players;
    private final Deck deck;

    public Game() {
        this.players = new ArrayList<>();
        this.deck = new Deck();
    }

    public void initializeGame() {
        
        players.add(new Player("Joueur 1"));
        players.add(new Player("Joueur 2"));

        
        deck.shuffle();

        
        distributeTiles();

        
        for (Player player : players) {
            player.initializeRack();
        }
    }

    private void distributeTiles() {
        int totalTiles = deck.getTotalTiles();
        int tilesPerPlayer = totalTiles / players.size();

        for (Player player : players) {
            for (int i = 0; i < tilesPerPlayer; i++) {
                Tile tile = deck.drawTile();
                if (tile != null) {
                    player.addToPool(tile);
                }
            }
        }
    }

    
}
