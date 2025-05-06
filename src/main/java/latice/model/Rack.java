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

    public void addTile(Tile tile) {
        if (tile != null && tiles.size() < capacity) {
            tiles.add(tile);
        }
    }

    public Tile removeTile(int index) {
        if (index >= 0 && index < tiles.size()) {
            return tiles.remove(index);
        }
        return null;
    }

    public void display() {
        if (tiles.isEmpty()) {
            System.out.println("Le rack est vide");
            return;
        }

        for (int i = 0; i < tiles.size(); i++) {
            System.out.println((i+1) + ". " + tiles.get(i));
        }
    }

    public boolean isFull() {
        return tiles.size() >= capacity;
    }

    public boolean isEmpty() {
        return tiles.isEmpty();
    }

    public int size() {
        return tiles.size();
    }

    public List<Tile> getTiles() {
        return new ArrayList<>(tiles); 
    }
}
