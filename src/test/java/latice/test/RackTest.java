package latice.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import latice.model.Color;
import latice.model.Rack;
import latice.model.Shape;
import latice.model.Tile;

public class RackTest {
	
	
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    
    private Rack rack;
    private Tile tile1;
    private Tile tile2;

    @BeforeEach
    void setUp() {
    	System.setOut(new PrintStream(outContent));
        rack = new Rack(3); // capacity 3
        tile1 = new Tile(Color.RED, Shape.FEATHER);
        tile2 = new Tile(Color.GREEN, Shape.TURTLE);
    }
    
    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    void testAddTileWithinCapacity() {
        rack.addTile(tile1);
        assertEquals(1, rack.size());
        assertTrue(rack.getTiles().contains(tile1));
    }

    @Test
    void testAddTileExceedCapacity() {
        rack.addTile(tile1);
        rack.addTile(tile2);
        rack.addTile(new Tile(Color.GREEN, Shape.BIRD));
        rack.addTile(new Tile(Color.YELLOW, Shape.FLOWER)); 

        assertEquals(3, rack.size());  
    }

    @Test
    void testRemoveTileByIndexValid() {
        rack.addTile(tile1);
        rack.addTile(tile2);

        Tile removed = rack.removeTile(0);
        assertEquals(tile1, removed);
        assertEquals(1, rack.size());
        assertFalse(rack.getTiles().contains(tile1));
    }

    @Test
    void testRemoveTileByIndexInvalid() {
        rack.addTile(tile1);

        Tile removed = rack.removeTile(5); 
        assertNull(removed);
        assertEquals(1, rack.size());
    }

    @Test
    void testRemoveTileByObject() {
        rack.addTile(tile1);
        rack.addTile(tile2);

        rack.removeTile(tile1);
        assertEquals(1, rack.size());
        assertFalse(rack.getTiles().contains(tile1));
    }

    @Test
    void testClear() {
        rack.addTile(tile1);
        rack.addTile(tile2);

        rack.clear();
        assertTrue(rack.isEmpty());
        assertEquals(0, rack.size());
    }

    @Test
    void testIsFull() {
        rack.addTile(tile1);
        rack.addTile(tile2);
        assertFalse(rack.isFull());

        rack.addTile(new Tile(Color.GREEN, Shape.BIRD));
        assertTrue(rack.isFull());
    }

    @Test
    void testIsEmpty() {
        assertTrue(rack.isEmpty());

        rack.addTile(tile1);
        assertFalse(rack.isEmpty());
    }

    @Test
    void testGetTilesReturnsCopy() {
        rack.addTile(tile1);

        List<Tile> tilesCopy = rack.getTiles();
        assertEquals(1, tilesCopy.size());

        tilesCopy.clear(); 

        assertEquals(1, rack.size());  
    }
    
    @Test
    void testDisplayEmptyRack() {
        rack.getTiles().clear();
    	rack.display();
    	assertEquals("The rack is empty" + System.lineSeparator(), outContent.toString());

    }

    @Test
    void testDisplayWithTiles() {
        rack.addTile(tile1);
        rack.display();

        String expectedOutput = "1. " + tile1.toString() + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString());
    }
    
}
