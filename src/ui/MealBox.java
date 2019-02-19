package ui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MealBox {
    public static void display(String meal){
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Meal of the day");
        window.setMaxWidth(500);
        window.setMinHeight(250);

        BorderPane borderPane = new BorderPane();
        VBox view = new VBox(10);
        view.setPadding(new Insets(20,20,20,20));
        Label label = new Label();
        label.setText(meal);
        label.setWrapText(true);
        label.setTextAlignment(TextAlignment.JUSTIFY);

        HBox bottomAlign = new HBox();
        Button doneButton = new Button("Done");
        bottomAlign.getChildren().add(doneButton);
        bottomAlign.setPadding(new Insets(10,10,10,10));
        bottomAlign.setAlignment(Pos.CENTER);

        doneButton.setOnAction(e -> {
            window.close();
        });

        view.getChildren().addAll(label);


        ScrollPane scroll = new ScrollPane();
        scroll.setContent(view);
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scroll.setFitToWidth(true);

        borderPane.setCenter(scroll);
        borderPane.setBottom(bottomAlign);
        Scene scene = new Scene(borderPane, 400, 500);
        window.setScene(scene);

        window.show();

    }
}
