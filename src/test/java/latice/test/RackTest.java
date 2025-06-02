package latice.test;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import latice.model.Color;
import latice.model.Deck;
import latice.model.Rack;
import latice.model.Shape;
import latice.model.Tile;

import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;



import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

class RackTest {

    private Rack rack;
    private Tile tile1;
    private Tile tile2;

    @BeforeEach
    void setUp() {
        rack = new Rack(5);
        tile1 = new Tile(Color.GREEN,Shape.DOLPHIN);
        tile2 = new Tile(Color.RED,Shape.FLOWER);
    }

    @Test
    void testAddTile() {
        rack.addTile(tile1);
        assertEquals(1, rack.size());
        assertTrue(rack.getTiles().contains(tile1));
    }

    @Test
    void testAddTileBeyondCapacity() {
        rack.addTile(tile1);
        rack.addTile(tile2);
        rack.addTile(new Tile(Color.GREEN,Shape.DOLPHIN)); 
        rack.addTile(new Tile(Color.GREEN,Shape.GECKO));
        rack.addTile(new Tile(Color.RED,Shape.DOLPHIN));
        assertEquals(5, rack.size());
    }

    @Test
    void testRemoveTileByIndex() {
        rack.addTile(tile1);
        Tile removed = rack.removeTile(0);
        assertEquals(tile1, removed);
        assertTrue(rack.isEmpty());
    }

    @Test
    void testRemoveTileByObject() {
        rack.addTile(tile1);
        rack.removeTile(tile1);
        assertFalse(rack.getTiles().contains(tile1));
    }

    @Test
    void testIsFullAndEmpty() {
        rack.addTile(tile1);
        rack.addTile(tile2);
        rack.addTile(new Tile(Color.GREEN,Shape.DOLPHIN)); 
        rack.addTile(new Tile(Color.GREEN,Shape.GECKO));
        rack.addTile(new Tile(Color.RED,Shape.DOLPHIN));
        assertTrue(rack.isFull());
    }

    @Test
    void testClear() {
        rack.addTile(tile1);
        rack.addTile(tile2);
        rack.clear();
        assertTrue(rack.isEmpty());
    }

    @Test
    void testExchangeAllTiles() {
        // Deck avec des tuiles à réutiliser
        Deck deck = new Deck();
        deck.initializeTiles(); // méthode à créer si nécessaire

        rack.addTile(tile1);
        rack.addTile(tile2);
        rack.exchangeAllTiles(deck);

        assertEquals(5, rack.size());
        assertFalse(rack.getTiles().contains(tile1));
        assertFalse(rack.getTiles().contains(tile2));
    }

    

    @Test
    void testGetTilesReturnsCopy() {
        rack.addTile(tile1);
        List<Tile> copy = rack.getTiles();
        copy.clear();
        assertEquals(1, rack.size());
    }
}