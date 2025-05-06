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
}