package latice.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import latice.model.Color;
import latice.model.Rack;
import latice.model.Shape;
import latice.model.Tile;

public class RackTest {
	private Rack rack;
    private Tile tile1;
    private Tile tile2;
    
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        rack = new Rack(5);  // Max 5 tiles per rack
        tile1 = new Tile(Color.RED, Shape.BIRD);
        tile2 = new Tile(Color.RED, Shape.DOLPHIN);
        
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    void test_Add_Tile_Increases_Size() {
        rack.addTile(tile1);
        assertEquals(1, rack.size());
    }
    
    @Test
    void test_Add_Tile_Up_To_Capacity() {
        for (int i = 0; i < 5; i++) {
            rack.addTile(new Tile(Color.values()[i], Shape.FEATHER));
        }
        assertEquals(5, rack.size());
        rack.addTile(tile1);  // should not be added
        assertEquals(5, rack.size(), "Rack should not exceed capacity of 5 tiles");
    }
    
    
    @Test
    void test_Is_Empty() {
        assertTrue(rack.isEmpty());
        rack.addTile(tile1);
        assertFalse(rack.isEmpty());
    }
    
    @Test
    void test_Remove_Tile_Valid_Index() {
        rack.addTile(tile1);
        Tile removed = rack.removeTile(0);
        assertEquals(tile1, removed, "The removed tile should match the one added.");
        assertTrue(rack.isEmpty(), "Rack should be empty after removing the only tile.");
    }
    
    @Test
    void test_Remove_Tile_Invalid_ndex() {
        rack.addTile(tile1);
        Tile removed = rack.removeTile(5); // Invalid index
        assertNull(removed, "Removing a tile with invalid index should return null.");
    }
    
    
    @Test
    void test_Is_Full() {
        for (int i = 0; i < 5; i++) {
            rack.addTile(new Tile(Color.values()[i], Shape.FEATHER));
        }
        assertTrue(rack.isFull(), "Rack should be full after adding 5 tiles.");
    }
    
    @Test
    void test_Display_With_Tiles() {
        rack.addTile(tile1);
        rack.addTile(tile2);
        rack.display();
        String output = outputStream.toString();
        assertTrue(output.contains("1."), "Should label the first tile.");
        assertTrue(output.contains("2."), "Should label the second tile.");
    }
    
    @Test
    void test_Display_Empty_ack() {
        rack.display();
        String output = outputStream.toString().trim();
        assertTrue(output.contains("Le rack est vide"), "Should display that the rack is empty.");
    }
    
    @Test
    void test_Get_Tiles_Returns_Correct_Copy() {
        Tile tile = new Tile(Color.RED, Shape.BIRD);
        rack.addTile(tile);

        List<Tile> tilesCopy = rack.getTiles();

        // Contains the same tile
        assertEquals(1, tilesCopy.size(), "Returned list should have one tile.");
        assertEquals(tile, tilesCopy.get(0), "Returned tile should match the added one.");

        // Modify copy and check rack is unchanged
        tilesCopy.clear();
        assertEquals(1, rack.size(), "Clearing returned list should not affect the rack.");
    }
}
