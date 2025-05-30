package latice.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.ByteArrayInputStream;
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



class PlayerTest {

    private Player player;

    @BeforeEach
    void setUp() {
        player = new Player("TestPlayer");
    }

    @Test
    void testAddToPoolAndInitializeRack() {
        Tile t1 = new Tile(Color.GREEN,Shape.DOLPHIN);
        Tile t2 = new Tile(Color.GREEN,Shape.FLOWER);
        player.addToPool(t1);
        player.addToPool(t2);

        assertEquals(2, player.getPoolSize());
        player.initializeRack();
        assertEquals(0, player.getPoolSize());
        assertTrue(player.getRack().getTiles().contains(t1));
    }

    @Test
    void testGettersSetters() {
        assertEquals("TestPlayer", player.getName());

        player.setTilesPlaced(1);
        player.incrementTilesPlaced();
        assertEquals(2, player.getTilesPlaced());

        player.addScore(10);
        assertEquals(10, player.getScore());
    }

    @Test
    void testFillRackFromPool() {
        for (int i = 0; i < 5; i++) {
            player.addToPool(new Tile(Color.GREEN,Shape.DOLPHIN));
        }
        player.fillRackFromPool();
        assertTrue(player.getRack().isFull());
    }
    
    @Test
    void testRackIsInitiallyEmpty() {
        assertNotNull(player.getRack());
        assertTrue(player.getRack().getTiles().isEmpty());
    }
    
    @Test
    void testHasTilesInPool() {
        assertFalse(player.hasTilesInPool());

        player.addToPool(new Tile(Color.GREEN, Shape.FLOWER));
        assertTrue(player.hasTilesInPool());
    }
    
    @Test
    void testChooseTile_invalidThenValidInput() {
       
        Tile tile1 = new Tile(Color.RED, Shape.DOLPHIN);
        Tile tile2 = new Tile(Color.NAVY, Shape.FLOWER);
        player.getRack().addTile(tile1);
        player.getRack().addTile(tile2);

        
        String userInput = "5\n1\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(userInput.getBytes()));

        
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        Tile chosen = player.chooseTile(scanner);

        System.setOut(System.out); 

        
        assertEquals(tile1, chosen);
        String output = outContent.toString();
        assertTrue(output.contains("⛔ Index invalide"));
    }
    
    @Test
    void testChooseTile_validInput() {
        
        Tile tile1 = new Tile(Color.RED, Shape.DOLPHIN);
        Tile tile2 = new Tile(Color.YELLOW, Shape.DOLPHIN);
        player.getRack().addTile(tile1);
        player.getRack().addTile(tile2);

        
        String userInput = "2\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(userInput.getBytes()));

        
        Tile chosen = player.chooseTile(scanner);

        
        assertEquals(tile2, chosen);
    }
    
    @Test
    void testAskCoordinate() {
        
        String input = "abc\n10\n3\n"; 
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(inputStream);

        Player player = new Player("TestPlayer");
        int max = 9;

        int result = player.askCoordinate(scanner, "row", max);

        
        assertEquals(2, result);
    }
    
    
    
    
    
    


}


    

   

