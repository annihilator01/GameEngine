package gui.selectionscene;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import gamengine.UnitLoader;
import gamengine.battle.Battle;
import gamengine.march.Army;
import gamengine.march.UnitStack;
import gamengine.unit.Unit;
import gui.Interface;
import gui.battlescene.BattleSceneController;
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

public class SelectionSceneController {
    private UnitLoader unitLoader;

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
            unitLoader = new UnitLoader();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    @FXML
    private void clickOnAcceptButton() {
        String name1 = nameField1.getText().equals("") ? Interface.DEFAULT_PLAYER_NAME_1 : nameField1.getText();
        String name2 = nameField2.getText().equals("") ? Interface.DEFAULT_PLAYER_NAME_2 : nameField2.getText();

        Army army1 = formArmy(ArmyVBox1, name1, 1);
        if (army1 == null) {
            return;
        }

        Army army2 = formArmy(ArmyVBox2, name2, 2);
        if (army2 == null) {
            return;
        }

        Interface.setBattle(new Battle(army1, army2, name1, name2));

        try {
            Parent newPane = FXMLLoader.load(getClass().getResource("/gui/battlescene/battleScene.fxml"));
            Scene newScene = new Scene(newPane);
            Interface.getMainWindow().setScene(newScene);
            Interface.getMainWindow().hide();
            Interface.getMainWindow().setMaximized(true);
            Interface.getMainWindow().show();
            BattleSceneController.showNewRoundInfo();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        Text heroText = new Text("Type of units");
        heroText.getStyleClass().add("text-hero-" + armyIndex);
        ComboBox comboBox = new ComboBox();
        comboBox.getStyleClass().add("combo-" + armyIndex);
        comboBox.setPrefWidth(150);
        comboBox.setPromptText("Select...");
        comboBox.getItems().addAll(unitLoader.getAllUnits());
        comboBox.setCellFactory(unitCell -> createUnitCell(armyIndex));
        comboBox.setButtonCell(createUnitCell(armyIndex));

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
        allowOnlyNumbers(textField);

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

    private Army formArmy(VBox mainVBox, String playerName,  int armyIndex) {
        if (mainVBox.getChildren().size() == 1) {
            Interface.displayError("Battle can't begin without unit stacks on both sides!");
            return null;
        }

        ArrayList<UnitStack> unitsStacks = new ArrayList<>();
        for (Node unitStackHBox: mainVBox.getChildren()) {
            if (!(unitStackHBox instanceof Button)) {
                VBox unitTypeVBox = (VBox) ((HBox) unitStackHBox).getChildren().get(0);
                VBox unitNumberVBox = (VBox) ((HBox) unitStackHBox).getChildren().get(1);

                Unit unit = (Unit) ((ComboBox) (unitTypeVBox.getChildren().get(1))).getValue();
                if (unit == null) {
                    Interface.displayError("Choose type of units for unit stack!");
                    return null;
                }
                String unitType = unit.getType();

                int unitNumber;
                try {
                    unitNumber = Integer.parseInt(((TextField) unitNumberVBox.getChildren().get(1)).getText());
                    if (unitNumber <= 0 || unitNumber > UnitStack.MAX_UNITS_NUM) {
                        Interface.displayError("Number of units in unit stack can't be more than " +
                                                UnitStack.MAX_UNITS_NUM + "!");
                        return null;
                    }
                } catch (Exception e) {
                    Interface.displayError("Number of units for unit stack is not given!");
                    return null;
                }

                unitsStacks.add(new UnitStack(unitLoader.createUnit(unitType), unitNumber, armyIndex));
            }
        }

        return new Army(unitsStacks, playerName, armyIndex);
    }

    private ListCell<Unit> createUnitCell(char armyIndex) {
        return new ListCell<Unit>() {
            @Override
            public void updateItem(Unit unit, boolean empty) {
                super.updateItem(unit, empty);
                if (unit != null) {
                    Tooltip toolTip = new Tooltip(unit.toString());
                    toolTip.getStyleClass().add("selection-tooltip-" + armyIndex);

                    setText(unit.getType());
                    setTooltip(toolTip);
                }
            }
        };
    }

    private void allowOnlyNumbers(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                textField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }
}
