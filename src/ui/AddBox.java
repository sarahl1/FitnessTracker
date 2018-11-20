package ui;

import com.sun.tools.javac.comp.Enter;
import exceptions.NotAnItemException;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.*;
import javafx.scene.Scene;

import java.util.Set;

public class AddBox extends MainMenu {

    public static void display() {
        Stage window = new Stage();
//        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Add");

        GridPane grid = new GridPane();
        grid.setHgap(8);
        grid.setVgap(8);
        grid.setPadding(new Insets(10, 10, 10, 10));

        Label exercise = new Label("Update exercise log: ");
        GridPane.setConstraints(exercise, 0, 0);
        Button exerciseButton = new Button("Add");
        GridPane.setConstraints(exerciseButton, 1, 0);

        Label food = new Label("Update food log: ");
        GridPane.setConstraints(food, 0, 1);
        Button foodButton = new Button("Add");
        GridPane.setConstraints(foodButton, 1, 1);

        Label nutrition = new Label("View nutritional facts: ");
        GridPane.setConstraints(nutrition, 0, 2);
        Button nutritionButton = new Button("View");
        GridPane.setConstraints(nutritionButton, 1, 2);

        Label create = new Label("Create custom item: ");
        GridPane.setConstraints(create, 0, 3);
        Button createButton = new Button("Create");
        GridPane.setConstraints(createButton, 1, 3);

        grid.getChildren().addAll(exercise, exerciseButton, food, foodButton,
                nutrition, nutritionButton, create, createButton);

        exerciseButton.setOnAction(e -> displayExerciseChoices());
        foodButton.setOnAction(e -> {
            try {
                displayFoodChoices();
            } catch (NotAnItemException e1) {
                e1.printStackTrace();
            }
        });
        Scene scene = new Scene(grid, 250, 200);

        window.setScene(scene);
        window.show();
    }

    private static void displayFoodChoices() throws NotAnItemException {
        Stage window = new Stage();
        window.setTitle("Add Food");
        VBox layout = new VBox();
        layout.setPadding(new Insets(20, 20, 20, 20));

        TextField foodField = new TextField();
        foodField.setPromptText("Enter keyword");

        Button submit = new Button("Search");
        foodField.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (foodField.getText().trim().isEmpty()) { // if blank textfield
                    submit.setDisable(true);
                } else {
                    submit.setDisable(false);
                }
            }
        });
        ChoiceBox<String> foodChoices = new ChoiceBox<>();
        foodChoices.setMaxHeight(200);
        submit.setOnAction(f -> {
            try {
                for (Item i : itemLog.search(foodField.getText()).getLog().keySet()) {
                    foodChoices.getItems().add(i.getName() + "- " + i.getCalories() + " cals");
                }
            } catch (NotAnItemException e1) {
                e1.printStackTrace();
            }
        });


        Button confirm = new Button("Add");
        confirm.setOnAction(
                e -> {
                    getChoice(foodChoices, itemLog.getAllFood().getLog().keySet(), itemLog.getFoodEaten());
                    window.close();
                });

        layout.getChildren().addAll(foodField, submit, foodChoices, confirm);
        Scene exerciseScene = new Scene(layout, 250, 200);
        window.setScene(exerciseScene);
        window.show();

    }

    private static void displayExerciseChoices() {
        Stage window = new Stage();
        window.setTitle("Add Exercise");
        VBox layout = new VBox();
        layout.setPadding(new Insets(20, 20, 20, 20));
        ChoiceBox<String> exerciseChoices = new ChoiceBox<>();
        for (Item e : itemLog.getAllExercise().getLog().keySet()) {
            exerciseChoices.getItems().add(e.getName());
        }
        exerciseChoices.setValue("Jog");
        Button confirm = new Button("Add");
        confirm.setOnAction(
                e -> {
                    getChoice(exerciseChoices, itemLog.getAllExercise().getLog().keySet(), itemLog.getExerciseDone());
                    window.close();
                });
        layout.getChildren().addAll(exerciseChoices, confirm);
        Scene exerciseScene = new Scene(layout, 250, 200);
        window.setScene(exerciseScene);
        window.show();

    }

    private static void getChoice(ChoiceBox<String> choiceBox, Set<Item> set, ItemDone id) {
        String item = choiceBox.getValue();
        item = item.split("-")[0];
        boolean found = false;
        for (Item i : set) {
            if (i.getName().equals(item) && found==false) {
                itemLog.addItem(i, id);
                update(itemLog);
                found = true;
            }
        }

    }
}
