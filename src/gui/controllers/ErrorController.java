package gui.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ErrorController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button ErrorCloseButton;

    @FXML
    void initialize() {
        ErrorCloseButton.setOnAction(event -> {
            ((Stage)ErrorCloseButton.getScene().getWindow()).close();
        });
    }
}
