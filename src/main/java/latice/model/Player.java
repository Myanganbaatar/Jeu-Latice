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
    private int score = 0;
    private int tilesPlaced = 0;
    private int extraActions = 0;

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
    
    
    public Tile chooseTile(Scanner scanner) {
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
    
    
    public int askCoordinate(Scanner scanner, String label, int max) {
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
    
    
    public boolean playTurn(Board board, Referee referee, Scanner scanner, boolean isFirstMove, Game game) {
        System.out.println("Current cycle info can be handled in Game if needed.");

        System.out.println("\n" + name + "'s rack:");
        rack.display();

        int action = 0;
        while (action != 1 && action != 2) {
            System.out.print("\n💡 Action (1=Play a tile | 2=Pass your turn): ");
            action = scanner.nextInt();
            if (action != 1 && action != 2) {
                System.out.println("⛔ Invalid action. Please try again!");
            }
        }

        if (action == 2) {
            System.out.println("🔄 " + name + " chose to pass their turn.");
            return false; // Turn skipped
        }

        // Play tile
        Tile tile = chooseTile(scanner);
        int row = askCoordinate(scanner, "row", 9);
        int col = askCoordinate(scanner, "column", 9);

        boolean validMove = referee.isPlacementValid(board, row, col, tile, isFirstMove);

        if (validMove) {
            board.placeTile(row, col, tile);
            rack.removeTile(tile);
            incrementTilesPlaced();

            int points = referee.calculateScore(board, row, col, tile);
            addScore(points);
            System.out.println("✅ Valid move: tile placed. Points earned: " + points);

            Tile newTile = game.drawTile();
            if (newTile != null) {
                rack.addTile(newTile);
                System.out.println("🟡 New tile added to the rack.");
            } else {
                System.out.println("⚠️ Deck is empty, no new tile.");
            }

            return true;
        } else {
            System.out.println("⛔ Invalid move: illegal placement according to the rules.");
            if (isFirstMove) {
                System.out.println("👉 Reminder: the first move must be at row 5, column 5!");
            }
            // Retry this turn
            return playTurn(board, referee, scanner, isFirstMove, game);
        }
    }
    
    public int getScore() {
        return score;
    }
    
    public void addScore(int s) {
        score += s;
    }
    
    
    public void displayStatus() {
        System.out.println("\n🎲 Current player: " + name);
        System.out.println("Current score: " + score);
        System.out.println("Tiles placed: " + tilesPlaced);
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
