package gui;

import gamengine.battle.Battle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Interface extends Application {
    public static final String DEFAULT_PLAYER_NAME_1 = "First Player";
    public static final String DEFAULT_PLAYER_NAME_2 = "Second Player";

    private static String errorMessage = "";

    private static Stage mainWindow;

    private static Battle battle;

    @Override
    public void start(Stage primaryStage) throws Exception {
        mainWindow = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("startscene/startScene.fxml"));

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Heroes");
        primaryStage.getIcons().add(new Image("/gui/assets/icon.png"));

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static Stage getMainWindow() {
        return mainWindow;
    }

    public static void setBattle(Battle battle) {
        Interface.battle = battle;
    }

    public static Battle getBattle() {
        return battle;
    }

    public static String getErrorMessage() {
        return errorMessage;
    }

    public static void displayError(String errorMessage) {
        Interface.errorMessage = errorMessage;

        try {
            Parent newPane = FXMLLoader.load(Interface.class.getResource("/gui/errorwindow/errorWindow.fxml"));

            Scene newScene = new Scene(newPane);
            newScene.setFill(Color.TRANSPARENT);

            Stage newStage = new Stage(StageStyle.TRANSPARENT);
            newStage.initModality(Modality.WINDOW_MODAL);
            newStage.initOwner(mainWindow);
            newStage.setTitle("Error");
            newStage.setScene(newScene);
            newStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
