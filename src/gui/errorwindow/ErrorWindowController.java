package gui.errorwindow;

import gui.Interface;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ErrorWindowController {
    @FXML private Button errorCloseButton;
    @FXML private Label errorMessageLabel;

    @FXML
    private void initialize() {
        if (!Interface.getErrorMessage().equals("")) {
            errorMessageLabel.setText(Interface.getErrorMessage());
        }
    }

    @FXML
    private void clickOnErrorCloseButton() {
        ((Stage)errorCloseButton.getScene().getWindow()).close();
    }
}
