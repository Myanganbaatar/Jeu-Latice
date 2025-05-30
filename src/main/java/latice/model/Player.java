package latice.model;

import latice.model.Rack;
import latice.model.Tile;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Player {
    private final String name;
    private final List<Tile> pool;
    private final Rack rack;
    private int tilesPlaced = 0;

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
    protected Tile chooseTile(Scanner scanner) {
        int tileIndex = -1;
        List<Tile> tiles = rack.getTiles();

        while (true) {
            System.out.print("\nEntrez l’index de la tuile à jouer (1-" + tiles.size() + ") : ");
            try {
                tileIndex = scanner.nextInt() - 1;
                if (tileIndex < 0 || tileIndex >= tiles.size()) {
                    System.out.println("⛔ Index invalide. Veuillez réessayer !");
                } else {
                    break;
                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("⛔ Entrée invalide, veuillez entrer un nombre.");
                scanner.nextLine();
            }
        }
        return tiles.get(tileIndex);
    }
    
    
   
}
