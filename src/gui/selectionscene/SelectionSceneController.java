package gui.selectionscene;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import gamengine.UnitsLoader;
import gamengine.battle.Battle;
import gamengine.march.Army;
import gamengine.march.UnitsStack;
import gui.Interface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SelectionSceneController {
    private UnitsLoader unitsLoader;

    @FXML private TextField nameField1;
    @FXML private TextField nameField2;

    @FXML private VBox ArmyVBox1;
    @FXML private VBox ArmyVBox2;

    @FXML private Button addButton1;
    @FXML private Button addButton2;

    @FXML private Button acceptButton;

    @FXML
    private void initialize() {
        try {
            unitsLoader = new UnitsLoader();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    @FXML
    private void clickOnAcceptButton() {
        String name1 = nameField1.getText().equals("") ? "Player1" : nameField1.getText();
        String name2 = nameField2.getText().equals("") ? "Player2" : nameField2.getText();
        Army army1 = formArmy(ArmyVBox1, 1);
        Army army2 = formArmy(ArmyVBox2, 2);

        if (army1 == null|| army2 == null) {
            displayError();
            return;
        }

        Interface.setBattle(new Battle(army1, army2, name1, name2));

    }

    @FXML
    private void clickOnAddButton1() {
        clickOnAddButtonHandler(ArmyVBox1, addButton1, '1');
    }

    @FXML
    private void clickOnAddButton2() {
        clickOnAddButtonHandler(ArmyVBox2, addButton2, '2');
    }

    private void clickOnAddButtonHandler(VBox mainVBox, Button addButton, char armyIndex) {
        HBox hBox = new HBox();
        hBox.setPrefSize(370, 65);
        hBox.getStylesheets().add(getClass().getResource("selectionScene.css").toExternalForm());
        hBox.getStyleClass().add("hbox-hero-" + armyIndex);
        hBox.setAlignment(Pos.CENTER);

        VBox vBoxLeft = new VBox();
        vBoxLeft.setPrefSize(200, 65);
        Text heroText = new Text("Type of unit");
        heroText.getStyleClass().add("text-hero-" + armyIndex);
        ComboBox comboBox = new ComboBox();
        comboBox.getStyleClass().add("combo-" + armyIndex);
        comboBox.setPrefWidth(150);
        comboBox.setPromptText("Select...");
        unitsLoader.getAllUnits().forEach(unit -> comboBox.getItems().add(unit.getType()));

        vBoxLeft.getChildren().addAll(heroText, comboBox);
        vBoxLeft.setAlignment(Pos.CENTER);

        VBox vBoxRight = new VBox();
        vBoxRight.setPrefSize(170, 65);
        Text numberText = new Text("Number of units");
        numberText.getStyleClass().add("text-hero-" + armyIndex);
        TextField textField = new TextField();
        textField.getStyleClass().add("number-" + armyIndex);
        textField.setPrefHeight(30);
        textField.setMaxWidth(100);
        vBoxRight.getChildren().addAll(numberText, textField);
        vBoxRight.setAlignment(Pos.CENTER);

        hBox.getChildren().addAll(vBoxLeft, vBoxRight);

        setDeleteNodeAction(mainVBox, hBox, addButton);
        mainVBox.getChildren().add(hBox);
        ObservableList<Node> swapCollection = FXCollections.observableArrayList(mainVBox.getChildren());
        Collections.swap(swapCollection, swapCollection.size() - 1, swapCollection.size() - 2);
        mainVBox.getChildren().clear();
        mainVBox.getChildren().addAll(swapCollection);
        if (mainVBox.getChildren().size() == Army.MAX_STACKS_NUM + 1) {
            mainVBox.getChildren().remove(Army.MAX_STACKS_NUM);
        }
    }

    private void setDeleteNodeAction(VBox mainVBox, HBox hBox, Button addButton) {
        hBox.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                ObservableList<Node> removeCollection = FXCollections.observableArrayList(mainVBox.getChildren());
                removeCollection.remove(hBox);
                mainVBox.getChildren().clear();
                mainVBox.getChildren().addAll(removeCollection);
                if (mainVBox.getChildren().size() == 5 && !removeCollection.contains(addButton)) {
                    mainVBox.getChildren().add(addButton);
                }
            }
        });
    }

    private Army formArmy(VBox mainVBox, int armyIndex) {
        if (mainVBox.getChildren().size() == 1) {
            return null;
        }

        ArrayList<UnitsStack> unitsStacks = new ArrayList<>();
        for (Node unitStackHBox: mainVBox.getChildren()) {
            if (!(unitStackHBox instanceof Button)) {
                VBox unitTypeVBox = (VBox) ((HBox) unitStackHBox).getChildren().get(0);
                VBox unitNumberVBox = (VBox) ((HBox) unitStackHBox).getChildren().get(1);

                String unitType = (String) ((ComboBox) (unitTypeVBox.getChildren().get(1))).getValue();
                if (unitType == null) {
                    return null;
                }

                int unitNumber;
                try {
                    unitNumber = Integer.parseInt(((TextField) unitNumberVBox.getChildren().get(1)).getText());
                    if (unitNumber <= 0 || unitNumber > UnitsStack.MAX_UNITS_NUM) {
                        throw new Exception();
                    }
                } catch (Exception e) {
                    return null;
                }

                unitsStacks.add(new UnitsStack(unitsLoader.createUnit(unitType), unitNumber, armyIndex));
            }
        }

        return new Army(unitsStacks);
    }

    private void displayError() {
        try {
            Parent newPane = FXMLLoader.load(getClass().getResource("../errorwindow/errorWindow.fxml"));
            Scene newScene = new Scene(newPane);
            Stage newStage = new Stage();
            newStage.initModality(Modality.WINDOW_MODAL);
            newStage.initOwner(Interface.getMainWindow());
            newStage.setTitle("Error");
            newStage.setScene(newScene);
            newStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
