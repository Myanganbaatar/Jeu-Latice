package latice.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private List<Tile> tiles;

    public Deck() {
        tiles = new ArrayList<>();
        initializeTiles();
    }

    private void initializeTiles() {
        for (Color color : Color.values()) {
            for (Shape shape : Shape.values()) {
                tiles.add(new Tile(color, shape));
                tiles.add(new Tile(color, shape));
            }
        }
    }

    public void shuffle() {
        Collections.shuffle(tiles);
    }

    public Tile drawTile() {
        return tiles.isEmpty() ? null : tiles.remove(0);
    }

    public int getTotalTiles() {
        return tiles.size();
    }

    public void displayAllTiles() {
        System.out.println("\n=== ALL GAME TILES ===");
        System.out.println("Total: " + tiles.size() + " tiles");

        // Par couleur
        for (Color color : Color.values()) {
            System.out.println("\n--- Color " + color + " ---");

            // Par forme
            for (Shape shape : Shape.values()) {
                long count = tiles.stream()
                    .filter(t -> t.getColor() == color && t.getShape() == shape)
                    .count();

                System.out.println(shape + ": " + count + "copies");
            }
        }
    }

    public List<Tile> getAllTiles() {
        return new ArrayList<>(tiles); 
    }
    
    public void returnTile(Tile tile) {
        if (tile != null) {
            tiles.add(tile);
        }
    }

}