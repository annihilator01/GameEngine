package gui.battlescene;

import gamengine.battle.Actions;
import gamengine.battle.Battle;
import gamengine.battle.BattleArmy;
import gamengine.battle.BattleUnitStack;
import gamengine.skills.ActiveSkills;
import gamengine.skills.PassiveSkills;
import gui.Interface;
import javafx.animation.*;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Locale;

public class BattleSceneController {
    private final double DEFAULT_NAME_LABLE_HEIGHT = 60;
    private final String DEFAULT_FONT_FAMILY = "Berlin Sans FB Demi";
    private final double DEFAULT_TITLE_FONT_SIZE = 45;

    private final String ACTOR_HIGHLIGHT_CLASS = "actor-highlight";
    private final String TARGET_HIGHLISHT_CLASS = "target-highlight";
    private final String DEAD_HIGHLIGHT_CLASS = "dead-highlight";
    private final String RESURRECTION_HIGHLIGHT_CLASS = "resurrection-highlight";

    private Battle battle;
    private HBox selectedHBox;

    private static int roundNumber;

    @FXML private VBox ArmyVBox1;
    @FXML private VBox ArmyVBox2;

    @FXML private TableView<BattleUnitStack> initiativeScale;
    @FXML private TableColumn<BattleUnitStack, Double> unitInitiative;
    @FXML private TableColumn<BattleUnitStack, String> unitType;
    @FXML private TableColumn<BattleUnitStack, String> unitActiveSkill;
    @FXML private TableColumn<BattleUnitStack, Integer> armyIndex;

    @FXML private Button attackButton;
    @FXML private Button defenseButton;
    @FXML private Button activeSkillButton;
    @FXML private Button waitButton;
    @FXML private Button giveUpButton;

    @FXML
    private void initialize() throws Exception {

        battle = Interface.getBattle();
        roundNumber = 0;

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
        setButtonsTooltip();
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
        double labelWidth = 0;
        try {
            labelWidth = ((HBox) FXMLLoader.load(getClass().getResource("unitStackHBox.fxml"))).getPrefWidth();
        } catch (Exception ignored) {}

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

            Tooltip newHBoxTooltip = new Tooltip(battleUnitStack.toString());
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

            Label unitTypeField = (Label) newHBox.getChildren().get(1);
            unitTypeField.getStylesheets().add(getClass().getResource("battleScene.css").toExternalForm());
            unitTypeField.getStyleClass().add("stack-info-" + armyIndex);
            unitTypeField.setText("Type of units:\n" + battleUnitStack.getUnitClass().getType());

            Label unitsNumberField = (Label) newHBox.getChildren().get(2);
            unitsNumberField.getStylesheets().add(getClass().getResource("battleScene.css").toExternalForm());
            unitsNumberField.getStyleClass().add("stack-info-" + armyIndex);
            unitsNumberField.setText("Number of units:\n" + battleUnitStack.getUnitsNumber() + " / " +  battleUnitStack.getInitialUnitsNumber());

            mainVBox.getChildren().add(newHBox);
        }

    }

    private void setClickOnUnitStackHBox(HBox hBox, BattleUnitStack battleUnitStack) {
        hBox.setOnMouseClicked(event -> {
            if (event.getButton() != MouseButton.PRIMARY) {
                return;
            }

            BattleUnitStack actor = battle.getCurrentMoveUnitsStack();
            if (hBox != actor.getBattleHBox() && hBox != selectedHBox) {
                ArrayList<String> styleClasses = new ArrayList<>();
                styleClasses.add(TARGET_HIGHLISHT_CLASS);

                if (hBox.getStyleClass().contains(DEAD_HIGHLIGHT_CLASS)) {
                    if (actor.getUnitClass().getActiveSkill() == ActiveSkills.RESURRECTION) {
                        styleClasses.add(RESURRECTION_HIGHLIGHT_CLASS);
                    } else {
                        return;
                    }
                }

                if (selectedHBox != null) {
                    selectedHBox.getStyleClass().remove(TARGET_HIGHLISHT_CLASS);
                    selectedHBox.getStyleClass().remove(RESURRECTION_HIGHLIGHT_CLASS);
                }


                selectedHBox = hBox;
                selectedHBox.getStyleClass().addAll(styleClasses);

                int unitPriority = battle.getInitiativeScale().indexOf(battleUnitStack);
                highlightRow(unitPriority, "red");
            } else if (hBox == selectedHBox) {
                selectedHBox.getStyleClass().remove(TARGET_HIGHLISHT_CLASS);
                selectedHBox.getStyleClass().remove(RESURRECTION_HIGHLIGHT_CLASS);
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
        initiativeScale.setPrefHeight(battle.getInitiativeScale().size() * 40 + 52);
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

    public static void showNewRoundInfo() {
        roundNumber++;
        showInfo("Round #" + roundNumber);
    }

    private static void showInfo(String info) {
        Pane pane = null;

        try {
            pane = FXMLLoader.load(BattleSceneController.class.getResource("info.fxml"));
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }

        Label label = (Label) pane.getChildren().get(0);
        label.setText(info);
        label.setAlignment(Pos.CENTER);
        label.setScaleX(0);
        label.setScaleY(0);
        label.getStyleClass().add("info");

        Scene scene = new Scene(pane);
        scene.setFill(Color.TRANSPARENT);
        scene.getStylesheets().add(BattleSceneController.class.getResource("battleScene.css").toExternalForm());

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
        BattleUnitStack actor = battle.getCurrentMoveUnitsStack();
        BattleUnitStack target = findBattleUnitStack(selectedHBox);

        if (selectedHBox == null && !actor.getUnitClass().getPassiveSkills().contains(PassiveSkills.ATTACKALL)) {
            Interface.displayError("Attack requires target!");
            return;
        } else if (selectedHBox != null) {
            if (actor.getArmyIndex() == target.getArmyIndex()) {
                Interface.displayError("Attack can't be used on allied unit stack!");
                return;
            } else if (actor.getUnitClass().getPassiveSkills().contains(PassiveSkills.ATTACKALL)) {
                Interface.displayError("Attack doesn't require target when passive skill is Attack ALL!");
                return;
            } else if (selectedHBox.getStyleClass().contains(RESURRECTION_HIGHLIGHT_CLASS)) {
                Interface.displayError("Attack can't be used on dead unit stack!");
                return;
            }
        }

        BattleArmy targetBattleArmy = (actor.getArmyIndex() == 2) ?
                                       battle.getBattleArmy1() : battle.getBattleArmy2();

        int beforeActorHP = actor.getHP();
        ArrayList<Integer> beforeTargetsHP = new ArrayList<>(){{
            for (BattleUnitStack target: targetBattleArmy.getBattleArmy()) {
                add(target.getHP());
            }
        }};

        battle.attack(actor, target);

        afterActionHandler(actor, target, Actions.ATTACK);

        // show hp change
        for (int i = 0; i < targetBattleArmy.getBattleArmy().size(); i++) {
            healthPointsHandler(targetBattleArmy.getBattleArmy().get(i), beforeTargetsHP.get(i));
        }
        healthPointsHandler(actor, beforeActorHP);
    }

    private void healthPointsHandler(BattleUnitStack battleUnitStack, int beforeHP) {
        int changeHP = beforeHP - battleUnitStack.getHP();

        if (changeHP == 0) {
            return;
        }

        showChange(battleUnitStack.getBattleHBox(), -changeHP, "/gui/assets/heart.png");
    }

    @FXML
    private void clickOnDefenseButton() {
        if (selectedHBox != null) {
            Interface.displayError("Defense doesn't require target!");
            return;
        }

        BattleUnitStack actor = battle.getCurrentMoveUnitsStack();

        if (isWaitAndDefenseForbidden(actor)) {
            return;
        }

        battle.defend(actor);
        afterActionHandler(actor, Actions.DEFENSE);
        showChange(actor.getBattleHBox(), actor.getDefenseChange(), "/gui/assets/defense.png");
    }

    @FXML
    private void clickOnActiveSkillButton() {
        BattleUnitStack actor = battle.getCurrentMoveUnitsStack();
        BattleUnitStack target = findBattleUnitStack(selectedHBox);
        ActiveSkills actorActiveSkill = actor.getUnitClass().getActiveSkill();

        if (actorActiveSkill == null) {
            Interface.displayError("Current unit stack doesn't have any active skills!");
            return;
        } else if (selectedHBox == null) {
            Interface.displayError("Active Skill requires target!");
            return;
        } else if (actor.getNumberActiveSkillUsed() == Battle.MAX_USE_OF_ACTIVE_SKILL) {
            Interface.displayError("Active Skill can't be used more than " + Battle.MAX_USE_OF_ACTIVE_SKILL + " times during the battle!");
            return;
        } else if (!actorActiveSkill.isPositive() && actorActiveSkill.getChangeParameter().getValue(target).equals(0)) {
            Interface.displayError("Active Skill can't decrease characteristics value below zero!");
            return;
        }

        // resurrection case handling
        if (actorActiveSkill == ActiveSkills.RESURRECTION) {
            if (!target.getUnitClass().getPassiveSkills().contains(PassiveSkills.UNDEAD)) {
                Interface.displayError("Resurrection active skill can't be used on not Undead units!");
                return;
            } else if (target.getHP() == target.getInitialUnitsNumber() * target.getUnitClass().getHP()) {
                Interface.displayError("Active Skill can't be used on full HP unit stacks!");
                return;
            }
        }

        // before battle characteristics
        Number beforeTargetValue = actorActiveSkill.getChangeParameter().getValue(target);

        battle.useActiveSkill(actor, target);
        afterActionHandler(actor, target, Actions.ACTIVESKILL);
        activeSkillHandler(target, actorActiveSkill, beforeTargetValue);
    }

    private void activeSkillHandler(BattleUnitStack target, ActiveSkills activeSkill, Number before) {
        Number after = activeSkill.getChangeParameter().getValue(target);
        Number change = after.doubleValue() - before.doubleValue();

        if (before instanceof Integer) {
            showChange(target.getBattleHBox(), change.intValue(), activeSkill.getImageURL());
        } else {
            showChange(target.getBattleHBox(), change.doubleValue(), activeSkill.getImageURL());
        }
    }

    @FXML
    private void clickOnWaitButton() {
        if (selectedHBox != null) {
            Interface.displayError("Waiting doesn't require target!");
            return;
        }

        BattleUnitStack actor = battle.getCurrentMoveUnitsStack();

        if (isWaitAndDefenseForbidden(actor)) {
            return;
        }

        battle.wait(actor);
        afterActionHandler(actor, Actions.WAIT);
        showChange(actor.getBattleHBox(), 0, "/gui/assets/wait.png");
    }

    @FXML
    private void clickOnGiveUpButton() {
        if (selectedHBox != null) {
            Interface.displayError("Giving Up doesn't require target!");
            return;
        }

        BattleUnitStack actor = battle.getCurrentMoveUnitsStack();

        if (actor.getArmyIndex() == 1) {
            battle.getBattleArmy1().giveUp();
        } else {
            battle.getBattleArmy2().giveUp();
        }

        afterActionHandler(actor, Actions.GIVEUP);
    }

    private BattleUnitStack findBattleUnitStack(HBox hBox) {
        if (hBox == null) {
            return null;
        }

        for (BattleUnitStack battleUnitStack: battle.getAllBattleUnitStacks()) {
            if (hBox == battleUnitStack.getBattleHBox()) {
                return battleUnitStack;
            }
        }

        return null;
    }

    private void afterActionHandler(BattleUnitStack actor, Actions action) {
        afterActionHandler(actor, null, action);
    }

    private void afterActionHandler(BattleUnitStack actor, BattleUnitStack target, Actions action) {
        // handler for certain actions
        switch (action) {
            case ATTACK:
                battle.getAllBattleUnitStacks().forEach(battleUnitStack -> {
                    if (battleUnitStack.getHP() == 0) {
                        battleUnitStack.getBattleHBox().getStyleClass().add(DEAD_HIGHLIGHT_CLASS);
                    }
                });
            case ACTIVESKILL:
            case GIVEUP:
                if (target != null) {
                    target.getBattleHBox().getStyleClass().removeAll(new ArrayList<String>() {{
                        add(TARGET_HIGHLISHT_CLASS);
                        add(RESURRECTION_HIGHLIGHT_CLASS);

                        if (target.getHP() != 0) {
                            add(DEAD_HIGHLIGHT_CLASS);
                        }
                    }});
                }

                selectedHBox = null;
                battle.getBattleArmy(actor).resetWaitAndDefenseActionsNumberInARow();
                break;
            case DEFENSE:
            case WAIT:
                battle.getBattleArmy(actor).waitAndDefenseActivated();
                break;
        }

        // updating battle unit stacks HBox info
        for (BattleUnitStack battleUnitStack: battle.getAllBattleUnitStacks()) {
            // update number of units info in HBox
            Label unitsNumberField = (Label) battleUnitStack.getBattleHBox().getChildren().get(2);
            unitsNumberField.setText("Number of units:\n" + battleUnitStack.getUnitsNumber() + " / " +  battleUnitStack.getInitialUnitsNumber());

            // update HBox tooltip info
            Tooltip tooltip = new Tooltip(battleUnitStack.toString());
            tooltip.setShowDelay(new Duration(2000));
            tooltip.setHideDelay(Duration.INDEFINITE);
            tooltip.getStyleClass().add("battle-tooltip");
            Tooltip.install(battleUnitStack.getBattleHBox(), tooltip);
        }

        // сheck if battle is ended and show screen with congratulations
        if (battle.isEnded()) {
            try {
                Parent parent = FXMLLoader.load(getClass().getResource("/gui/congratulationwindow/congratulationWindow.fxml"));

                Scene scene = new Scene(parent);
                scene.setFill(Color.TRANSPARENT);

                Stage stage = new Stage(StageStyle.TRANSPARENT);
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(Interface.getMainWindow());
                stage.setScene(scene);
                stage.show();

                TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(1), parent);
                translateTransition.setFromY( (-1) * parent.getLayoutBounds().getHeight() / 2 );
                translateTransition.setToY(0);
                translateTransition.play();
                return;
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }

        // remove highlight from actor
        actor.getBattleHBox().getStyleClass().remove(ACTOR_HIGHLIGHT_CLASS);

        // check if new round is started
        if (battle.getInitiativeScale().isEmpty()) {
            battle.createInitiativeScale();

            for (BattleUnitStack battleUnitStack: battle.getAllBattleUnitStacks()) {
                battleUnitStack.resetResisted();
            }

            showNewRoundInfo();
        }

        // set highlight on the current move unit stack
        battle.getCurrentMoveUnitsStack().getBattleHBox().getStyleClass().add(ACTOR_HIGHLIGHT_CLASS);
        setInitiativeScale();

        // update buttons tooltip info
        setButtonsTooltip();
    }


    private void setButtonsTooltip() {
        BattleUnitStack actor = battle.getCurrentMoveUnitsStack();

        // for attack
        String tmpAttackTooltip = "Passive Skills\n";
        if (actor.getUnitClass().getPassiveSkills().isEmpty()) {
            tmpAttackTooltip = "\n";
        } else {
            for (PassiveSkills passiveSkill: actor.getUnitClass().getPassiveSkills()) {
                tmpAttackTooltip += '\t' + passiveSkill.getTitle() + ": " + passiveSkill.getDescription() + '\n';
            }
        }

        final String attackTooltip = tmpAttackTooltip.substring(0, tmpAttackTooltip.length() - 1);

        // for active skill
        String tmpActiveSkillTooltip = "Active Skill\n\t";
        if (actor.getUnitClass().getActiveSkill() == null) {
            tmpActiveSkillTooltip = "";
        } else {
            tmpActiveSkillTooltip += actor.getUnitClass().getActiveSkill().getTitle() + ": " + actor.getUnitClass().getActiveSkill().getDescription();
        }

        final String activeSkillTooltip = tmpActiveSkillTooltip;


        ArrayList<Tooltip> tooltips = new ArrayList<Tooltip>(){{
            add(new Tooltip(attackTooltip)); // for attack
            add(new Tooltip("Defense: actor skips the turn (doesn't play in this round)\nand increases own defense by 30%")); // for defense
            add(new Tooltip(activeSkillTooltip)); // for active skill
            add(new Tooltip("Wait: actor skips the turn (take turn further in this round)\nand move to the end of the initiative scale, where priority for anticipants is inversive")); // for wait
        }};

        tooltips.forEach(tooltip -> {
            tooltip.getStyleClass().add("battle-tooltip");
        });

        if (!attackTooltip.equals("")) {
            attackButton.setTooltip(tooltips.get(0));
        } else {
            attackButton.setTooltip(null);
        }

        if (!activeSkillTooltip.equals("")) {
            activeSkillButton.setTooltip(tooltips.get(2));
        } else {
            activeSkillButton.setTooltip(null);
        }

        defenseButton.setTooltip(tooltips.get(1));
        waitButton.setTooltip(tooltips.get(3));
    }

    private boolean isWaitAndDefenseForbidden(BattleUnitStack actor) {
        BattleArmy actorBattleArmy = battle.getBattleArmy(actor);

        if (actorBattleArmy.getWaitAndDefenseActionsNumberInARow() == Battle.MAX_WAIT_AND_DEFENSE_ACTIONS_IN_A_ROW) {
            Interface.displayError("Wait and Defense can't be used together more than " + Battle.MAX_WAIT_AND_DEFENSE_ACTIONS_IN_A_ROW + " times in a row!");
            return true;
        }

        return false;
    }

    private void showChange(HBox targetHBox, Number change, String imageURL) {
        HBox changeHBox = null;
        try {
            changeHBox = FXMLLoader.load(getClass().getResource("/gui/battlescene/change.fxml"));
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }

        if (change instanceof Double) {
            change = getFormattedValue(change.doubleValue());
        }

        String styleClass = (change.doubleValue() > 0) ? "change-pos" : "change-neg";
        String signStr = (change.doubleValue() > 0) ? "+" : "";
        int signInt = (int) (change.doubleValue() / Math.abs(change.doubleValue()));
        Image trendImageTemplate = (change.doubleValue() > 0) ?
                                    new Image("/gui/assets/green_up.png") :
                                    new Image("/gui/assets/red_down.png");

        // set label text
        Label changeLabel = (Label) changeHBox.getChildren().get(0);
        changeLabel.setText(signStr + change);
        changeLabel.getStylesheets().add(getClass().getResource("battleScene.css").toExternalForm());
        changeLabel.getStyleClass().add(styleClass);

        // set trend image
        ImageView trendImage = (ImageView) changeHBox.getChildren().get(1);
        trendImage.setImage(trendImageTemplate);
        trendImage.setScaleX(0.8); trendImage.setScaleY(0.8);

        // set changed characteristic image
        ImageView characteristicImage = (ImageView) changeHBox.getChildren().get(2);
        characteristicImage.setImage(new Image(imageURL));

        // check if func got wait action
        if (change.doubleValue() == 0) {
            changeHBox.getChildren().remove(0, 2);
            signInt = 1;
        }

        // locate change box on the scene
        ((AnchorPane) Interface.getMainWindow().getScene().getRoot()).getChildren().add(changeHBox);
        Interface.getMainWindow().getScene().getRoot().applyCss();
        Interface.getMainWindow().getScene().getRoot().layout();
        Bounds targetHBoxBounds = targetHBox.localToScene(targetHBox.getLayoutBounds());
        changeHBox.setLayoutX(targetHBoxBounds.getMinX() + (targetHBox.getWidth() - changeHBox.getWidth()) / 2);
        changeHBox.setLayoutY(targetHBoxBounds.getMinY() - signInt * changeHBox.getHeight() / 2);

        // set transitions
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(5));
        translateTransition.setToY(signInt * (-200));

        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(3));
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);

        ParallelTransition parallelTransition = new ParallelTransition(changeHBox, translateTransition, fadeTransition);
        parallelTransition.play();


        final HBox finalChangeHBox = changeHBox;
        parallelTransition.setOnFinished(actionEvent -> ((AnchorPane) finalChangeHBox.getScene().getRoot()).getChildren().remove(finalChangeHBox));
    }

    private Double getFormattedValue(double value) {
        String decimal = ((Double) (value)).toString().split("\\.")[1];

        if (decimal.length() > 3) {
            return Double.parseDouble(String.format(Locale.US, "%.3f", value));
        } else {
            return value;
        }
    }

}
