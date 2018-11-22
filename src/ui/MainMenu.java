package ui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Scene;
import model.ItemLog;
import observer.ItemDoneMonitor;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class MainMenu extends Application {
    Stage window;
    Scene scene;
    static int totalCals;
    static ItemLog itemLog;
    static Label titleCals;
    static ItemDoneMonitor observer;

    public void setItemLog(ItemLog il){
        this.itemLog = il;
    }

    public void run(String[] args){
        observer = new ItemDoneMonitor();
        launch(args);
    }

    public static void update(ItemLog il){
        totalCals = il.getTotal();
        titleCals.setText(Integer.toString(totalCals));
    }

    @Override
    public void start(Stage primaryStage){
        window = primaryStage;
        window.setTitle("Calorie Counter");
        BorderPane mainUI = new BorderPane();
        mainUI.setPadding(new Insets(10,10,10,10));

        VBox top = new VBox(10);
        top.setAlignment(Pos.CENTER);
        Label titleString = new Label("Welcome to your calorie counter!");
        titleCals = new Label("Calories logged: " + totalCals);
        top.getChildren().addAll(titleString, titleCals);

        VBox leftMenu = new VBox(15);
        leftMenu.setPadding(new Insets(10,10,10,10));

        Button addButton = new Button("Add");
        Button removeButton = new Button("Remove");
        Button viewButton = new Button("View Previous");
        Button setButton = new Button("Resume Previous");
        Button exitButton = new Button("Exit");
        addButton.setPadding(new Insets(5, 5, 5, 5));
        removeButton.setPadding(new Insets(5, 5, 5, 5));
        viewButton.setPadding(new Insets(5, 5, 5, 5));
        setButton.setPadding(new Insets(5, 5, 5, 5));
        exitButton.setPadding(new Insets(5, 5, 5, 5));
        leftMenu.getChildren().addAll(addButton,removeButton,viewButton,setButton,exitButton);

        exitButton.setOnAction(e -> closeProgram());
        addButton.setOnAction(e -> addOptions());
        removeButton.setOnAction(e -> removeOptions());

        mainUI.setTop(top);
        mainUI.setLeft(leftMenu);

        Button mealButton = new Button("MEAL OF THE DAY");
        mealButton.setMinSize(200, 200);
        mealButton.setPadding(new Insets(20,20,10,10));
        mealButton.setOnAction(e -> {
            try {
                viewMeal();
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
        });
        mainUI.setRight(mealButton);
        scene = new Scene(mainUI, 500, 300);

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

    private void removeOptions() { RemoveBox.display(); }

    private void addOptions() {
        AddBox.display();
    }

    private void viewMeal() throws FileNotFoundException {
        Scanner inFile = new Scanner(new FileReader("meal.txt"));
        String sFile = "";

        while(inFile.hasNextLine())
            sFile = sFile + inFile.nextLine() + "\n";
        MealBox.display(sFile);
    }
}
