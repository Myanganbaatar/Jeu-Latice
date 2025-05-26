package latice.model;

import latice.model.Deck;
import latice.model.Tile;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {
    private final List<Player> players;
    private final Deck deck;
    private Player currentPlayer;
    

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

    public void displayPlayersRacks() {
        for (Player player : players) {
            player.displayRack();
            System.out.println();
        }
    }

    public List<Player> getPlayers() {
        return new ArrayList<>(players); // cette ligne est important pour protéger l'encapsulation de l'atribut players
    }
    
    private void selectRandomStartingPlayer() {
        int randomIndex = new Random().nextInt(players.size());
        currentPlayer = players.get(randomIndex);
    }
    
    
}
