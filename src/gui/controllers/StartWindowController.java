package gui.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import gui.Interface;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

public class StartWindowController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button QuitButton;

    @FXML
    private Button StartButton;

    @FXML
    void initialize() {
        StartButton.setOnAction(event -> {
            try {
                Parent newPane = FXMLLoader.load(Interface.class.getResource("fxmls/selection.fxml"));
                Scene newScene = new Scene(newPane);
                Interface.getMainWindow().setScene(newScene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        QuitButton.setOnAction(event -> Platform.exit());
    }
}

