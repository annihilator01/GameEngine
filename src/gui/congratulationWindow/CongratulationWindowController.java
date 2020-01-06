package gui.congratulationwindow;

import gui.Interface;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.TextAlignment;

public class CongratulationWindowController {
    @FXML private Button closeButton;
    @FXML private Label nameField;
    @FXML private HBox nameHBox;
    @FXML private AnchorPane innerPane;

    @FXML
    private void initialize() throws Exception {
        nameField.setText(Interface.getBattle().getWinner().getPlayerName() + " Wins");

        double startPos = innerPane.getLayoutX();
        double paneWidth = innerPane.getWidth();
        double hBoxWidth = nameHBox.getWidth();
    }

    @FXML
    private void clickOnCloseButton() {
        Interface.getMainWindow().close();
    }
}
