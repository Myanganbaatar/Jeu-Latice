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

    public void addToPool(Tile tile) {
        if (tile != null) {
            pool.add(tile);
        }
    }

    public void initializeRack() {
        while (!pool.isEmpty() && !rack.isFull()) {
            rack.addTile(pool.remove(0));
        }
    }

    public void displayRack() {
        System.out.println("Rack de " + name + ":");
        rack.display();
    }

    public Rack getRack() {
        return rack;
    }

    public int getPoolSize() {
        return pool.size();
    }

    public boolean hasTilesInPool() {
        return !pool.isEmpty();
    }
}
