package ui;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.ItemLog;
import javafx.scene.Scene;

public class AddBox {
    static boolean answer;
    public static boolean display(ItemLog il){
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Add");

        GridPane grid = new GridPane();
        grid.setHgap(8);
        grid.setVgap(8);
        grid.setPadding(new Insets(10,10,10,10));

        Label exercise = new Label("Update exercise log: ");
        GridPane.setConstraints(exercise,0,0);
        Button exerciseButton = new Button("Add");
        GridPane.setConstraints(exerciseButton,1,0);

        Label food = new Label("Update food log: ");
        GridPane.setConstraints(food,0,1);
        Button foodButton = new Button("Add");
        GridPane.setConstraints(foodButton,1,1);

        Label nutrition = new Label("View nutritional facts: ");
        GridPane.setConstraints(nutrition,0,2);
        Button nutritionButton = new Button("View");
        GridPane.setConstraints(nutritionButton,1,2);

        Label create = new Label("Create custom item: ");
        GridPane.setConstraints(create,0,3);
        Button createButton = new Button("Create");
        GridPane.setConstraints(createButton,1,3);

        grid.getChildren().addAll(exercise, exerciseButton, food, foodButton,
                nutrition, nutritionButton, create, createButton);
        Scene scene = new Scene(grid, 250, 200);
        window.setScene(scene);
        window.show();
        return true;
    }
}
