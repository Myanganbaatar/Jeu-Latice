package latice.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import latice.model.Player;
import latice.model.Shape;
import latice.model.Tile;
import latice.rules.Referee;
import latice.Board.Board;
import latice.model.Color;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import java.io.*;
import java.util.*;

class PlayerTest {

    Player player;
    Tile redDolphin = new Tile(Color.RED, Shape.DOLPHIN);
    Tile blueElephant = new Tile(Color.GREEN, Shape.FEATHER);

    @BeforeEach
    void setup() {
        player = new Player("Alice");
    }

    

    @Test
    void testPoolAddAndInitializeRack() {
        player.addToPool(redDolphin);
        player.addToPool(blueElephant);
        assertEquals(2, player.getPoolSize());
        player.initializeRack();
        assertEquals(2, player.getRack().size());
        assertEquals(0, player.getPoolSize());
    }

    @Test
    void testHasTilesInPool() {
        assertFalse(player.hasTilesInPool());
        player.addToPool(redDolphin);
        assertTrue(player.hasTilesInPool());
    }

    @Test
    void testFillRackFromPool() {
        player.addToPool(redDolphin);
        player.addToPool(blueElephant);
        player.fillRackFromPool();
        assertEquals(2, player.getRack().size());
        assertEquals(0, player.getPoolSize());
    }

    @Test
    void testDisplayRackOutputsCorrectly() {
        player.getRack().addTile(redDolphin);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        player.displayRack();

        String output = outContent.toString();
        assertTrue(output.contains("Alice's rack:"));
        assertTrue(output.contains(redDolphin.toString()));

        System.setOut(System.out);
    }

    
    @Test
    void testPlayTurnValidMove() {
       
        player.getRack().addTile(redDolphin);

       
        Board board = new Board(9) {
            @Override
            public void placeTile(int r, int c, Tile tile) {
                /
                assertEquals(redDolphin, tile);
            }

            @Override
            public void displayBoard() {
               
            }
        };

        
        Referee referee = new Referee() {
            @Override
            public boolean isPlacementValid(Board b, int row, int col, Tile tile, boolean isFirstMove) {
                return true;
            }
        };

        
        String input = "1\n1\n1\n";
        Scanner scanner = new Scanner(input);

        
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        player.playTurn(board, referee, scanner, false);

        System.setOut(System.out);

        
        assertEquals(0, player.getRack().size());
        
        assertEquals(1, player.getTilesPlaced());

        
        assertTrue(outContent.toString().contains("✅ Coup valide"));
    }

    @Test
    void testPlayTurnInvalidThenValidMove() {
        
        player.getRack().addTile(redDolphin);

        Board board = new Board(9) {
            @Override
            public void placeTile(int r, int c, Tile tile) {
                assertEquals(redDolphin, tile);
            }

            @Override
            public void displayBoard() {
                // no-op
            }
        };

        
        class RefereeStub extends Referee {
            int calls = 0;
            @Override
            public boolean isPlacementValid(Board b, int row, int col, Tile tile, boolean isFirstMove) {
                calls++;
                return calls == 2; 
            }
        }
        RefereeStub referee = new RefereeStub();

        
        String input = "1\n1\n1\n1\n2\n2\n";
        Scanner scanner = new Scanner(input);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        player.playTurn(board, referee, scanner, false);

        System.setOut(System.out);

        
        assertEquals(1, player.getTilesPlaced());
        assertEquals(0, player.getRack().size());

        String output = outContent.toString();
        assertTrue(output.contains("⛔ Coup invalide"));
        assertTrue(output.contains("✅ Coup valide"));
    }

    @Test
    void testChooseTileHandlesInvalidInput() {
        player.getRack().addTile(redDolphin);
        player.getRack().addTile(blueElephant);

       
        String input = "a\n0\n2\n";
        Scanner scanner = new Scanner(input);

        
        class TestPlayer extends Player {
            public TestPlayer(String name) {
                super(name);
            }

            public Tile testChooseTile(Scanner scanner) {
                return super.chooseTile(scanner);
            }
        }
        TestPlayer testPlayer = new TestPlayer("Bob");
        testPlayer.getRack().addTile(redDolphin);
        testPlayer.getRack().addTile(blueElephant);

        Tile chosen = testPlayer.testChooseTile(scanner);
        assertEquals(blueElephant, chosen);
    }

    @Test
    void testAskCoordinateHandlesInvalidInput() {
        
        class TestPlayer extends Player {
            public TestPlayer(String name) {
                super(name);
            }
            public int testAskCoordinate(Scanner scanner, String label, int max) {
                return super.askCoordinate(scanner, label, max);
            }
        }
        TestPlayer testPlayer = new TestPlayer("Bob");

        
        String input = "x\n0\n5\n";
        Scanner scanner = new Scanner(input);

        int coord = testPlayer.testAskCoordinate(scanner, "ligne", 9);
        assertEquals(4, coord); 
    }
}


