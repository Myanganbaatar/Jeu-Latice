package latice.test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import latice.model.Game;
import latice.model.Player;
import latice.model.Tile;

public class GameTest {

    Game game;

    @BeforeEach
    void setUp() {
        game = new Game();
    }

    @Test
    void testConstructorInitialState() {
        assertNotNull(game.getPlayers());
        assertEquals(0, game.getPlayers().size());
        assertFalse(game.isDeckEmpty()); 
    }

    @Test
    void testInitializeGameAddsPlayersAndDistributesTiles() {
        game.initializeGame();
        List<Player> players = game.getPlayers();
        assertEquals(2, players.size());
        for (Player p : players) {
            assertFalse(p.getRack().getTiles().isEmpty()); 
            assertTrue(p.getPoolSize() >= 0);
        }
    }

    @Test
    void testNextPlayerCycles() {
        game.initializeGame();
        Player first = game.getCurrentPlayer();
        game.nextPlayer();
        Player second = game.getCurrentPlayer();
        assertNotEquals(first, second);

        // cycle back
        game.nextPlayer();
        Player backToFirst = game.getCurrentPlayer();
        assertEquals(first, backToFirst);
    }

    @Test
    void testDrawTileDelegatesToDeck() {
        Tile tile = game.drawTile();
        assertNotNull(tile); 
    }

    @Test
    void testIsDeckEmptyReflectsDeckState() {
        assertFalse(game.isDeckEmpty());
       
    }

  
}