package gui.startscene;

import gamengine.UnitLoader;
import gamengine.unit.Unit;
import gui.Interface;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

import java.io.File;

public class StartSceneController {
    @FXML private Button startButton;
    @FXML private Button quitButton;

    @FXML
    private void clickOnStartButton() throws Exception {
        Parent newPane = FXMLLoader.load(getClass().getResource("/gui/selectionscene/selectionScene.fxml"));
        Scene newScene = new Scene(newPane);

        Interface.getMainWindow().setScene(newScene);
    }

    @FXML
    private void clickOnQuitButton() {
        Platform.exit();
    }
}

