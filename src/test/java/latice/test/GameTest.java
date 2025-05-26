package latice.test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import latice.model.Game;
import latice.model.Player;

class GameTest {

	private Game game;
	private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
    	System.setOut(new PrintStream(outputStream));
        game = new Game();
        game.initializeGame();
        
    }

    @Test
    void test_Initialize_Game_Creates_TwoPlayers() {
        
        List<Player> players = game.getPlayers();
        assertEquals(2, players.size());

        assertEquals("Joueur 1", players.get(0).getName());
        assertEquals("Joueur 2", players.get(1).getName());
    }
    
    @Test
    void Assert_Players_Have_Tiles_In_Pool_After_Initialization() {
       
        List<Player> players = game.getPlayers();

        for (Player player : players) {
            assertTrue(player.hasTilesInPool() || player.getRack().size() > 0);
        }
    }
    
    @Test
    void test_Players_Racks_Are_Initialized() {
        
        List<Player> players = game.getPlayers();

        for (Player player : players) {
            assertTrue(player.getRack().size() <= 5);
        }
    }
    
    @Test
    void test_Get_Players_Returns_Copy() {
        
        List<Player> original = game.getPlayers();
        original.clear(); 

        List<Player> after = game.getPlayers();
        assertEquals(2, after.size());
    }
    
    @Test
    void test_Display_Players_Racks_Prints_Correctly() {
        game.displayPlayersRacks();
        String output = outputStream.toString();

        
        assertTrue(output.contains("Rack de Joueur 1"), "Output should contain rack for Joueur 1");
        assertTrue(output.contains("Rack de Joueur 2"), "Output should contain rack for Joueur 2");
        assertTrue(output.length() > 0, "Output should not be empty");
    }
    
    
    @Test
    void test_Get_Current_Player_Returns_Valid_Player() {
        Player currentPlayer = game.getCurrentPlayer();

        assertNotNull(currentPlayer, "Current player should not be null");
        assertTrue(
            game.getPlayers().contains(currentPlayer),
            "Current player should be one of the game's players"
        );
    }
}
