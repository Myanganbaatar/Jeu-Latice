package latice.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import latice.Board.Board;
import latice.model.Color;
import latice.model.Player;
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
    
    @Test
    void testCalculateScoreNoMatches() {
        // No tiles around position (4,4)
        int score = referee.calculateScore(board, 4, 4, tile);
        assertEquals(0, score);
    }
    
    
    @Test
    void testCalculateScoreTwoMatchingSides() {
        Tile matchingTile1 = new Tile(Color.GREEN, Shape.FEATHER);  // Color match
        Tile matchingTile2 = new Tile(Color.RED, Shape.BIRD); // Shape match

        board.placeTile(3, 4, matchingTile1); // Above (4,4)
        board.placeTile(5, 4, matchingTile2); // Below (4,4)

        int score = referee.calculateScore(board, 4, 4, tile);
        assertEquals(1, score);  // 2 matching sides → 1 point
    }
    
    @Test
    void testCalculateScoreThreeMatchingSides() {
        board.placeTile(3, 4, new Tile(Color.GREEN, Shape.FEATHER));    // Above
        board.placeTile(5, 4, new Tile(Color.RED, Shape.BIRD));   // Below
        board.placeTile(4, 3, new Tile(Color.GREEN, Shape.FLOWER));  // Left

        int score = referee.calculateScore(board, 4, 4, tile);
        assertEquals(2, score);  // 3 matching sides → 2 points
    }
    
    @Test
    void testCalculateScoreFourMatchingSides() {
        board.placeTile(3, 4, new Tile(Color.GREEN, Shape.FEATHER));    // Above
        board.placeTile(5, 4, new Tile(Color.RED, Shape.BIRD));   // Below
        board.placeTile(4, 3, new Tile(Color.GREEN, Shape.FLOWER));  // Left
        board.placeTile(4, 5, new Tile(Color.NAVY, Shape.BIRD));  // Right

        int score = referee.calculateScore(board, 4, 4, tile);
        assertEquals(4, score);  // 4 matching sides → 4 points
    }
    
    @Test
    void testCalculateScoreOneMatchingSide() {
        board.placeTile(3, 4, new Tile(Color.RED, Shape.FEATHER));

        int score = referee.calculateScore(board, 4, 4, tile);
        assertEquals(0, score);
    }
    
    @Test
    void testIsGameOverAllEmpty() {
        Player p1 = new Player("Player 1");
        Player p2 = new Player("Player 2");

        p1.getRack().getTiles().clear();

        p2.getRack().getTiles().clear();


        List<Player> players = List.of(p1, p2);

        assertTrue(referee.isGameOver(board, players));
    }
    
    
    
    
    @Test
    void testGetWinnerSingleWinner() {
        Player p1 = new Player("Player 1");
        Player p2 = new Player("Player 2");

        p1.setTilesPlaced(5);
        p2.setTilesPlaced(3);

        List<Player> players = List.of(p1, p2);

        assertEquals(p1, referee.getWinner(players));
    }
    
    @Test
    public void testGetWinnerTie() {
    	Player p1 = new Player("Player 1");
        Player p2 = new Player("Player 2");

        p1.setTilesPlaced(4);
        p2.setTilesPlaced(4);

        List<Player> players = List.of(p1, p2);

        assertNull(referee.getWinner(players));
    }
    
    @Test
    public void testGetWinnerNoPlayers() {
        List<Player> players = new ArrayList<>();

        assertNull(referee.getWinner(players));
    }
    
    
    
}
