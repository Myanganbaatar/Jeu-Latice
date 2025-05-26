package latice.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import latice.model.Player;
import latice.model.Shape;
import latice.model.Tile;
import latice.model.Color;

public class PlayerTest {
    private Player player;

    @BeforeEach
    void setUp() {
        player = new Player("Alice");
    }

    @Test
    void test_Get_Name() {
        assertEquals("Alice", player.getName());
    }
    
    
    @Test
    void test_Add_Tile_To_Pool_And_Initialize_Rack() {
        Tile tile1 = new Tile(Color.RED, Shape.BIRD);
        Tile tile2 = new Tile(Color.MAGENTA, Shape.DOLPHIN);

        player.addToPool(tile1);
        player.addToPool(tile2);

        assertEquals(2, player.getPoolSize());

        player.initializeRack();

        assertEquals(0, player.getPoolSize());
        assertEquals(2, player.getRack().size());
    }
    
    @Test
    void test_Has_Tiles_In_Pool() {
        assertFalse(player.hasTilesInPool());

        player.addToPool(new Tile(Color.GREEN,Shape.FLOWER));
        assertTrue(player.hasTilesInPool());
    }
    
    @Test
    void test_Initialize_Rack_Fills_Up_To_Five() {
        for (int i = 0; i < 7; i++) {
            player.addToPool(new Tile(Color.GREEN,Shape.GECKO));
        }

        player.initializeRack();

        assertEquals(2, player.getPoolSize()); 
        assertEquals(5, player.getRack().size()); 
    }
    
    @Test
    void test_Initialize_Rack_Does_No_Over_fill() {
        for (int i = 0; i < 3; i++) {
            player.addToPool(new Tile(Color.GREEN,Shape.GECKO));
        }

        player.initializeRack();

        assertEquals(0, player.getPoolSize());
        assertEquals(3, player.getRack().size()); 
    }
    
    @Test
    void test_Display_Rack() {
        
        player.displayRack();
    }
    
}
