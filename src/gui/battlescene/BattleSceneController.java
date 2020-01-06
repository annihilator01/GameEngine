package gui.battlescene;

import gamengine.battle.Battle;
import gamengine.battle.BattleArmy;
import gamengine.battle.BattleUnitStack;
import gui.Interface;
import javafx.animation.*;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class BattleSceneController {
    private final double DEFAULT_NAME_LABLE_HEIGHT = 60;
    private final String DEFAULT_FONT_FAMILY = "Berlin Sans FB Demi";
    private final double DEFAULT_TITLE_FONT_SIZE = 45;

    private final String ACTOR_HIGHLIGHT_CLASS = "actor-highlight";
    private final String TARGET_HIGHLISHT_CLASS = "target-highlight";
    private final String DEAD_HIGHLIGHT_CLASS = "dead-highlight";

    private Battle battle;
    private HBox selectedHBox;
    private int roundNumber;

    @FXML private VBox ArmyVBox1;
    @FXML private VBox ArmyVBox2;

    @FXML private TableView<BattleUnitStack> initiativeScale;
    @FXML private TableColumn<BattleUnitStack, Double> unitInitiative;
    @FXML private TableColumn<BattleUnitStack, String> unitType;
    @FXML private TableColumn<BattleUnitStack, String> unitActiveSkill;
    @FXML private TableColumn<BattleUnitStack, Integer> armyIndex;

    @FXML
    private void initialize() throws Exception {
        battle = Interface.getBattle();
        roundNumber = 1;

        String title1 = battle.getPlayerName1().equals(Interface.DEFAULT_PLAYER_NAME_1) ?
                        Interface.DEFAULT_PLAYER_NAME_1 + "'s Army" : battle.getPlayerName1() + "'s Army";

        String title2 = battle.getPlayerName2().equals(Interface.DEFAULT_PLAYER_NAME_2) ?
                Interface.DEFAULT_PLAYER_NAME_2 + "'s Army" : battle.getPlayerName2() + "'s Army";

        createTitle(ArmyVBox1, title1, '1');
        createTitle(ArmyVBox2, title2, '2');

        setUnitStacks(ArmyVBox1, battle.getBattleArmy1());
        setUnitStacks(ArmyVBox2, battle.getBattleArmy2());

        initInitiativeScaleSet();
        battle.getCurrentMoveUnitsStack().getBattleHBox().getStyleClass().add(ACTOR_HIGHLIGHT_CLASS);

        Interface.getMainWindow().setMaximized(true);
        showRoundInfo();
    }


    private void createTitle(VBox mainVBox, String title, char titleIndex) {
        Label nameLabel = new Label();
        nameLabel.setPrefSize(mainVBox.getPrefWidth(), DEFAULT_NAME_LABLE_HEIGHT);
        nameLabel.setAlignment(Pos.CENTER);
        nameLabel.setFont(new Font(DEFAULT_FONT_FAMILY, DEFAULT_TITLE_FONT_SIZE));
        nameLabel.setPadding(new Insets(0, 0, 20, 0));

        nameLabel.getStylesheets().add(getClass().getResource("battleScene.css").toExternalForm());
        nameLabel.getStyleClass().add("title-" + titleIndex);

        setTextToFitSize(nameLabel, title);

        mainVBox.getChildren().add(nameLabel);
    }

    private void setTextToFitSize(Label label, String text) {
        String labelFontFamily = label.getFont().getFamily();
        double labelFontSize = label.getFont().getSize();

        Text tmpText = new Text(text);
        tmpText.setFont(new Font(labelFontFamily, labelFontSize));

        double textWidth = tmpText.getLayoutBounds().getWidth();
        double labelWidth = label.getPrefWidth();

        if (textWidth <= labelWidth) {
            label.setText(text);
        } else {
            double newFontSize = labelFontSize * labelWidth / textWidth;
            label.setText(text);
            label.setFont(Font.font(labelFontFamily, newFontSize));
        }
    }

    private void setUnitStacks(VBox mainVBox, BattleArmy battleArmy) throws Exception {
        char armyIndex = (char) (battleArmy.getBattleArmyIndex() + '0');

        for (BattleUnitStack battleUnitStack: battleArmy.getBattleArmy()) {
            HBox newHBox = FXMLLoader.load(getClass().getResource("unitStackHBox.fxml"));

            newHBox.getStylesheets().add(getClass().getResource("battleScene.css").toExternalForm());
            newHBox.getStyleClass().add("stack-hbox-" + armyIndex);
            newHBox.setPickOnBounds(true);
            setClickOnUnitStackHBox(newHBox, battleUnitStack);
            battleUnitStack.setBattleHBox(newHBox);

            Tooltip newHBoxTooltip = new Tooltip(battleUnitStack.getUnitClass().toString());
            newHBoxTooltip.getStyleClass().add("battle-tooltip");
            Tooltip.install(newHBox, newHBoxTooltip);

            Pane pane = (Pane) newHBox.getChildren().get(0);
            ImageView imageView = (ImageView) pane.getChildren().get(0);
            Ellipse innerEllipse = (Ellipse) pane.getChildren().get(1);
            innerEllipse.getStyleClass().add("image-border-" + armyIndex);

            Ellipse tmpEllipse = new Ellipse(
                    innerEllipse.getCenterX(),
                    innerEllipse.getCenterY(),
                    innerEllipse.getRadiusX() - 2,
                    innerEllipse.getRadiusY() - 2
            );
            imageView.setImage(battleUnitStack.getUnitClass().getIcon());
            imageView.setClip(tmpEllipse);

            Label unitType = (Label) newHBox.getChildren().get(1);
            unitType.getStylesheets().add(getClass().getResource("battleScene.css").toExternalForm());
            unitType.getStyleClass().add("stack-info-" + armyIndex);
            unitType.setText("Type of units:\n" + battleUnitStack.getUnitClass().getType());

            Label unitNumber = (Label) newHBox.getChildren().get(2);
            unitNumber.getStylesheets().add(getClass().getResource("battleScene.css").toExternalForm());
            unitNumber.getStyleClass().add("stack-info-" + armyIndex);
            unitNumber.setText("Number of units:\n" + battleUnitStack.getUnitsNumber() + " / " +  battleUnitStack.getInitialUnitsNumber());

            mainVBox.getChildren().add(newHBox);
        }

    }

    private void setClickOnUnitStackHBox(HBox hBox, BattleUnitStack battleUnitStack) {
        hBox.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY &&
                hBox != battle.getCurrentMoveUnitsStack().getBattleHBox() &&
                battle.getInitiativeScale().contains(battleUnitStack) &&
                hBox != selectedHBox) {
                if (selectedHBox != null) {
                    selectedHBox.getStyleClass().remove(TARGET_HIGHLISHT_CLASS);
                }

                selectedHBox = hBox;
                selectedHBox.getStyleClass().add(TARGET_HIGHLISHT_CLASS);

                int unitPriority = battle.getInitiativeScale().indexOf(battleUnitStack);
                highlightRow(unitPriority, "red");
            } else if (event.getButton() == MouseButton.PRIMARY && hBox == selectedHBox) {
                selectedHBox.getStyleClass().remove(TARGET_HIGHLISHT_CLASS);
                selectedHBox = null;
                highlightRow(0, "");
            }
        });
    }

    private void initInitiativeScaleSet() {
        unitInitiative.setCellValueFactory(new PropertyValueFactory<>("Initiative"));
        unitType.setCellValueFactory(new PropertyValueFactory<>("Type"));
        unitActiveSkill.setCellValueFactory(new PropertyValueFactory<>("ActiveSkill"));
        armyIndex.setCellValueFactory(new PropertyValueFactory<>("ArmyIndex"));

        initiativeScale.addEventFilter(ScrollEvent.ANY, Event::consume);

        setInitiativeScale();
    }



    private void setInitiativeScale() {
        initiativeScale.setItems(FXCollections.observableArrayList(battle.getInitiativeScale()));
        resizeInitiativeScale();
        highlightRow(0, "");
    }

    private void resizeInitiativeScale() {
        initiativeScale.setPrefHeight(battle.getInitiativeScale().size() * 45 + 40);
    }

    private void highlightRow(int unitPriority, String color) {
        highlightColumnCell(unitInitiative, unitPriority,color);
        highlightColumnCell(unitType, unitPriority,color);
        highlightColumnCell(unitActiveSkill, unitPriority,color);
        highlightColumnCell(armyIndex, unitPriority,color);
    }

    private <T> void highlightColumnCell(TableColumn<BattleUnitStack, T> tableColumn, int unitPriority, String color) {
        tableColumn.setCellFactory(param -> new TableCell<BattleUnitStack, T>() {
            @Override
            public void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null) {
                    if (getIndex() == 0) {
                        setStyle("-fx-text-fill: lime;");
                    } else if (getIndex() == unitPriority) {
                        setStyle("-fx-text-fill: " + color + ";");
                    }

                    setText(item.toString());
                }
            }
        });
    }

    private void showRoundInfo() {
        showInfo("Round #" + roundNumber);
    }

    private void showInfo(String info) {
        Pane pane = null;

        try {
            pane = FXMLLoader.load(getClass().getResource("info.fxml"));
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }

        Label label = (Label) pane.getChildren().get(0);
        label.setText(info);
        label.setAlignment(Pos.CENTER);
        label.getStyleClass().add("info");

        Scene scene = new Scene(pane);
        scene.setFill(Color.TRANSPARENT);
        scene.getStylesheets().add(getClass().getResource("battleScene.css").toExternalForm());

        Stage stage = new Stage(StageStyle.TRANSPARENT);
        stage.initOwner(Interface.getMainWindow());
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();

        ScaleTransition increaseTransition = new ScaleTransition(Duration.seconds(1.5), label);
        increaseTransition.setFromX(0);
        increaseTransition.setFromY(0);
        increaseTransition.setToX(1);
        increaseTransition.setByY(1);

        increaseTransition.setOnFinished(event -> {
            ScaleTransition decreaseTransition = new ScaleTransition(Duration.seconds(1.5), label);
            decreaseTransition.setToX(0);
            decreaseTransition.setToY(0);
            decreaseTransition.play();
            decreaseTransition.setOnFinished(event1 -> stage.close());
        });

        increaseTransition.play();
    }

    @FXML
    private void clickOnAttackButton() {
    }

    @FXML
    private void clickOnDefenseButton() {
    }

    @FXML
    private void clickOnActiveSkillButton() {
    }

    @FXML
    private void clickOnWaitButton() {
        if (selectedHBox != null) {
            Interface.displayError("Wait doesn't require target!");
            return;
        }

        BattleUnitStack prevMoveBUS = battle.getCurrentMoveUnitsStack();
        battle.wait(battle.getCurrentMoveUnitsStack());
        setInitiativeScale();
    }

    @FXML
    private void clickOnGiveUpButton() {
        if (selectedHBox != null) {
            Interface.displayError("Give Up doesn't require target!");
            return;
        }

        BattleUnitStack currentMoveBUS = battle.getCurrentMoveUnitsStack();

        if (currentMoveBUS.getArmyIndex() == 1) {
            battle.getBattleArmy1().giveUp();
        } else {
            battle.getBattleArmy2().giveUp();
        }

        afterActionHandler(currentMoveBUS);
    }


    private void afterActionHandler(BattleUnitStack prevMoveBUS) {
        if (battle.isEnded()) {
            try {
                Parent parent = FXMLLoader.load(getClass().getResource("/gui/congratulationwindow/congratulationWindow.fxml"));

                Scene scene = new Scene(parent);
                scene.setFill(Color.TRANSPARENT);

                Stage stage = new Stage(StageStyle.TRANSPARENT);
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(Interface.getMainWindow());
                stage.setScene(scene);
                stage.showAndWait();
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }

        prevMoveBUS.getBattleHBox().getStyleClass().remove(ACTOR_HIGHLIGHT_CLASS);

        if (selectedHBox != null) {
            selectedHBox.getStyleClass().remove(TARGET_HIGHLISHT_CLASS);
        }
    }
}
