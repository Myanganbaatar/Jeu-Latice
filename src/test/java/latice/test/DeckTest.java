package latice.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import latice.model.Shape;
import latice.model.Tile;
import latice.model.Color;



import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import latice.model.Deck;

public class DeckTest {
	private Deck deck;
	private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
	
	@BeforeEach
	void setUp() {
		deck = new Deck();
		System.setOut(new PrintStream(outputStream));
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
	void test_All_Tiles_Are_Duplicated() {
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
	
	@Test
    void test_Draw_Tile_Reduces_Deck_Size() {
        int initialSize = deck.getTotalTiles();
        Tile drawn = deck.drawTile();
        assertNotNull(drawn, "La tuile piochée ne devrait pas être null");
        assertEquals(initialSize - 1, deck.getTotalTiles(), "Le nombre de tuiles devrait diminuer de 1 après un tirage");
    }
	
	 @Test
	    void test_Draw_All_Tiles() {
	        int total = deck.getTotalTiles();
	        for (int i = 0; i < total; i++) {
	            assertNotNull(deck.drawTile(), "Chaque tirage avant l'épuisement doit retourner une tuile");
	        }
	        assertEquals(0, deck.getTotalTiles(), "Le paquet devrait être vide après avoir tiré toutes les tuiles");
	        assertNull(deck.drawTile(), "Tirer une tuile d'un paquet vide devrait retourner null");
	    }
	
	 
	 @Test
	    void test_Display_All_Tiles_Output() {
	        deck.displayAllTiles();
	        String output = outputStream.toString();

	        // Basic checks
	        assertTrue(output.contains("ALL GAME TILES"), "Output should contain the title");
	        assertTrue(output.contains("Total: 72 tiles"), "Should show total count of 72 tiles");

	        // Check at least one color and shape is listed
	        assertTrue(output.contains("Color RED"), "Should list color RED");
	        assertTrue(output.contains("DOLPHIN"), "Should list shape DOLPHIN");
	        
	    }

	    // Restore System.out after tests (optional, to keep console clean)
	    @org.junit.jupiter.api.AfterEach
	    void restoreSystemOut() {
	        System.setOut(originalOut);
	    }
}

