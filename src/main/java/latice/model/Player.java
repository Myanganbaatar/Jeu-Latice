package latice.model;

import latice.model.Rack;
import latice.model.Tile;
import java.util.ArrayList;
import java.util.List;

public class Player {
    private final String name;
    private final List<Tile> pool;
    private final Rack rack;

    public Player(String name) {
        this.name = name;
        this.pool = new ArrayList<>();
        this.rack = new Rack(5); 
    }

    public String getName() {
        return name;
    }

 
}
