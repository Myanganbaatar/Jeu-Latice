package latice.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @BeforeEach
    void setUp() {
        rack = new Rack(5);  // Max 5 tiles per rack
        tile1 = new Tile(Color.RED, Shape.BIRD);
        tile2 = new Tile(Color.RED, Shape.DOLPHIN);
    }

    @Test
    void testAddTileIncreasesSize() {
        rack.addTile(tile1);
        assertEquals(1, rack.size());
    }
    
    @Test
    void testAddTileUpToCapacity() {
        for (int i = 0; i < 5; i++) {
            rack.addTile(new Tile(Color.values()[i], Shape.FEATHER));
        }
        assertEquals(5, rack.size());
        rack.addTile(tile1);  // should not be added
        assertEquals(5, rack.size(), "Rack should not exceed capacity of 5 tiles");
    }
    
    
    @Test
    void testIsEmpty() {
        assertTrue(rack.isEmpty());
        rack.addTile(tile1);
        assertFalse(rack.isEmpty());
    }

}
