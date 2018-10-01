package model;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static java.lang.Integer.parseInt;

public class Food implements CalorieCounter, Save, Load {
    private String id;
    private String name;
    private int calories;

    public int total = 0;
    private int max = 2500;

    public HashSet<Food> allFood = new HashSet<>();
    private Scanner scan = new Scanner(System.in);

    private String num;
    private String fff;

    private ArrayList<Food> listToRemove;
    private ArrayList<Food> food_eaten;


    public Food(String id, String name, int calories){
        this.id = id;
        this.name = name;
        this.calories = calories;

    }

    //getters
    public int getCalories(){ return calories;}
    public String getId(){ return id;}
    public String getName(){ return name;}
    public ArrayList<Food> getFood_eaten(){ return food_eaten; }
    public ArrayList<Food> getListToRemove(){ return listToRemove;}

    //MODIFIES: this
    //EFFECTS: instantiates Food objects with id, name, and calories -- then adds them to a list of all food
    public void makeFood(){
        Food apple = new Food("001", "Apple", 100);
        Food orange = new Food("002", "Orange", 120);
        Food celery = new Food("003", "Celery", 60);
        Food hamburger = new Food("100", "Hamburger", 350);
        Food cheeseburger = new Food("101", "Apple", 100);
        Food p_pizza = new Food("102", "Pepperoni Pizza", 200);
        Food h_pizza = new Food("103", "Hawaiian Pizza", 180);
        Food v_pizza = new Food("104", "Veggie Pizza", 180);
        allFood.add(apple);
        allFood.add(orange);
        allFood.add(celery);
        allFood.add(hamburger);
        allFood.add(cheeseburger);
        allFood.add(p_pizza);
        allFood.add(h_pizza);
        allFood.add(v_pizza);
        food_eaten = new ArrayList<>();
    }

    //EFFECTS: prints a list of all food with id, name, and calories
    public void printFood(){
        for (Food f : allFood){
            System.out.println(f.id + " " + f.name + ": " + f.calories + "\n");
        }
    }

    //MODIFIES: this
    //EFFECTS: takes user input and calls find food on the ID input
    public void selectFood(){
        System.out.println("Please enter the ID of the item you want to add.");
        num = scan.nextLine();
        findFood(num);
    }

    //EFFECTS: prints options and calls corresponding methods
    public void options() throws IOException{
        System.out.println( "[1] Add more items \n" +
                "[2] Remove item \n" +
                "[3] Exit \n" +
                "[4] View previous day \n" +
                "[5] Resume previous day");
        String choice = scan.nextLine();
        switch (choice) {
            case "1":
                printFood();
                selectFood();
                options();
                break;
            case "2":
                printLog();
                scanRemove();
                createRemoveList();
                removeFood(fff);
                removeFromEaten();
                options();
                break;
            case "3":
                printLog();
                break;
            case "4":
                viewPrevious();
                options();
                break;
            case "5":
                setFood_eaten();
                setTotal();
                printLog();
                options();
                break;
        }
    }


    //REQUIRES: testFood is not empty
    //EFFECTS: prints a list of food eaten, total calories eaten, and calories left
    private void printLog(){
        System.out.println("Summary: ");
        for (Food f : food_eaten){
            System.out.println(f.id + " " + f.name + ": " + f.calories + "\n");
        }
        System.out.println("Total calories: " + total + "\n" +
                "Calories until limit: " + (max - total));
    }



    //REQUIRES: allFood is not empty
    //MODIFIES: this
    //EFFECTS: looks for selected food in list of all food and calls addFood if it is found
    private void findFood(String food){
        for (Food f : allFood){
            if (f.id.equals(food)){
                addFood(f);
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: adds food to testFood -- adds calories of the food to total
    public void addFood(Food f){
        food_eaten.add(f);
        addCal(f);
    }

    //REQUIRES: testFood is not empty
    //MODIFIES: this
    //EFFECTS: takes user input and adds the food with input id to listToRemove
    public void removeFood(String toRemove){
        for (Food f : food_eaten) {
            if (f.id.equals(toRemove)) {
                listToRemove.add(f);
            }
        }

    }

    //MODIFIES: this
    //EFFECTS: takes user input and sets it to fff, instantiates listToRemove
    private void scanRemove(){
        System.out.println("Enter ID of food to remove.");
        fff = scan.nextLine();
    }

    //EFFECTS: instantiates a list which will hold items to remove
    public void createRemoveList(){
        listToRemove = new ArrayList<>();
    }



    //REQUIRES: listToRemove and testFood are not empty
    //MODIFIES: this
    //EFFECTS: remove items from testFood that are
    public void removeFromEaten(){
        for (Food f : listToRemove) {
            food_eaten.remove(f);
            removeCal(f);
        }
    }

    //REQUIRES: calories of f has to be positive
    //MODIFIES: this
    //EFFECTS: add calories of f to total
    public void addCal(CalorieCounter f){
        total += f.getCalories();
    }


    //REQUIRES: calories of f has to be positive
    //MODIFIES: this
    //EFFECTS: subtract calories of f from total
    public void removeCal(CalorieCounter f){
        total -= f.getCalories();
    }

    //EFFECTS: prints end statement
    public void exit(){
        System.out.println("Changes saved.");}

    //EFFECTS: returns the date with format yyyy/MM/dd
    private String date(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate localDate = LocalDate.now();
        return dtf.format(localDate); //2016/11/16
    }

    //EFFECTS: returns a log of food eaten for PrintWriter
    private String summary(Food f){
        return (f.id + " " + f.name + ": " + f.calories );
    }

    //EFFECTS: prints total calories
    public String printTotal() {
        return ("Total calories: " + total);
   }

    //EFFECTS: prints calories until limit
    public String printRemaining(){
       return ("Calories until limit: " + (max - total));
   }


    //REQUIRES: there exists a previous log
    //EFFECTS: prints the previous log
    @Override
    public void viewPrevious() throws IOException{
        Scanner inFile = new Scanner(new FileReader("inputfile.txt"));
        String sFile = "";

        while(inFile.hasNextLine())
            sFile = sFile + inFile.nextLine() + "\n";

        // prints to console
        System.out.println("Previous Log: ");
        System.out.println(sFile);
    }

    //REQUIRES: there exists a previous log
    //MODIFIES: this
    //EFFECTS: adds food from previous log to current list of food eaten
    public void setFood_eaten() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("inputfileLIST.txt"));
        int lines = 0;
        while (reader.readLine() != null) lines++;
        reader.close();
        for (int n = 0; n < lines; n++) {
                String line = Files.readAllLines(Paths.get("inputfileLIST.txt")).get(n);
                for (Food f : allFood) {
                    if (line.equals(f.getId())) {
                        food_eaten.add(f);
                    }

                }
            }
        }


    //REQUIRES: food eaten is non-empty
    //EFFECTS: saves ID of food eaten to file
    public void saveToInputList() throws IOException{
        List<String> lines = new ArrayList<>();
        FileWriter fw = new FileWriter("inputfileLIST.txt", false);
        PrintWriter writer = new PrintWriter(fw);
        for (Food f : food_eaten) {
            lines.add(f.getId());
        }
        for (String line : lines){
            writer.println(line);
        }
        writer.close();
    }

    //REQUIRES: there exists a previous non-negative total
    //MODIFIES: this
    //EFFECTS: adds previous total to current total
    public void setTotal() throws IOException{
        String line = Files.readAllLines(Paths.get("inputTOTAL.txt")).get(0);
        total += parseInt(line);
    }

    //REQUIRES: total is non-negative
    //EFFECTS: saves total to file
    public void saveToTotal() throws IOException{
        FileWriter fw = new FileWriter("inputTOTAL.txt", false);
        PrintWriter writer = new PrintWriter(fw);
        writer.println(Integer.toString(total));
        writer.close();
    }


    //REQUIRES: food eaten is non-empty
    //EFFECTS: saves log to a list of all logs
    @Override
    public void saveToOutput() throws IOException {
        List<String> lines = new ArrayList<>();
        FileWriter fw = new FileWriter("outputfile.txt", true);
        PrintWriter writer = new PrintWriter(fw);
        lines.add(date());
        for (Food f : food_eaten) { //change summary parameter to Food f
            lines.add(summary(f));
        }
        lines.add(printTotal());
        lines.add(printRemaining());
        for (String line : lines){

            writer.println(line);

        }
        writer.println("");
        writer.close();
    }

    //REQUIRES: food eaten is non-empty
    //EFFECTS: saves log to file
    @Override
    public void saveToInput() throws IOException {
        List<String> lines = new ArrayList<>();
        FileWriter file = new FileWriter("inputfile.txt", false);
        PrintWriter writeToInput = new PrintWriter(file);
        lines.add(date());
        for (Food f : food_eaten) { //change summary parameter to Food f
            lines.add(summary(f));
        }
        lines.add(printTotal());
        lines.add(printRemaining());
        for (String line : lines) {

            writeToInput.println(line);

        }
        writeToInput.println("");
        writeToInput.close();
    }


}