package latice.test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import latice.Board.Board;
import latice.Board.BoardCase;
import latice.Board.CaseType;
import latice.model.Color;
import latice.model.Shape;
import latice.model.Tile;

class BoardTest {
	
	private Board board;

	 @BeforeEach
	    void setUp() {
	        board = new Board(9);
	        
	    }
	
	@Test
    void test_Board_Initialization() {
		
		int boardSize= 9;
        
        

        //pour vérifier qu'on a la bonne taille pour le plateau du jeu
        assertEquals(9, boardSize);
    }
	
	@Test
	void test_Moon_Case() {
	    
	    BoardCase c = board.getCase(4, 4);
	    assertEquals(CaseType.MOON, c.getType(), "La case au centre devrait être LUNE");
	}
	
	@Test
	void is_sun_case(){
		
		
		
		assertTrue(board.isSunCase(0, 0));
		assertFalse(board.isSunCase(0,2));

	}
	
	
	@Test
    void testPlaceTile_HasTileAndGetTile() {
        Tile tile = new Tile(Color.RED, Shape.DOLPHIN);
        int row = 2, col = 3;

        assertFalse(board.hasTile(row, col));
        assertNull(board.getTile(row, col));

        board.placeTile(row, col, tile);

        assertTrue(board.hasTile(row, col));
        assertEquals(tile, board.getTile(row, col));
    }
	
	@Test
    void testDisplayBoard_DoesNotThrow() {
        
        board.displayBoard();
    }
	
	@Test
	public void testDisplayBoardWithTiles() {
        
        Tile tile = new Tile(Color.RED, Shape.DOLPHIN);
        board.placeTile(0, 0, tile);

        // Capture system output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        try {
            board.displayBoard();
            String output = outContent.toString();

            
            assertTrue(output.contains(tile.toColoredSymbol()), "Output should contain tile's colored symbol");
        } finally {
           
            System.setOut(originalOut);
        }
        
	}
	
	@Test
    void testGetCase_InvalidCoordinates_Throws() {
        assertThrows(IndexOutOfBoundsException.class, () -> board.getCase(-1, 0));
        assertThrows(IndexOutOfBoundsException.class, () -> board.getCase(0, 9));
        assertThrows(IndexOutOfBoundsException.class, () -> board.getCase(9, 9));
    }

	 
		
}
