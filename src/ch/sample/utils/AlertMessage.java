package ch.sample.utils;
import javafx.scene.control.Alert;

public class AlertMessage {
    public static void showErrorMessage(String title, String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
}
