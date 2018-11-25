package ui;

import exceptions.NoPreviousException;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.Scene;

import java.io.IOException;

public class PreviousBox extends MainMenu{
    static Stage window;

    public static void display(){
        window = new Stage();
        window.setTitle("Previous Log");

        Label previous = new Label();
        try {
            previous.setText(itemLog.viewPrevious());
        } catch (IOException e) {

        } catch (NoPreviousException e) {
            previous.setText("No previous log found!");
            window.show();
        }
        StackPane base = new StackPane();
        base.getChildren().add(previous);
        StackPane.setMargin(previous, new Insets(10,10,10,10));

        Scene scene = new Scene(base, 400,200);
        window.setScene(scene);
        window.show();
    }

}
