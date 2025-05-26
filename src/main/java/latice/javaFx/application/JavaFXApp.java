package latice.javaFx.application;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import latice.Board.Board;
import latice.javaFx.gameView.BoardView;
import latice.javaFx.gameView.RackView;
import latice.model.Game;
import latice.model.Tile;

import java.util.List;
import java.util.Random;

public class JavaFXApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Initialiser le plateau et le jeu
        Board board = new Board(9);
        Game game = new Game();
        game.initializeGame();

        // Sélection aléatoire du joueur courant
        latice.model.Player currentPlayer = game.getCurrentPlayer();
        System.out.println("Starting player: " + currentPlayer.getName());

        // Création des vues
        BoardView boardView = new BoardView(board);
        RackView rackView = new RackView(currentPlayer.getRack().getTiles());
        Label playerLabel = new Label("Current player: " + currentPlayer.getName());
        playerLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        VBox root = new VBox(10, boardView.getGrid(), playerLabel, rackView.getBox());
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-padding: 20; -fx-background-color: #f0f0f0;");

        Scene scene = new Scene(root, 800, 800);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Latice - Version 4");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}