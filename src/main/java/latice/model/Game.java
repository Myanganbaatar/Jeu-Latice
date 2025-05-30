package latice.model;

import latice.Board.Board;
import latice.model.Deck;
import latice.model.Tile;
import latice.rules.Referee;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {
	private final List<Player> players;
    public final Deck deck;
    private int currentPlayerIndex = 0;
    private final Board board;
    private final Referee referee;
    private boolean isFirstMove = true;
    

    public Game() {
    	this.players = new ArrayList<>();
        this.deck = new Deck();
        this.board = new Board(9); // 9x9 board
        this.referee = new Referee();
    }

    public void initializeGame() {
        
    	players.add(new Player("Player 1"));
        players.add(new Player("Player 2"));

        deck.shuffle();
        distributeTiles();

        for (Player player : players) {
            player.initializeRack();
        }

        currentPlayerIndex = new Random().nextInt(players.size());
    }

    private void distributeTiles() {
        for (Player player : players) {
            for (int i = 0; i < 5; i++) {
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
    public Player getCurrentPlayer() {
        return currentPlayer;
    }
    
    
}
