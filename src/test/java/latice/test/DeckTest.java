package latice.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.List;

import latice.model.Shape;
import latice.model.Tile;
import latice.model.Color;



import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import latice.model.Deck;

public class DeckTest {
	private Deck deck;
	
	@BeforeEach
	void setUp() {
		deck = new Deck();
	}
	
	@Test
	void test_Initial_Tile_Count() {
        int expectedTotal = Color.values().length * Shape.values().length * 2;
        assertEquals(expectedTotal, deck.getTotalTiles());
    }
	
	@Test
	void test_Shuffle_Changes_Order() {
        List<Tile> beforeShuffle = deck.getAllTiles();
        deck.shuffle();
        List<Tile> afterShuffle = deck.getAllTiles();

        
        assertEquals(beforeShuffle.size(), afterShuffle.size());
        assertNotEquals(beforeShuffle, afterShuffle);
	}
	
	
	@Test
	void testAllTilesAreDuplicated() {
	    for (Color color : Color.values()) {
	        for (Shape shape : Shape.values()) {
	            int count = 0;
	            for (Tile tile : deck.getAllTiles()) {
	                if (tile.getColor() == color && tile.getShape() == shape) {
	                    count++;
	                }
	            }
	            assertEquals(2, count, "The tile " + color + "-" + shape + " il apparaitre deux fois dans la pioche");
	        }
	    }
	}
	
}

