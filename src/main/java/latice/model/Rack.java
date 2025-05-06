package latice.model;

import java.util.ArrayList;
import java.util.List;

public class Rack {
    private final List<Tile> tiles;
    private final int capacity;

    public Rack(int capacity) {
        this.tiles = new ArrayList<>();
        this.capacity = capacity;
    }

}
