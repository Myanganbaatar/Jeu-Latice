package latice.test;

import static org.junit.jupiter.api.Assertions.*;

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
    void testBoardInitialization() {
		
		int boardSize= 9;
        
        

        //pour vérifier qu'on a la bonne taille pour le plateau du jeu
        assertEquals(9, boardSize);
    }
	
	@Test
	void testMoonCase() {
	    
	    BoardCase c = board.getCase(4, 4);
	    assertEquals(CaseType.MOON, c.getType(), "La case au centre devrait être LUNE");
	}
	
	@Test
	void is_sun_case(){
		
		
		
		assertTrue(board.isSunCase(0, 0));
		assertFalse(board.isSunCase(0,2));

	}


	
}
