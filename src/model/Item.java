package model;

import exceptions.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public abstract class Item implements CalorieCounter, Loadable, Saveable{
    protected String id;
    protected String name;
    protected int calories;
    protected int max = 2500;
    protected int total;
    protected Scanner scan = new Scanner(System.in);
    protected boolean found;


    protected String toRemove;
    protected ArrayList<Item> listToRemove;

    protected ArrayList<Item> foodEaten;
    protected ArrayList<Item> exerciseDone;
    protected HashSet<Item> allFood;
    protected HashSet<Item> allExercise;

    public Item(String id, String name, int calories) {
        this.id = id;
        this.name = name;
        this.calories = calories;
    }

    //getters
    public int getCalories(){ return calories;}
    public String getId(){ return id;}
    public String getName(){ return name;}
    public int getTotal() { return total;}
    public ArrayList<Item> getExerciseDone(){ return this.exerciseDone; }
    public HashSet<Item> getAllExercise(){return this.allExercise;}
    public ArrayList<Item> getFoodEaten(){ return this.foodEaten; }
    public HashSet<Item> getAllFood(){return this.allFood;}
    public ArrayList<Item> getListToRemove(){ return listToRemove;}
    public String getToRemove(){ return toRemove;}
    public abstract boolean getHealthy();

    //MODIFIES: this
    //EFFECTS: instantiates Items with id, name, and calories -- then adds them to a list of all items
    public void makeItems(){
        foodEaten = new ArrayList<>();
        exerciseDone = new ArrayList<>();
        allFood = new HashSet<>();
        allExercise = new HashSet<>();
        Item jog = new Exercise("0011", "Jog", -100);
        Item run = new Exercise("0021", "Run", -120);
        Item swim = new Exercise("1001", "Swim", -350);
        Item basketball = new Exercise("1011", "Basketball", -100);
        Item hockey = new Exercise("1021", "Hockey", -200);
        Item pilates = new Exercise("1031", "Pilates", -180);
        Item yoga = new Exercise("1041", "Yoga", -180);
        allExercise.add(jog);
        allExercise.add(run);
        allExercise.add(swim);
        allExercise.add(basketball);
        allExercise.add(hockey);
        allExercise.add(pilates);
        allExercise.add(yoga);
        Item apple = new Food("001", "Apple", 100, true);
        Item orange = new Food("002", "Orange", 120, true);
        Item celery = new Food("003", "Celery", 60, true);
        Item hamburger = new Food("100", "Hamburger", 350, false);
        Item cheeseburger = new Food("101", "Cheeseburger", 100, false);
        Item p_pizza = new Food("102", "Pepperoni Pizza", 200, false);
        Item h_pizza = new Food("103", "Hawaiian Pizza", 180, false);
        Item v_pizza = new Food("104", "Veggie Pizza", 180, false);
        allFood.add(apple);
        allFood.add(orange);
        allFood.add(celery);
        allFood.add(hamburger);
        allFood.add(cheeseburger);
        allFood.add(p_pizza);
        allFood.add(h_pizza);
        allFood.add(v_pizza);
    }

    //MODIFIES: this
    //EFFECTS: gives add item options and takes user input
    public void optionAdd() throws NotAnOptionException, NotAnItemException{
        System.out.println("Add [1] Exercise or [2] Food?");
        String choice = scan.nextLine();
        if (choice.equals("1")){
            itemOptions(getAllExercise(), getExerciseDone());
        } else if (choice.equals("2")){
            itemOptions(getAllFood(), getFoodEaten());
        } else {
            throw new NotAnOptionException("Not an option!");
        }
    }

    //MODIFIES: this
    //EFFECTS: prints log and removes item
    public void optionRemove() throws NotAnItemException{
        printLog(getFoodEaten(), getExerciseDone());
        scanRemove();
        createRemoveList();
        removeItem(getToRemove(), getFoodEaten(), getExerciseDone(), getListToRemove());
        if (!found){
            throw new NotAnItemException("Not an item!");
        }
    }


    //EFFECTS: prints previous log
    //         throws IOException if file is not found
    public void optionViewPrevious() throws IOException, NoPreviousException {
        viewPrevious();
    }

    //REQUIRES: file is not empty
    //MODIFIES: this
    //EFFECTS: resumes previous log and prints log
    public void optionResume() throws IOException, NoPreviousException {
        setDone(getAllExercise(), getExerciseDone());
        setDone(getAllFood(), getFoodEaten());
        setTotal();
        printLog(getFoodEaten(), getExerciseDone());
    }

    //EFFECTS: prints log
    public void optionExit(){
        printLog(getFoodEaten(), getExerciseDone());
    }

    //MODIFIES: this
    //EFFECTS: displays options for the subclass
    protected void itemOptions(HashSet<Item> hs, ArrayList<Item> list) throws NotAnItemException{
        printItem(hs);
        selectItem(hs, list);
    }

    //EFFECTS: prints a list of all food with id, name, and calories
    protected void printItem(HashSet<Item> allItems){
        Food f = new Food(null, null, 0, false);
        Exercise e = new Exercise (null, null, 0);
        for (Item i : allItems){
            if (i.getClass() == Food.class)
//            System.out.println(f.id + " " + f.name + ": " + f.calories + "\n");
                System.out.println(f.summary(i));
            else if (i.getClass() == Exercise.class)
                System.out.println(e.summary(i));
        }
    }

    //MODIFIES: this
    //EFFECTS: adds food to testFood -- adds calories of the food to total
    public void addItem(Item f, ArrayList<Item> list){
        list.add(f);
        try {
            addCal(f);
        } catch (HighTotalException e) {
            System.out.println(e.getMessage());
        } catch (LowTotalException e) {
            System.out.println(e.getMessage());
        }
    }

    //MODIFIES: this
    //EFFECTS: takes user input and calls find food on the ID input
    private void selectItem(HashSet<Item> hs, ArrayList<Item> done) throws NotAnItemException{
        System.out.println("Please enter the ID of the item you want to add.");
        String num = scan.nextLine();
        findItem(num, hs, done);
        if (!found){
            throw new NotAnItemException("Item not found!");
        }
    }

    //MODIFIES: this
    //EFFECTS: looks for selected food in list of all food and calls addItem if it is found
    private void findItem(String i, HashSet<Item> hs, ArrayList<Item> done){
        found = false;
        for (Item f : hs){
            if (f.id.equals(i)){
                found = true;
                addItem(f, done);
            }
        }
    }


    //EFFECTS: prints a summary
    private void printLog(ArrayList<Item> foodList, ArrayList<Item> exList){
        Food f = new Food (null, null, 0, false);
        Exercise e = new Exercise (null, null, 0);
        System.out.println("Summary: ");
        System.out.println("-FOOD-");
        for (Item i : foodList){
//            System.out.println(f.id + " " + f.name + ": " + f.calories );
            System.out.println(f.summary(i));
        }
        System.out.println("-EXERCISE-");
        for (Item i : exList){
//            System.out.println(e.id + " " + e.name + ": " + e.calories );
            System.out.println(e.summary(i));
        }
        System.out.println("Total calories: " + total + "\n" +
                "Calories until limit: " + (max - total));
    }

    //MODIFIES: this
    //EFFECTS: takes user input and sets it to toRemove, instantiates listToRemove
    private void scanRemove(){
        System.out.println("Enter ID of item to remove.");
        toRemove = scan.nextLine();
    }


    //MODIFIES: this
    //EFFECTS: takes user input and adds the food with input id to listToRemove
    public void removeItem(String toRemove, ArrayList<Item> fooddone, ArrayList<Item> exdone, ArrayList<Item> remove){
        found = false;
        for (Item f : fooddone) {
            if (f.id.equals(toRemove)) {
                found = true;
                remove.add(f);
            }
        }
        for (Item e : exdone){
            if (e.id.equals(toRemove)){
                found = true;
                remove.add(e);
            }
        }
        removeFromRemove(fooddone, exdone);
    }

    //EFFECTS: instantiates a list which will hold items to remove
    public void createRemoveList(){
        listToRemove = new ArrayList<>();
    }

    //REQUIRES: listToRemove and food_eaten are not empty
    //MODIFIES: this
    //EFFECTS: remove items from food_eaten that are
    public void removeFromRemove(ArrayList<Item> il, ArrayList<Item> il2){
        for (Item f : listToRemove) {
            il.remove(f);
            il2.remove(f);
            try {
                removeCal(f);
            } catch (LowTotalException e) {
                System.out.println(e.getMessage());
            } catch (HighTotalException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    //REQUIRES: total is non-negative
    //MODIFIES: this
    //EFFECTS: add calories of f to total
    public void addCal(CalorieCounter i) throws HighTotalException, LowTotalException {
        this.total += i.getCalories();
        if (this.total >= 3000) {
            throw new HighTotalException("Warning: High calorie intake. ");
        }
        if (this.total <= -100) {
            throw new LowTotalException("Warning: Low calorie intake. ");
        }
    }

    //REQUIRES: total is non-negative
    //MODIFIES: this
    //EFFECTS: subtract calories of f from total
    public void removeCal(CalorieCounter i) throws LowTotalException, HighTotalException {
        this.total -= i.getCalories();
        if (this.total <= -100) {
            throw new LowTotalException("Warning: Low calorie intake. ");
        }
        if (this.total >= 3000) {
            throw new HighTotalException("Warning: High calorie intake. ");
        }
    }

    //EFFECTS: prints end statement
    public void exit(){
        System.out.println("Changes saved.");}

    //EFFECTS: returns the date with format yyyy/MM/dd
    protected String date() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate localDate = LocalDate.now();
        return dtf.format(localDate); //2016/11/16
    }

    abstract protected String summary(Item i);

    //EFFECTS: prints total calories
    protected String printTotal() {
        return ("Total calories: " + total);
    }

    //EFFECTS: prints calories until limit
    protected String printRemaining(){
        return ("Calories until limit: " + (max - total));
    }


    //REQUIRES: there exists a previous log
    //EFFECTS: prints the previous log
    @Override
    public void viewPrevious() throws IOException, NoPreviousException {
        Scanner inFile = new Scanner(new FileReader("inputfile.txt"));
        String sFile = "";

        if (!(inFile.hasNextLine())){
            throw new NoPreviousException("No previous log!");
        }

        while(inFile.hasNextLine())
            sFile = sFile + inFile.nextLine() + "\n";

        // prints to console
        System.out.println("Previous Log: ");
        System.out.println(sFile);
    }

    //MODIFIES: this
    //EFFECTS: adds previous total to current total
    @Override
    public void setTotal() throws IOException, NoPreviousException {
        BufferedReader reader = new BufferedReader(new FileReader("previousTOTAL.txt"));
        if (reader.readLine() == null){
            throw new NoPreviousException("No previous total found.");
        }
        String line = Files.readAllLines(Paths.get("previousTOTAL.txt")).get(0);
        total += parseInt(line);
    }

    //EFFECTS: saves total to file
    @Override
    public void saveToPreviousTotal() throws IOException{
        FileWriter fw = new FileWriter("previousTOTAL.txt", false);
        PrintWriter writer = new PrintWriter(fw);
        writer.println(Integer.toString(getTotal()));
        writer.close();
    }

    //REQUIRES: file is not empty
    //MODIFIES: this
    //EFFECTS: adds food from previous log to current list of food eaten
    @Override
    public void setDone(HashSet<Item> all, ArrayList<Item> done) throws IOException, NoPreviousException {
        BufferedReader reader = new BufferedReader(new FileReader("previous.txt"));
        if (reader.readLine() == null){
            throw new NoPreviousException("No previous log found.");
        }
        int lines = 0;
        while (reader.readLine() != null) lines++;
        reader.close();
        for (int n = 0; n < lines; n++) {
            String line = Files.readAllLines(Paths.get("previous.txt")).get(n);
            for (Item f : all) {
                if (line.equals(f.getId())) {
                    done.add(f);
                }

            }
        }
    }

}