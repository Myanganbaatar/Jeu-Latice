package latice.javaFx.gameView;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
            String imageName = tile.getShape().name().toLowerCase() + "_" +
                               tile.getColor().getCode() + ".png";

            var imageUrl = getClass().getResource("/" + imageName);
            if (imageUrl == null) {
                System.out.println("Missing image: " + imageName);
                continue;
            }

            Image image = new Image(imageUrl.toExternalForm());
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(TILE_SIZE);
            imageView.setFitHeight(TILE_SIZE);
            imageView.setPreserveRatio(true);

            StackPane tilePane = new StackPane(imageView);
            tilePane.setStyle("-fx-border-color: black; -fx-background-color: white;");
            tilePane.setPrefSize(TILE_SIZE, TILE_SIZE);

            allTilePanes.add(tilePane);

            tilePane.setOnMouseClicked(e -> {
                // Reset all
                for (StackPane pane : allTilePanes) {
                    pane.setStyle("-fx-border-color: black; -fx-background-color: white;");
                }
                tilePane.setStyle("-fx-border-color: red; -fx-border-width: 3; -fx-background-color: white;");
            });

            rackBox.getChildren().add(tilePane);
        }
    }

    public HBox getBox() {
        return rackBox;
    }
}