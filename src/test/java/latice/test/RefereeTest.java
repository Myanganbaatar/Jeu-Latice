package latice.test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import latice.Board.Board;
import latice.model.Color;
import latice.model.Shape;
import latice.model.Tile;
import latice.rules.Referee;

public class RefereeTest {

    private Referee referee;
    private Board board;
    private Tile tile;

    @BeforeEach
    void setUp() {
        referee = new Referee();
        board = new Board(9);
        tile = new Tile(Color.GREEN,Shape.BIRD);  // Adjust based on your Tile constructor
    }

    @Test
    void testPlacementOutOfBounds() {
        assertFalse(referee.isPlacementValid(board, -1, 0, tile, false));
        assertFalse(referee.isPlacementValid(board, 0, 9, tile, false));
        assertFalse(referee.isPlacementValid(board, 9, 9, tile, false));
    }

    @Test
    void testPlacementOnOccupiedTile() {
        board.placeTile(2, 2, tile);  // Assuming you have a placeTile method
        assertFalse(referee.isPlacementValid(board, 2, 2, tile, false));
    }

    @Test
    void testFirstMoveMustBeCenter() {
        assertTrue(referee.isPlacementValid(board, 4, 4, tile, true));
        assertFalse(referee.isPlacementValid(board, 4, 3, tile, true));
    }

    @Test
    void testValidAdjacentPlacement() {
        Tile neighbor = new Tile(Color.RED, Shape.DOLPHIN);
        board.placeTile(4, 4, neighbor);
        Tile newTile = new Tile(Color.RED, Shape.FEATHER);
        assertTrue(referee.isPlacementValid(board, 4, 5, newTile, false));
    }

    @Test
    void testInvalidAdjacentPlacementNoMatch() {
        Tile neighbor = new Tile(Color.RED,Shape.GECKO);
        board.placeTile(4, 4, neighbor);
        Tile newTile = new Tile(Color.GREEN, Shape.DOLPHIN);
        assertFalse(referee.isPlacementValid(board, 4, 5, newTile, false));
    }
    
}
