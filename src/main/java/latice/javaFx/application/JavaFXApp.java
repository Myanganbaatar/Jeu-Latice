package latice.javaFx.application;

import javafx.application.Application;
import javafx.stage.Stage;
import latice.Board.Board;
import latice.model.Game;

public class JavaFXApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        Board board = new Board(9);
        Game game = new Game();
        game.initializeGame();

        var player = game.getPlayers().get(0);

        primaryStage.setTitle("Latice - Version 3");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}