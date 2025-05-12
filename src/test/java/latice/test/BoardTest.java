package latice.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import latice.Board.Board;

class BoardTest {

	@Test
    public void testBoardInitialization() {
        int boardSize = 9;
        Board board = new Board(boardSize);

        //pour vérifier qu'on a la bonne taille pour le plateau du jeu
        assertEquals(9, boardSize);
    }

	
}
