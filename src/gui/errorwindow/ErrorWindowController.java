package gui.errorwindow;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ErrorWindowController {
    @FXML private Button errorCloseButton;

    @FXML
    private void clickOnErrorCloseButton() {
        ((Stage)errorCloseButton.getScene().getWindow()).close();
    }
}
