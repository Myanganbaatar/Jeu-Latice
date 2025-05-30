package latice.javaFx.util;

import javafx.scene.control.Alert;

public class AlertInvalid {

    public static void showInvalidMoveAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText("⛔ Invalid Move!");
        alert.setContentText("Please choose a valid tile placement.");
        alert.showAndWait();
    }
}
