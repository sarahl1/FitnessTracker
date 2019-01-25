package ui;

import exceptions.HighTotalException;
import exceptions.LowTotalException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.util.Duration;
import model.Exercise;
import model.Food;
import model.Item;

import java.util.ArrayList;
import java.util.Iterator;

import static java.lang.Integer.parseInt;

public class RemoveBox extends MainMenu {
    static Item foundItem;

    public static void display() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Remove");


        ListView<String> listView = new ListView<>();
        listView.setMaxHeight(200);
        for (Item i : itemLog.getExerciseDone().getDone()) {
            listView.getItems().add(i.getName() + "- " + i.getCalories());
        }
        for (Item i : itemLog.getFoodEaten().getDone()) {
            listView.getItems().add(i.getName() + "- " + i.getCalories());
        }

        Button remove = new Button("Remove");
        remove.setPadding(new Insets(10, 10, 10, 10));
        remove.setOnAction(e -> {
            mediaPlayer.seek(Duration.ZERO);
            mediaPlayer.play();
            String selected = listView.getItems().get(listView.getSelectionModel().getSelectedIndex());
            try {
                removeItem(selected);
            } catch (HighTotalException e1) {
                exception.setText(e1.getMessage());
                window.show();
            } catch (LowTotalException e1) {
                exception.setText(e1.getMessage());
                window.show();
            }
            window.close();
        });


        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(listView, remove);

        Scene scene = new Scene(layout, 300, 350);
        window.setScene(scene);
        window.show();

    }

    private static void removeItem(String selected) throws HighTotalException, LowTotalException {
        boolean found = false;
        String name = selected.split("- ")[0];
        String calories = selected.split("- ")[1];
        String finalCalories = calories.split(" cals")[0];
        ArrayList<Item> toBeRemoved = new ArrayList<>();
        for (Item i : itemLog.getExerciseDone().getDone()) {
            if ((i.getName().equals(name)) && (i.getCalories() == parseInt(finalCalories)) && found == false) {
                toBeRemoved.add(i);
                found = true;
                foundItem = i;
            }
        }
        for (Item i : itemLog.getFoodEaten().getDone()) {
            if ((i.getName().equals(name)) && (i.getCalories() == parseInt(calories)) && found == false) {
                toBeRemoved.add(i);
                foundItem = i;
                found = true;
            }
        }
        for (Item i : toBeRemoved) {
            if (i instanceof Food) {
                itemLog.removeItem(itemLog.getFoodEaten(), i);
                updateRemove(itemLog, i);
            }
            if (i instanceof Exercise) {
                itemLog.removeItem(itemLog.getExerciseDone(), i);
                updateRemove(itemLog, i);
            }
            }
        display();
        }
    }


