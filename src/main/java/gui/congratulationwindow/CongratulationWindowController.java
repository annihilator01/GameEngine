package gui.congratulationwindow;

import gui.Interface;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class CongratulationWindowController {
    @FXML private Button closeButton;
    @FXML private Label nameField;

    @FXML
    private void initialize() throws Exception {
        nameField.setText(Interface.getBattle().getWinner().getPlayerName() + " Wins");
    }

    @FXML
    private void clickOnCloseButton() {
        Interface.getMainWindow().close();
    }
}
