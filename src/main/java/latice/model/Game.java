package latice.model;

import latice.model.Deck;
import latice.model.Tile;
import java.util.ArrayList;
import java.util.List;

public class Game {
    private final List<Player> players;
    private final Deck deck;

    public Game() {
        this.players = new ArrayList<>();
        this.deck = new Deck();
    }

}
