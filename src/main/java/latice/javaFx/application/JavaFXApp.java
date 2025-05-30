package latice.javaFx.application;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import latice.Board.Board;
import latice.javaFx.gameView.BoardView;
import latice.javaFx.gameView.RackView;
import latice.model.Game;
import latice.model.Player;
import latice.model.Tile;
import latice.rules.Referee;

import java.util.List;
import java.util.Random;

public class JavaFXApp extends Application {
	private Tile selectedTile;
    private StackPane selectedTilePane;
    private boolean isFirstMove = true;
    private Referee referee = new Referee();

    @Override
    public void start(Stage primaryStage) {
        // Initialiser le plateau et le jeu
        Board board = new Board(9);
        Game game = new Game();
        game.initializeGame();

        // Sélection aléatoire du joueur courant
        Player currentPlayer = game.getCurrentPlayer();
        System.out.println("Starting player: " + currentPlayer.getName());

        // Création des vues
        BoardView boardView = new BoardView(board);
        RackView rackView = new RackView(currentPlayer.getRack().getTiles());

        // Action : poser une tuile sur une case du plateau
        boardView.setOnTilePlace((row, col, targetCell) -> {
            if (selectedTile != null && targetCell.getChildren().size() == 1) {
                boolean isValid = referee.isPlacementValid(board, row, col, selectedTile, isFirstMove);
                if (!isValid) {
                    System.out.println("⛔ Coup invalide selon l’arbitre.");
                    return;
                }

                String imageName = selectedTile.getShape().name().toLowerCase() + "_" +
                                   selectedTile.getColor().getCode() + ".png";
                var imageUrl = getClass().getResource("/" + imageName);
                if (imageUrl != null) {
                    ImageView tileView = new ImageView(imageUrl.toExternalForm());
                    tileView.setFitWidth(70);
                    tileView.setFitHeight(70);
                    tileView.setPreserveRatio(true);
                    targetCell.getChildren().add(tileView);
                }

                board.placeTile(row, col, selectedTile);

                if (selectedTilePane != null) {
                    selectedTilePane.setVisible(false);
                }

                selectedTile = null;
                selectedTilePane = null;
                isFirstMove = false;
            }
        });

        // Action : sélectionner une tuile du rack
        rackView.setOnTileSelect((tile, tilePane) -> {
            selectedTile = tile;
            selectedTilePane = tilePane;
        });

        // Affichage principal
        Label playerLabel = new Label("Current player: " + currentPlayer.getName());
        playerLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        VBox root = new VBox(10, boardView.getGrid(), playerLabel, rackView.getBox());
        root.setAlignment(Pos.CENTER);
        Image bgImage = new Image(getClass().getResource("/background.png").toExternalForm());
        
        BackgroundImage backgroundImage = new BackgroundImage(
        	    bgImage,
        	    BackgroundRepeat.NO_REPEAT,
        	    BackgroundRepeat.NO_REPEAT,
        	    BackgroundPosition.CENTER,
        	    new BackgroundSize(100, 100, true, true, true, true) 
        	);

        root.setBackground(new Background(backgroundImage));

        Scene scene = new Scene(root, 800, 800);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Latice - Version 5");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}