package latice.javaFx.application;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import latice.Board.Board;
import latice.javaFx.gameView.BoardView;
import latice.javaFx.gameView.RackView;
import latice.javaFx.util.AlertInvalid;
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

    private Player currentPlayer;
    private int currentPlayerIndex = 0;
    private List<Player> players;
    private Label playerLabel;
    private Label scoreLabelP1;
    private Label scoreLabelP2;
    private Label tilesPlacedP1;
    private Label tilesPlacedP2;
    private Label scoreLabel;
    private RackView rackView;
    private BoardView boardView;
    private Board board;
    private Game game;
    private int cycles = 0;
    private Label cycleLabel;
    private HBox rackAndButtonBox;
    private int turns = 0;
    private HBox boardAndScoresBox;

    @Override
    public void start(Stage primaryStage) {
        initializeGameState();

        VBox root = new VBox(5);
        root.setAlignment(Pos.CENTER);

        boardAndScoresBox = new HBox(20,
                new VBox(scoreLabelP1, tilesPlacedP1),
                boardView.getGrid(),
                new VBox(scoreLabelP2, tilesPlacedP2)
        );
        boardAndScoresBox.setAlignment(Pos.CENTER);

        root.getChildren().addAll(boardAndScoresBox, playerLabel, cycleLabel, rackAndButtonBox);

        Image bgImage = new Image(getClass().getResource("/background.png").toExternalForm());
        BackgroundImage backgroundImage = new BackgroundImage(
                bgImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(800, 720, true, true, true, true)
        );
        root.setBackground(new Background(backgroundImage));

        Scene scene = new Scene(root, 1000, 800);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Latice - Version 7");
        primaryStage.show();
    }
    
    private void updateScoreLabels() {
        scoreLabelP1.setText(players.get(0).getName() + ": " + players.get(0).getScore());
        scoreLabelP2.setText(players.get(1).getName() + ": " + players.get(1).getScore());
        tilesPlacedP1.setText(players.get(0).getName() + " tiles: " + players.get(0).getTilesPlaced());
        tilesPlacedP2.setText(players.get(1).getName() + " tiles: " + players.get(1).getTilesPlaced());
    }


    private void configureBoardView() {
        boardView.setOnTilePlace((row, col, targetCell) -> {
            if (selectedTile != null && targetCell.getChildren().size() == 1) {
                boolean isValid = referee.isPlacementValid(board, row, col, selectedTile, isFirstMove);
                if (!isValid) {
                    AlertInvalid.showInvalidMoveAlert();
                    return;
                }

                String imageName = selectedTile.getShape().name().toLowerCase() + "_" +
                        selectedTile.getColor().getCode() + ".png";
                var imageUrl = getClass().getResource("/" + imageName);
                if (imageUrl != null) {
                    ImageView tileView = new ImageView(imageUrl.toExternalForm());
                    tileView.setFitWidth(60);
                    tileView.setFitHeight(60);
                    tileView.setPreserveRatio(true);
                    targetCell.getChildren().add(tileView);
                }

                board.placeTile(row, col, selectedTile);
                currentPlayer.incrementTilesPlaced();
                currentPlayer.getRack().removeTile(selectedTile);

                Tile newTile = game.drawTile();
                if (newTile != null) {
                    currentPlayer.getRack().addTile(newTile);
                }

                int points = referee.calculateScore(board, row, col, selectedTile);
                currentPlayer.addScore(points);
                updateScoreLabels();

                if (referee.isGameOver(board, players)) {
                    showEndOfGame();
                }

                if (selectedTilePane != null) {
                    selectedTilePane.setVisible(false);
                }
                selectedTile = null;
                selectedTilePane = null;
                isFirstMove = false;

                switchPlayer();
            }
        });
    }
    
    private void switchPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        currentPlayer = players.get(currentPlayerIndex);

        turns++;
        if (turns % 2 == 0) {
            cycles++;
            cycleLabel.setText("Cycle: " + cycles);
            if (cycles >= 10) {
                showEndOfGame();
            }
        }

        rackView = new RackView(currentPlayer.getRack().getTiles());
        rackView.setOnTileSelect((tile, tilePane) -> {
            selectedTile = tile;
            selectedTilePane = tilePane;
        });

        playerLabel.setText("Current player: " + currentPlayer.getName());
        updateScoreLabels();

        rackAndButtonBox.getChildren().set(0, rackView.getBox());
    }
    
    private void showEndOfGame() {
        Player winner = referee.getWinner(players);

        String finalMessage;
        if (winner == null) {
            finalMessage = "DRAW\nGAME OVER";
        } else {
            finalMessage = "WINNER: " + winner.getName() + "\nGAME OVER";
        }

        Label endLabel = new Label(finalMessage);
        endLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: black;");
        endLabel.setAlignment(Pos.CENTER);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.getDialogPane().setContent(endLabel);

        ButtonType replayButton = new ButtonType("Replay");
        ButtonType quitButton = new ButtonType("Quit");
        alert.getButtonTypes().setAll(replayButton, quitButton);

        alert.showAndWait().ifPresent(response -> {
            if (response == replayButton) {
                restartGame();
            } else if (response == quitButton) {
                Stage stage = (Stage) playerLabel.getScene().getWindow();
                stage.close();
            }
        });
    }
    
    private void restartGame() {
        // Réinitialiser l'état
        board = new Board(9);
        game = new Game();
        game.initializeGame();
        players = game.getPlayers();
        currentPlayerIndex = new Random().nextInt(players.size());
        currentPlayer = players.get(currentPlayerIndex);
        selectedTile = null;
        selectedTilePane = null;
        isFirstMove = true;
        turns = 0;
        cycles = 0;

        // Réinitialiser la vue
        boardView = new BoardView(board);
        configureBoardView(); // ⚠️ Ajoute cette ligne pour remettre l'événement sur le board

        rackView = new RackView(currentPlayer.getRack().getTiles());
        rackView.setOnTileSelect((tile, tilePane) -> {
            selectedTile = tile;
            selectedTilePane = tilePane;
        });

        // Mettre à jour les labels et la vue
        playerLabel.setText("Current player: " + currentPlayer.getName());
        updateScoreLabels();
        cycleLabel.setText("Cycle: 0");

        // Réinitialiser la vue du rack et du plateau
        rackAndButtonBox.getChildren().set(0, rackView.getBox());
        boardAndScoresBox.getChildren().set(1, boardView.getGrid());
    }
    
    
    private void initializeGameState() {
        board = new Board(9);
        game = new Game();
        game.initializeGame();
        players = game.getPlayers();
        currentPlayerIndex = new Random().nextInt(players.size());
        currentPlayer = players.get(currentPlayerIndex);
        selectedTile = null;
        selectedTilePane = null;
        isFirstMove = true;
        turns = 0;
        cycles = 0;

        boardView = new BoardView(board);
        configureBoardView();

        rackView = new RackView(currentPlayer.getRack().getTiles());
        rackView.setOnTileSelect((tile, tilePane) -> {
            selectedTile = tile;
            selectedTilePane = tilePane;
        });

        scoreLabelP1 = new Label(players.get(0).getName() + " score: 0");
        scoreLabelP1.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        scoreLabelP2 = new Label(players.get(1).getName() + " score: 0");
        scoreLabelP2.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        tilesPlacedP1 = new Label(players.get(0).getName() + " tiles: 0");
        tilesPlacedP2 = new Label(players.get(1).getName() + " tiles: 0");
        tilesPlacedP1.setStyle("-fx-font-size: 14px;");
        tilesPlacedP2.setStyle("-fx-font-size: 14px;");

        playerLabel = new Label("Current player: " + currentPlayer.getName());
        playerLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        cycleLabel = new Label("Cycle: 0");
        cycleLabel.setStyle("-fx-font-size: 14px;");

        scoreLabel = new Label();
        scoreLabel.setStyle("-fx-font-size: 14px;");
        updateScoreLabels();

        Button passTurnButton = new Button("Pass Turn");
        passTurnButton.setOnAction(e -> switchPlayer());

        rackAndButtonBox = new HBox(20, rackView.getBox(), passTurnButton);
        rackAndButtonBox.setAlignment(Pos.CENTER);
    }
    
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    

    public static void main(String[] args) {
        launch(args);
    }
}