package latice.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import latice.Board.Board;
import latice.controller.PlayTile;
import latice.model.Game;
import latice.model.Player;
import latice.rules.Referee;

public class PlayTileTest {

    private Game game;
    private Player player;
    private Board board;

    @BeforeEach
    void setUp() {
        game = new Game();
        game.initializeGame();
        player = game.getCurrentPlayer();
        board = new Board(9);
    }

    @Test
    void testFirstMoveOnMoon() {
        new PlayTile().playTile(new Scanner("1\n5\n5\n"), player, board, new Referee(), game, true);

        assertTrue(board.hasTile(4, 4));
        assertEquals(1, player.getTilesPlaced());
        assertEquals(5, player.getRack().size(), "le rack doit etre complete apres la pose");
    }

    @Test
    void testFirstMoveOutsideMoonIsRetried() {
        // 1er essai en (1,1) refuse, 2eme essai sur la lune
        new PlayTile().playTile(new Scanner("1\n1\n1\n1\n5\n5\n"), player, board, new Referee(), game, true);

        assertTrue(board.hasTile(4, 4));
        assertEquals(1, player.getTilesPlaced());
    }

    @Test
    void testInvalidInputThenValidMove() {
        // "abc" est une saisie invalide, le joueur recommence avec 1 / 5 / 5
        new PlayTile().playTile(new Scanner("abc\n1\n5\n5\n"), player, board, new Referee(), game, true);

        assertTrue(board.hasTile(4, 4));
        assertEquals(1, player.getTilesPlaced());
    }
}
