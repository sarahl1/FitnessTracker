package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class Food {
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
    public ArrayList<Food> food_eaten;


    public Food(String id, String name, int calories){
        this.id = id;
        this.name = name;
        this.calories = calories;

    }

    //getters
    public int getCalories(){ return calories;}
    public String getId(){ return id;}
    public String getName(){ return name;}


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
        num = scan.nextLine();
        findFood(num);
    }

    //EFFECTS: prints options and calls corresponding methods
    public void options() {
        System.out.println( "[1] Add more items \n" +
                "[2] Remove item \n" +
                "[3] Exit ");
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
                removeFood(food_eaten, fff, listToRemove);
                removeFromEaten(listToRemove, food_eaten);
                options();
                break;
            case "3":
                printLog();
                break;
        }
    }


    //REQUIRES: testFood is not empty
    //EFFECTS: prints a list of food eaten, total calories eaten, and calories left
    private void printLog(){
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
                addFood(food_eaten, f);
            }
        }
    }

    //REQUIRES: testFood is not empty
    //MODIFIES: this
    //EFFECTS: takes user input and adds the food with input id to listToRemove
    public void removeFood(ArrayList<Food> fl, String toRemove, ArrayList<Food> listToRemove){
        for (Food f : fl) {
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
        listToRemove = new ArrayList<>();
    }

    //MODIFIES: this
    //EFFECTS: adds food to testFood -- adds calories of the food to total
    public void addFood(ArrayList<Food> fl, Food f){
        fl.add(f);
        addCal(f);
    }



    //REQUIRES: listToRemove and testFood are not empty
    //MODIFIES: this
    //EFFECTS: remove items from testFood that are
    public void removeFromEaten(ArrayList<Food> toRemove, ArrayList<Food> fl){
        for (Food f : toRemove) {
            fl.remove(f);
            removeCal(f);
        }
    }

    //REQUIRES: calories of f has to be positive
    //MODIFIES: this
    //EFFECTS: add calories of f to total
    public void addCal(Food f){
        total += f.getCalories();
    }


    //REQUIRES: calories of f has to be positive
    //MODIFIES: this
    //EFFECTS: subtract calories of f from total
    public void removeCal(Food f){
        total -= f.getCalories();
    }

    //EFFECTS: prints end statement
    public void exit(){
        System.out.println("Changes saved.");}

}