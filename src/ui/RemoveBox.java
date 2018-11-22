package ui;

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
import model.Item;

import static java.lang.Integer.parseInt;

public class RemoveBox extends MainMenu{
    static Item foundItem;
    static Label notify = new Label();

    public static void display(){
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Remove");

        GridPane grid = new GridPane();
        grid.setHgap(8);
        grid.setVgap(8);
        grid.setPadding(new Insets(10,10,10,10));

        ListView<String> listView = new ListView<>();
        for (Item i: itemLog.getExerciseDone().getDone()){
            listView.getItems().add(i.getName() + "- " + i.getCalories());
        }
        for (Item i: itemLog.getFoodEaten().getDone()){
            listView.getItems().add(i.getName() + "- " + i.getCalories());
        }

        Button remove = new Button("Remove");
        remove.setPadding(new Insets(10,10,10,10));
        remove.setAlignment(Pos.CENTER);
        remove.setOnAction(e -> {
                String selected = listView.getItems().get(listView.getSelectionModel().getSelectedIndex());
                removeItem(selected);
                display();
                window.close();
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(listView, remove);
        if (foundItem != null){
            notify = new Label(observer.updateRemove(foundItem));
            GridPane.setConstraints(notify, 0, 4);
            grid.getChildren().add(notify);
        }
        Scene scene = new Scene(layout, 300, 300);
        window.setScene(scene);
        window.show();

    }

    private static void removeItem(String selected){
        boolean found = false;
            String name = selected.split("- ")[0];
            String calories = selected.split("- ")[1];
            String finalCalories = calories.split(" cals")[0];
            for (Item i : itemLog.getExerciseDone().getDone()){
                if ((i.getName().equals(name)) && (i.getCalories() == parseInt(finalCalories)) && found==false){
                    itemLog.removeItem(itemLog.getExerciseDone(), i);
                    found = true;
                    update(itemLog);
                }
            }
            for (Item i : itemLog.getFoodEaten().getDone()){
                if ((i.getName().equals(name))&& (i.getCalories() == parseInt(calories)) && found == false){
                    itemLog.removeItem(itemLog.getFoodEaten(), i);
                    found = true;
                    update(itemLog);
                }
            }
        }
    }

