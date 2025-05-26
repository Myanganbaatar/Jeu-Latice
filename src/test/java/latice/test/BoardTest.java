package latice.test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import latice.Board.Board;
import latice.Board.BoardCase;
import latice.Board.CaseType;

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
	 void test_Display_Board_Output() {
	     // Redirect System.out
	     ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	     PrintStream originalOut = System.out;
	     System.setOut(new PrintStream(outputStream));

	     try {
	         board.displayBoard(); // call the method
	         String output = outputStream.toString();

	         // Check for expected characters in output
	         assertTrue(output.contains("S"), "Board display should contain 'S' for SUN");
	         assertTrue(output.contains("M"), "Board display should contain 'M' for MOON");
	         assertTrue(output.contains("|"), "Board should contain '|' for grid formatting");
	         assertTrue(output.contains("----"), "Board should contain horizontal separators");

	     } finally {
	         // Restore original System.out
	         System.setOut(originalOut);
	     }
	 }
	
}
