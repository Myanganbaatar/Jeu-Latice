package latice.javaFx.application;

import javafx.application.Application;
import javafx.stage.Stage;

public class JavaFXApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Latice - Version 3");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}