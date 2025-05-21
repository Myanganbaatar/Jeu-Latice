package latice.javaFx.gameView;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import latice.model.Tile;
import java.util.ArrayList;
import java.util.List;

public class RackView {

    private final HBox rackBox = new HBox(10);
    private final int TILE_SIZE = 70;
    private final List<StackPane> allTilePanes = new ArrayList<>();

    public RackView(List<Tile> tiles) {
        rackBox.setAlignment(Pos.CENTER);

        for (Tile tile : tiles) {
            StackPane tilePane = new StackPane();
            tilePane.setStyle("-fx-border-color: black; -fx-background-color: white;");
            tilePane.setPrefSize(TILE_SIZE, TILE_SIZE);

            allTilePanes.add(tilePane);
            rackBox.getChildren().add(tilePane);
        }
    }

    public HBox getBox() {
        return rackBox;
    }
}