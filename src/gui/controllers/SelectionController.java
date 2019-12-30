package gui.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.ResourceBundle;

import com.sun.org.apache.xpath.internal.res.XPATHErrorResources;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import gamengine.UnitsLoader;
import gamengine.march.Army;
import gamengine.march.UnitsStack;
import gamengine.unit.Unit;
import gui.Interface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Pair;

import javax.swing.*;

public class SelectionController {

    ArrayList<Pair<ComboBox, TextField>> listUnitArmy1 = new ArrayList<>();
    ArrayList<Pair<ComboBox, TextField>> listUnitArmy2 = new ArrayList<>();

    UnitsLoader unitsLoader;
    {
        try {
            unitsLoader = new UnitsLoader();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField NameField1;

    @FXML
    private TextField NameField2;

    @FXML
    private VBox HeroBox1;

    @FXML
    private VBox HeroBox2;

    @FXML
    private Button AddHero1;

    @FXML
    private Button AddHero2;

    @FXML
    private Button AcceptButton;

    @FXML
    void initialize() {
        AcceptButton.setOnAction(event -> {
            Army army1 = formAmy(listUnitArmy1, 1);
            Army army2 = formAmy(listUnitArmy2, 2);

            if (army1 == null || army2 == null) {
                display();
                return;
            }

            System.out.println(army1);
            System.out.println(army2);
        });

        AddHero1.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                ArrayList<String> unitsName = new ArrayList<>();
                for (Unit unit: unitsLoader.getAllUnits()) {
                    unitsName.add(unit.getType());
                }

                HBox hBox = new HBox();
                hBox.setPrefSize(370, 65);
                hBox.getStylesheets().add(Interface.class.getResource("fxmls/styles.css").toExternalForm());
                hBox.getStyleClass().add("hbox-hero-1");
                hBox.setAlignment(Pos.CENTER);

                VBox vBoxLeft = new VBox();
                vBoxLeft.setPrefSize(185, 65);
                Text heroText = new Text("Type of unit");
                heroText.setId("TextHero1");
                ComboBox comboBox = new ComboBox();
                comboBox.setId("Combo");
                comboBox.setPrefWidth(140);
                comboBox.setPromptText("Select...");
                comboBox.getItems().addAll(unitsName);

                vBoxLeft.getChildren().addAll(heroText, comboBox);
                vBoxLeft.setAlignment(Pos.CENTER);

                VBox vBoxRight = new VBox();
                vBoxRight.setPrefSize(185, 65);
                Text numberText = new Text("Number of units");
                numberText.setId("TextHero1");
                TextField textField = new TextField();
                textField.setId("Number1");
                textField.setPrefWidth(70);
                textField.setMaxWidth(70);
                vBoxRight.getChildren().addAll(numberText, textField);
                vBoxRight.setAlignment(Pos.CENTER);

                hBox.getChildren().addAll(vBoxLeft, vBoxRight);

                setDeleteNode(HeroBox1, hBox, AddHero1, listUnitArmy1, comboBox, textField);
                listUnitArmy1.add(new Pair<>(comboBox, textField));
                HeroBox1.getChildren().add(hBox);
                ObservableList<Node> swapCollection = FXCollections.observableArrayList(HeroBox1.getChildren());
                Collections.swap(swapCollection, swapCollection.size() - 1, swapCollection.size() - 2);
                HeroBox1.getChildren().clear();
                HeroBox1.getChildren().addAll(swapCollection);
                if (HeroBox1.getChildren().size() == Army.MAX_STACKS_NUM + 1) {
                    HeroBox1.getChildren().remove(Army.MAX_STACKS_NUM);
                }
            }
        });

        AddHero2.setOnMouseClicked(event -> {
            MouseButton button = event.getButton();
            if (button == MouseButton.PRIMARY) {
                UnitsLoader unitsLoader = null;
                try {
                    unitsLoader = new UnitsLoader();
                } catch (Exception e) {
                    e.printStackTrace();
                    System.exit(-1);
                }
                ArrayList<String> unitsName = new ArrayList<>();
                for (Unit unit: unitsLoader.getAllUnits()) {
                    unitsName.add(unit.getType());
                }

                HBox hBox = new HBox();
                hBox.setPrefSize(370, 65);
                hBox.getStylesheets().add(Interface.class.getResource("fxmls/styles.css").toExternalForm());
                hBox.getStyleClass().add("hbox-hero-2");
                hBox.setAlignment(Pos.CENTER);

                VBox vBoxLeft = new VBox();
                vBoxLeft.setPrefSize(185, 65);
                Text heroText = new Text("Type of unit");
                heroText.setId("TextHero2");
                ComboBox comboBox = new ComboBox();
                comboBox.setId("Combo");
                comboBox.setPrefWidth(140);
                comboBox.setPromptText("Select...");
                comboBox.getItems().addAll(unitsName);

                vBoxLeft.getChildren().addAll(heroText, comboBox);
                vBoxLeft.setAlignment(Pos.CENTER);

                VBox vBoxRight = new VBox();
                vBoxRight.setPrefSize(185, 65);
                Text numberText = new Text("Number of units");
                numberText.setId("TextHero2");
                TextField textField = new TextField();
                textField.setId("Number2");
                textField.setPrefWidth(70);
                textField.setMaxWidth(70);
                vBoxRight.getChildren().addAll(numberText, textField);
                vBoxRight.setAlignment(Pos.CENTER);

                hBox.getChildren().addAll(vBoxLeft, vBoxRight);

                setDeleteNode(HeroBox2, hBox, AddHero2, listUnitArmy2, comboBox, textField);
                listUnitArmy2.add(new Pair<>(comboBox, textField));
                HeroBox2.getChildren().add(hBox);
                ObservableList<Node> swapCollection = FXCollections.observableArrayList(HeroBox2.getChildren());
                Collections.swap(swapCollection, swapCollection.size() - 1, swapCollection.size() - 2);
                HeroBox2.getChildren().clear();
                HeroBox2.getChildren().addAll(swapCollection);
                if (HeroBox2.getChildren().size() == 7) {
                    HeroBox2.getChildren().remove(6);
                }
            }
        });

    }

    void setDeleteNode(VBox mainVBox, HBox hBox, Button addButton, ArrayList<Pair<ComboBox, TextField>> listUnitArmy, ComboBox comboBox, TextField textField) {
        hBox.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                ObservableList<Node> removeCollection = FXCollections.observableArrayList(mainVBox.getChildren());
                removeCollection.remove(hBox);
                listUnitArmy.remove(new Pair<>(comboBox, textField));
                mainVBox.getChildren().clear();
                mainVBox.getChildren().addAll(removeCollection);
                if (mainVBox.getChildren().size() == 5 && !removeCollection.contains(addButton)) {
                    mainVBox.getChildren().add(addButton);
                }
            }
        });
    }

    Army formAmy(ArrayList<Pair<ComboBox, TextField>> listUnitArmy, int armyIndex) {
        ArrayList<UnitsStack> unitsStacks = new ArrayList<>();
        for (Pair<ComboBox, TextField> pairUnitStack: listUnitArmy) {
            String unitType = (String)pairUnitStack.getKey().getValue();
            int unitsNumber;
            try {
                unitsNumber = Integer.parseInt(pairUnitStack.getValue().getText());
            } catch (Exception e) {
                return null;
            }

            if (unitType.equals("null") || unitsNumber <= 0 || unitsNumber > UnitsStack.MAX_UNITS_NUM) {
                return null;
            } else {
                unitsStacks.add(new UnitsStack(unitsLoader.createUnit(unitType),
                                unitsNumber, armyIndex));
            }
        }

        if (unitsStacks.isEmpty()) {
            return null;
        }

        return new Army(unitsStacks);
    }

    void display() {
        try {
            Parent newPane = FXMLLoader.load(Interface.class.getResource("fxmls/error.fxml"));
            Scene newScene = new Scene(newPane);
            Stage newStage = new Stage();
            newStage.setTitle("Error");
            newStage.setScene(newScene);
            newStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
