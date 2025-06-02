package latice.test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import latice.Board.Board;
import latice.model.Deck;
import latice.model.Game;
import latice.model.Player;
import latice.model.Tile;
import latice.rules.Referee;

class GameTest {
    private Game game;

    @BeforeEach
    void setUp() {
        game = new Game();
        game.initializeGame();
    }

    @Test
    void testInitializeGame() {
        // Verify players were created
        assertEquals(2, game.getPlayers().size());
        assertEquals("Player 1", game.getPlayers().get(0).getName());
        assertEquals("Player 2", game.getPlayers().get(1).getName());
        
        // Verify tiles were distributed
        for (Player player : game.getPlayers()) {
            assertEquals(5, player.getRack().size());
        }
        
        // Verify current player is set
        assertNotNull(game.getCurrentPlayer());
    }

    @Test
    void testGetPlayersReturnsCopy() {
        List<Player> players = game.getPlayers();
        players.remove(0); // Modify the copy
        
        assertEquals(2, game.getPlayers().size()); // Original unchanged
    }

    @Test
    void testNextPlayer() {
        Player firstPlayer = game.getCurrentPlayer();
        game.nextPlayer();
        Player secondPlayer = game.getCurrentPlayer();
        
        assertNotEquals(firstPlayer, secondPlayer);
        
        game.nextPlayer();
        assertEquals(firstPlayer, game.getCurrentPlayer()); // Should wrap around
    }

    @Test
    void testDrawTile() {
        int initialDeckSize = game.getDeck().getTotalTiles();
        Tile tile = game.drawTile();
        
        assertNotNull(tile);
        assertEquals(initialDeckSize - 1, game.getDeck().getTotalTiles());
    }

    @Test
    void testIsDeckEmpty() {
        // Empty the deck
        while (!game.isDeckEmpty()) {
            game.drawTile();
        }
        
        assertTrue(game.isDeckEmpty());
    }

    @Test
    void testExchangeCurrentPlayerRack() {
        Player currentPlayer = game.getCurrentPlayer();
        List<Tile> originalRack = currentPlayer.getRack().getTiles();
        int initialDeckSize = game.getDeck().getTotalTiles();
        
        game.exchangeCurrentPlayerRack();
        
        // Verify rack has new tiles
        assertNotEquals(originalRack, currentPlayer.getRack().getTiles());
        // Verify deck size remains the same (exchange, not draw)
        assertEquals(initialDeckSize, game.getDeck().getTotalTiles());
    }

    @Test
    void testCanBuyExtraAction() {
        Player player = game.getPlayers().get(0);
        
        // Initially shouldn't be able to buy
        assertFalse(game.canBuyExtraAction(player));
        
        // Add enough points
        player.addScore(2);
        assertTrue(game.canBuyExtraAction(player));
        
        // Test with exact score
        player.addScore(-1); // Now has 1 point
        assertFalse(game.canBuyExtraAction(player));
    }

    @Test
    void testBuyExtraAction() {
        Player player = game.getPlayers().get(0);
        player.addScore(3);
        int initialScore = player.getScore();
        int initialActions = player.getExtraActions();
        
        // Successful purchase
        game.buyExtraAction(player);
        assertEquals(initialScore - 2, player.getScore());
        assertEquals(initialActions + 1, player.getExtraActions());
        
        // Failed purchase (not enough points)
        initialScore = player.getScore();
        initialActions = player.getExtraActions();
        game.buyExtraAction(player); // Only 1 point left now
        assertEquals(initialScore, player.getScore());
        assertEquals(initialActions, player.getExtraActions());
    }

    @Test
    void testGetDeck() {
    	
        Deck deck = game.getDeck();
        assertNotNull(deck);
        
        // Verify it's the same deck instance
        assertSame(deck, game.getDeck());
    }
}