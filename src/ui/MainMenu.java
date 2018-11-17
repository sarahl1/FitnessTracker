package ui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Scene;
import model.ItemLog;

public class MainMenu extends Application {
    Button b1, b2, b3, b4, b5;
    Stage window;
    Scene scene;
    static int totalCals;
    static ItemLog itemLog;

    public static void run(ItemLog il){
        launch();
        totalCals = il.getTotal();
        itemLog = il;
    }

    public static void update(ItemLog il){
        totalCals = il.getTotal();
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        window = primaryStage;
        window.setTitle("Calorie Counter");

        BorderPane mainUI = new BorderPane();
        mainUI.setPadding(new Insets(10,10,10,10));

        VBox top = new VBox(10);
        top.setAlignment(Pos.CENTER);
        Label titleString = new Label("Welcome to your calorie counter!");
        Label titleCals = new Label("Calories logged: " + totalCals);
        top.getChildren().addAll(titleString, titleCals);

        VBox leftMenu = new VBox(10);
        leftMenu.setPadding(new Insets(10,10,10,10));

        Button addButton = new Button("Add");
        Button removeButton = new Button("Remove");
        Button viewButton = new Button("View Previous");
        Button setButton = new Button("Resume Previous");
        Button exitButton = new Button("Exit");
        addButton.setMinWidth(15);
        removeButton.setMinWidth(15);
        viewButton.setMinWidth(15);
        setButton.setMinWidth(15);
        exitButton.setMinWidth(15);
        leftMenu.getChildren().addAll(addButton,removeButton,viewButton,setButton,exitButton);

        exitButton.setOnAction(e -> closeProgram());
        addButton.setOnAction(e -> showOptions());

        mainUI.setTop(top);
        mainUI.setLeft(leftMenu);
        scene = new Scene(mainUI, 400, 500);

        window.setOnCloseRequest(e -> {
            e.consume();
            closeProgram();
        });

        window.setScene(scene);
        window.show();
    }

    private void closeProgram() {
        Boolean answer = ConfirmBox.display("", "Sure you want to exit?");
        if(answer)
            window.close();
    }

    private void showOptions() {
        Boolean answer = AddBox.display(itemLog);
    }
}
