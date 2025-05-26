package latice.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import latice.model.Game;
import latice.model.Player;

class GameTest {

	private Game game;

    @BeforeEach
    void setUp() {
        game = new Game();
    }

    @Test
    void test_Initialize_Game_Creates_TwoPlayers() {
        game.initializeGame();
        List<Player> players = game.getPlayers();
        assertEquals(2, players.size());

        assertEquals("Joueur 1", players.get(0).getName());
        assertEquals("Joueur 2", players.get(1).getName());
    }
    
    @Test
    void Assert_Players_Have_Tiles_In_Pool_After_Initialization() {
        game.initializeGame();
        List<Player> players = game.getPlayers();

        for (Player player : players) {
            assertTrue(player.hasTilesInPool() || player.getRack().size() > 0);
        }
    }
    
    @Test
    void test_Players_Racks_Are_Initialized() {
        game.initializeGame();
        List<Player> players = game.getPlayers();

        for (Player player : players) {
            assertTrue(player.getRack().size() <= 5);
        }
    }
    
    @Test
    void test_Get_Players_Returns_Copy() {
        game.initializeGame();
        List<Player> original = game.getPlayers();
        original.clear(); // Should not affect internal list

        List<Player> after = game.getPlayers();
        assertEquals(2, after.size());
    }
}
