package latice.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import latice.Board.Board;
import latice.Board.BoardCase;
import latice.Board.CaseType;

class BoardTest {

	@Test
    public void testBoardInitialization() {
        int boardSize = 9;
        Board board = new Board(boardSize);

        //pour vérifier qu'on a la bonne taille pour le plateau du jeu
        assertEquals(9, boardSize);
    }
	
	@Test
	public void testLuneCase() {
	    Board board = new Board(9);
	    BoardCase c = board.getCase(4, 4);
	    assertEquals(CaseType.LUNE, c.getType(), "La case au centre devrait être LUNE");
	}


	
}
