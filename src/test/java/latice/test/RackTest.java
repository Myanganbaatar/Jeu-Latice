package latice.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

}
