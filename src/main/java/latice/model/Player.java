package latice.model;


import java.util.ArrayList;
import java.util.List;

public class Player {
    private final String name;
    private final List<Tile> pool;
    private final Rack rack;
    private int score = 0;
    private int tilesPlaced = 0;
    private int extraActions = 0;

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
    
    public int getTilesPlaced() {
        return tilesPlaced;
    }
    
    public void setTilesPlaced(int tilesPlaced) {
        this.tilesPlaced = tilesPlaced;
    }
    
    public void incrementTilesPlaced() {
        tilesPlaced++;
    }
    
    public void fillRackFromPool() {
        while (!rack.isFull() && !pool.isEmpty()) {
            rack.addTile(pool.remove(0));
        }
    }
    
    
    public int getScore() {
        return score;
    }
    
    public void addScore(int s) {
        score += s;
    }
    
    
    public boolean spendPoints(int points) {
        if (score >= points) {
            score -= points;
            return true;
        }
        return false;
    }

    
    
    public void exchangeRack(Deck deck) {
        if (deck != null) {
            this.rack.exchangeAllTiles(deck);
        }
    }
    
    public int getExtraActions() {
        return extraActions;
    }
    
    public void addExtraAction() {
        extraActions++;
    }

    public void useExtraAction() {
        if (extraActions > 0) {
            extraActions--;
        }
    }   
}
