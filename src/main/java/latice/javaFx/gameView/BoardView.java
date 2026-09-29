package latice.javaFx.gameView;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import latice.Board.Board;
import latice.Board.CaseType;

public final class BoardView {

    private final GridPane grid = new GridPane();
    private static final int TILE_SIZE = 70;

    public interface TilePlaceHandler {
        void onPlace(int row, int col, StackPane cell);
    }

    private TilePlaceHandler handler;

    public BoardView(Board board) {
        grid.setAlignment(Pos.CENTER);

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                CaseType type = board.getCase(row, col).getType();
                String imageName = switch (type) {
                    case NORMAL -> "bg_sea.png";
                    case SUN    -> "bg_sun.png";
                    case MOON   -> "bg_moon.png";
                };

                var imageUrl = getClass().getResource("/" + imageName);
                if (imageUrl == null) {
                    System.out.println("❌ Missing image: " + imageName);
                    continue;
                }

                Image image = new Image(imageUrl.toExternalForm());
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(TILE_SIZE);
                imageView.setFitHeight(TILE_SIZE);
                imageView.setPreserveRatio(true);

                StackPane cell = new StackPane(imageView);
                cell.setPrefSize(TILE_SIZE, TILE_SIZE);
                cell.setStyle("-fx-border-color: black;");

                int finalRow = row;
                int finalCol = col;
                cell.setOnMouseClicked(e -> {
                    if (handler != null) {
                        handler.onPlace(finalRow, finalCol, cell);
                    }
                });

                grid.add(cell, col, row);
            }
        }
    }

    public void setOnTilePlace(TilePlaceHandler handler) {
        this.handler = handler;
    }

    public GridPane getGrid() {
        return grid;
    }
}