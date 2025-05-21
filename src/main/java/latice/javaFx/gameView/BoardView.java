package latice.javaFx.gameView;

import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import latice.Board.Board;

public class BoardView {

    private final GridPane grid = new GridPane();
    private final int TILE_SIZE = 70;

    public BoardView(Board board) {
        grid.setAlignment(Pos.CENTER);

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                StackPane cell = new StackPane();
                cell.setPrefSize(TILE_SIZE, TILE_SIZE);
                cell.setStyle("-fx-border-color: black;");
                grid.add(cell, col, row);
            }
        }
    }

    public GridPane getGrid() {
        return grid;
    }
}