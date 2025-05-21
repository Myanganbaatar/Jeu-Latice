package latice.javaFx.application;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import latice.Board.Board;
import latice.javaFx.gameView.BoardView;
import latice.javaFx.gameView.RackView;
import latice.model.Game;

public class JavaFXApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        Board board = new Board(9);
        Game game = new Game();
        game.initializeGame();

        var player = game.getPlayers().get(0);

        BoardView boardView = new BoardView(board);
        RackView rackView = new RackView(player.getRack().getTiles());

        VBox root = new VBox(10, boardView.getGrid(), rackView.getBox());
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-padding: 20; -fx-background-color: #f0f0f0;");

        Scene scene = new Scene(root, 800, 800);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Latice - Version 3");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}