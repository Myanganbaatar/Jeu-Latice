package latice.model;

import latice.Board.Board;
import latice.model.Deck;
import latice.model.Tile;
import latice.rules.Referee;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

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

    

    public List<Player> getPlayers() {
        return new ArrayList<>(players); // cette ligne est important pour protéger l'encapsulation de l'atribut players
    }
   
    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }
    
    public void nextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }
    
    public Tile drawTile() {
        return deck.drawTile();
    }
    
    public boolean isDeckEmpty() {
        return deck.getTotalTiles() == 0;
    }
    
    public void exchangeCurrentPlayerRack() {
        Player currentPlayer = getCurrentPlayer();
        currentPlayer.getRack().exchangeAllTiles(this.deck);
        this.deck.shuffle();
    }
    
    public boolean canBuyExtraAction(Player player) {
        return player.getScore() >= 2;
    }
    
    public void buyExtraAction(Player player) {
        if (canBuyExtraAction(player)) {
            player.addScore(-2);
            player.addExtraAction();
        }
    }
    
    public Deck getDeck() {
        return this.deck;
    }

    
}
