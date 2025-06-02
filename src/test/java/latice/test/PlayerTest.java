package latice.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import latice.model.Player;
import latice.model.Shape;
import latice.model.Tile;
import latice.rules.Referee;
import latice.Board.Board;
import latice.model.Color;
import latice.model.Deck;

class PlayerTest {
    private Player player;
    private Tile tile1;
    private Tile tile2;
    private Deck deck;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outContent));
        player = new Player("Test Player");
        tile1 = new Tile(Color.RED, Shape.DOLPHIN);
        tile2 = new Tile(Color.MAGENTA, Shape.GECKO);
        deck = new Deck();
    }

    @Test
    void testConstructor() {
        assertEquals("Test Player", player.getName());
        assertEquals(0, player.getScore());
        assertEquals(0, player.getTilesPlaced());
        assertEquals(0, player.getExtraActions());
        assertTrue(player.getPoolSize()==0);
        assertTrue(player.getRack().isEmpty());
    }

    @Test
    void testAddToPool() {
        player.addToPool(tile1);
        assertEquals(1, player.getPoolSize());
        assertTrue(player.hasTilesInPool());
        
        player.addToPool(null);
        assertEquals(1, player.getPoolSize()); // Null shouldn't be added
    }

    @Test
    void testInitializeRack() {
        // Add tiles to pool
        player.addToPool(tile1);
        player.addToPool(tile2);
        
        player.initializeRack();
        
        assertEquals(0, player.getPoolSize());
        assertEquals(2, player.getRack().size());
    }

    @Test
    void testFillRackFromPool() {
        // Add more tiles than rack capacity
        for (int i = 0; i < 7; i++) {
            player.addToPool(new Tile(Color.values()[i % Color.values().length], 
                           Shape.values()[i % Shape.values().length]));
        }
        
        player.fillRackFromPool();
        
        assertEquals(5, player.getRack().size()); // Rack at full capacity
        assertEquals(2, player.getPoolSize()); // 7 total - 5 in rack
    }

    @Test
    void testScoreManagement() {
        player.addScore(5);
        assertEquals(5, player.getScore());
        
        player.addScore(-3);
        assertEquals(2, player.getScore());
    }

    @Test
    void testTilePlacementTracking() {
        assertEquals(0, player.getTilesPlaced());
        
        player.incrementTilesPlaced();
        assertEquals(1, player.getTilesPlaced());
        
        player.setTilesPlaced(3);
        assertEquals(3, player.getTilesPlaced());
    }

    

    @Test
    void testExchangeRackWithNullDeck() {
        player.getRack().addTile(tile1);
        int initialRackSize = player.getRack().size();
        
        player.exchangeRack(null);
        
        assertEquals(initialRackSize, player.getRack().size()); // No change
    }

    @Test
    void testExtraActions() {
        assertEquals(0, player.getExtraActions());
        
        player.addExtraAction();
        assertEquals(1, player.getExtraActions());
        
        player.useExtraAction();
        assertEquals(0, player.getExtraActions());
        
        // Can't go negative
        player.useExtraAction();
        assertEquals(0, player.getExtraActions());
    }

    

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }
    
    @Test
    void testExchangeRack() {
        Deck deck = new Deck();
        deck.initializeTiles(); // À implémenter si nécessaire
        for (int i = 0; i < 5; i++) {
            player.getRack().addTile(new Tile(Color.GREEN,Shape.BIRD));
        }
        player.exchangeRack(deck);
        assertEquals(5, player.getRack().size());
    }
}


    

   

