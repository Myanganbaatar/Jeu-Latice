package latice.model;

import latice.Board.Board;
import latice.model.Rack;
import latice.model.Tile;
import latice.rules.Referee;

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
        this.pool = new java.util.ArrayList<>();
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
    
    
    protected int askCoordinate(Scanner scanner, String label, int max) {
        int coord = -1;

        while (true) {
            System.out.print("Entrez la " + label + " (1-" + max + ") : ");
            try {
                coord = scanner.nextInt() - 1;
                if (coord < 0 || coord >= max) {
                    System.out.println("⛔ " + label + " invalide. Veuillez réessayer !");
                } else {
                    break; 
                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("⛔ Entrée invalide, veuillez entrer un nombre.");
                scanner.nextLine();
            }
        }
        return coord;
    }
    
    
    public void playTurn(Board board, Referee referee, Scanner scanner, boolean isFirstMove) {
        System.out.println("\n🎲 Joueur actuel : " + name);
        displayRack();
        board.displayBoard();

        Tile tile = chooseTile(scanner);
        int row = askCoordinate(scanner, "ligne", 9);
        int col = askCoordinate(scanner, "colonne", 9);

        boolean validMove = referee.isPlacementValid(board, row, col, tile, isFirstMove);

        if (validMove) {
            board.placeTile(row, col, tile);
            rack.removeTile(tile);
            incrementTilesPlaced();
            System.out.println("✅ Coup valide : tuile posée.");

            fillRackFromPool(); // Optionally refill rack here
        } else {
            System.out.println("⛔ Coup invalide : emplacement interdit selon les règles.");
            if (isFirstMove) {
                System.out.println("👉 Rappel : le premier coup doit être placé en ligne 5, colonne 5 !");
            }
            // Retry the turn
            playTurn(board, referee, scanner, isFirstMove);
        }
    }

   
}
