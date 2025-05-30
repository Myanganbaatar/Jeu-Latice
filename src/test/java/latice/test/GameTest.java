package latice.test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import latice.Board.Board;
import latice.model.Game;
import latice.model.Player;
import latice.model.Tile;
import latice.rules.Referee;

public class GameTest {

    Game game;
    Referee referee;
    @BeforeEach
    void setUp() {
        game = new Game();
        game.initializeGame();
        referee = new Referee();
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
    
    @Test
    void testAnnounceWinnerDetectsCorrectWinner() throws Exception {
        List<Player> players = game.getPlayers();

        
        Player player1 = players.get(0);
        Player player2 = players.get(1);

        player1.addScore(20);
        player2.addScore(10);

       
        var method = Game.class.getDeclaredMethod("announceWinner", List.class, Referee.class);
        method.setAccessible(true);

        System.out.println("\n🔍 Testing announceWinner...");
        method.invoke(game, players, referee);  

        assertEquals(player1, referee.getWinner(players));
    }
    
    @Test
    void testAnnounceWinnerWithDraw() throws Exception {
        List<Player> players = game.getPlayers();

        Player player1 = players.get(0);
        Player player2 = players.get(1);

        player1.addScore(15);
        player2.addScore(15);

        var method = Game.class.getDeclaredMethod("announceWinner", List.class, Referee.class);
        method.setAccessible(true);

        System.out.println("\n🔍 Testing announceWinner (draw)...");
        method.invoke(game, players, referee);  // Should print DRAW

        assertNull(referee.getWinner(players));
    }
    
    @Test
    public void testAskReplayYes() {
        Scanner scanner = new Scanner("y");
        Game game = new Game();
        assertTrue(game.askReplay(scanner));
    }
  
}