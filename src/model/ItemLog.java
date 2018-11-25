package model;

import exceptions.*;
import observer.Observer;
import parsers.JSONRead;
import parsers.ReadWebPage;
import ui.MainMenu;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;

import static java.lang.Integer.parseInt;
import static ui.Main.options;

public class ItemLog implements CalorieCounter, Loadable{
    protected ItemList allFood;
    protected ItemList allExercise;
    protected ItemDone foodEaten;
    protected ItemDone exerciseDone;
    protected Scanner scan = new Scanner(System.in);
    protected boolean found;
    protected String toRemove;
    protected ArrayList<Item> listToRemove;

    private JSONRead jsonReader;

    protected int total;
    protected int MAX_TOTAL = 2500;

    public ItemLog(){
    }

    //EFFECTS: getters
    public ItemList getAllFood(){ return jsonReader.getAllFood(); }
    public ItemList getAllExercise(){
        return allExercise;
    }
    public int getTotal(){ return total; }
    public ArrayList<Item> getListToRemove(){return listToRemove;}
    public String getToRemove(){ return toRemove; }
    public ItemDone getFoodEaten(){ return foodEaten;}
    public ItemDone getExerciseDone(){ return exerciseDone;}

    //MODIFIES: this
    //EFFECTS: instantiates Items with id, name, and calories -- then adds them to a list of all items
    public void makeItems() throws IOException {
        allFood = new FoodList();
        allExercise = new ExerciseList();
        ReadWebPage recipe = new ReadWebPage();
        jsonReader = new JSONRead();
        foodEaten = new FoodEaten();
        exerciseDone = new ExerciseDone();
        Item jog = new Exercise("0011", "Jog", -100);
        Item run = new Exercise("0021", "Run", -120);
        Item swim = new Exercise("1001", "Swim", -300);
        Item cardio1 = new Exercise("1002", "Cardio (20 mins)", -100);
        Item strength1 = new Exercise("1002", "Strength Training", -100);
        Item basketball = new Exercise("1011", "Basketball", -100);
        Item hockey = new Exercise("1021", "Hockey", -200);
        Item pilates = new Exercise("1031", "Pilates", -180);
        Item yoga = new Exercise("1041", "Yoga", -180);
        cardio1.setList(allExercise);
        strength1.setList(allExercise);
        jog.setList(allExercise);
        run.setList(allExercise);
        swim.setList(allExercise);
        basketball.setList(allExercise);
        hockey.setList(allExercise);
        pilates.setList(allExercise);
        yoga.setList(allExercise);
        allFood = jsonReader.getAllFood();

    }
//TODO: write new item to JSON!!!!!!!!!!!
    //MODIFIES: this
    //EFFECTS: gives add item options and takes user input
    //         throws NotAnOptionException if user enters an invalid input
    public void optionAdd() throws NotAnOptionException, NotAnItemException {
        System.out.println("Add [1] Exercise or [2] Food?");
        System.out.println("[3] to view nutritional facts.");
        System.out.println("[4] to add a custom item.");
        String choice = scan.nextLine();
        if (choice.equals("1")) {
            itemOptions(allExercise, exerciseDone);
        } else if (choice.equals("2")) {
            searchOption();
        } else if (choice.equals("3")){
            itemNutritionalOptions(allFood, allExercise);
        } else if (choice.equals("4")){
            try { createItem();
            } catch (NotAnOptionException e) {
                e.getMessage();
            } finally {
                options();
            }
        } else { throw new NotAnOptionException("Not an option!");
        }
    }

    //MODIFIES: this
    //EFFECTS: prints log and removes item
    public void optionRemove() throws NotAnItemException{
        printLog(foodEaten, exerciseDone);
        scanRemove();
        createRemoveList();
        //removeItem(toRemove, foodEaten, exerciseDone, listToRemove);
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
        setTotal();
        setDone(allExercise, exerciseDone);
        setDone(allFood, foodEaten);
    }

    //EFFECTS: prints log
    public void optionExit(){
        printLog(foodEaten, exerciseDone);
    }

    //EFFECTS: calls printItem on the lists of all food and all exercise
    protected void itemNutritionalOptions(ItemList food, ItemList exercise){
        printItem(food);
        printItem(exercise);
        selectView();
    }

    //EFFECTS: allows user to select item to view nutritional facts of
    protected void selectView(){
        System.out.println("Please enter the ID of the item which you are interested in.");
        String num = scan.nextLine();
        Set<Item> foodMap = allFood.getLog().keySet();
        printNutritionHelper(foodMap, num);
        Set<Item> exMap = allExercise.getLog().keySet();
        printNutritionHelper(exMap, num);
    }

    //EFFECTS: sets the item's nutritional facts and prints the nutritional facts
    private void printNutritionHelper(Set<Item> map, String num){
        for (Item i : map){
            if(i.id.equals(num)){
                i.nutriFacts.setNutriFacts(i);
                i.printNutrition();
            }
        }
    }

    //EFFECTS: prints a list of all il with id, name, and calories
    protected void printItem(ItemList allItems){
        Food f = new Food(null, null, 0, false);
        Exercise e = new Exercise (null, null, 0);
        System.out.printf("%-5s %-40s %-7s %-5s \n", "ID", "Name", "KCals", "Healthy?");
        Set<Item> mapItem = allItems.getLog().keySet();
        for (Item i : mapItem){
            if (i.getClass() == Food.class)
                System.out.println(f.summary(i));
            else if (i.getClass() == Exercise.class)
                System.out.println(e.summary(i));
        }
    }

    //EFFECTS: prompts user to search for an item,
    //         throws NotAnItemException if item is not found
    private void searchOption() throws NotAnItemException {
        System.out.println("Enter food keyword:");
        String input = scan.nextLine();
        search(input);
    }

    //EFFECTS: searches allFood for the food name matching input,
    //         throws NotAnItemException if item is not found
    public ItemList search(String input) throws NotAnItemException {
        ItemList hasString = new FoodList();
        for (Item i : allFood.getLog().keySet()){
            if (i.name.toLowerCase().contains(input.toLowerCase())){
                hasString.addItem(i);
            }
        }
        //itemOptions(hasString , foodEaten);
        return hasString;
    }


    //MODIFIES: this
    //EFFECTS: adds il to testFood -- adds calories of the il to total
    public void addItem(Item f, ItemDone list){
        list.addDone(f);
        try {
            addCal(f);
        } catch (HighTotalException e) {
            MainMenu.exception.setText(e.getMessage());
            MainMenu.window.show();
        } catch (LowTotalException e) {
            MainMenu.exception.setText(e.getMessage());
            MainMenu.window.show();
        }
    }

    //EFFECTS: prompts the user to give information about their new item
    //         throws NotAnOptionException if they do not choose to create food or exercise
    private void createItem() throws NotAnOptionException{
        System.out.println("Is it [1] food or [2] exercise? ");
        String option = scan.nextLine();
        System.out.println("Item ID:");
        String id = scan.nextLine();
        System.out.println("Item name:");
        String name = scan.nextLine();
        System.out.println("Number of calories:");
        String cal = scan.nextLine();
        System.out.println("Is it healthy? (true or false)");
        String health = scan.nextLine();

        if (option.equals("1")){
            createType(id, name, cal, health, "food");
        } else if (option.equals("2")){
            createType(id, name, cal, health,"exercise");
        } else {
            throw new NotAnOptionException("Not an option!");
        }
    }

    //EFFECTS: creates the item using information from createItem and adds it to the ItemDone list,
    //         and sets nutritional facts
    private void createType(String id, String name, String cal, String health, String type){
        if (type == "food"){
            Item newItem = new Food(id, name, parseInt(cal), Boolean.parseBoolean(health));
            addItem(newItem, foodEaten);
            newItem.nutriFacts.setNutriFacts(newItem);
            newItem.list.addItem(newItem);
        }
        else if (type == "exercise"){
            Item newItem = new Exercise(id, name, parseInt(cal));
            addItem(newItem, exerciseDone);
            newItem.nutriFacts.setNutriFacts(newItem);
            newItem.list.addItem(newItem);
        }

    }




    //MODIFIES: this
    //EFFECTS: takes user input and calls find il on the ID input
    private void selectItem(ItemList hs, ItemDone done) throws NotAnItemException{
        System.out.println("Please enter the ID of the item you want to add.");
        String num = scan.nextLine();
        findItem(num, hs, done);
        if (!found){
            throw new NotAnItemException("Item not found!");
        }
    }

    //MODIFIES: this
    //EFFECTS: looks for selected item in list of all items and calls addItem if it is found
    private void findItem(String i, ItemList hs, ItemDone done){
        found = false;
        Set<Item> itemMap = hs.getLog().keySet();
        for (Item f : itemMap){
            if (f.getId().equals(i)){
                found = true;
                addItem(f, done);
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: displays options for the subclass
    protected void itemOptions(ItemList hs, ItemDone list) throws NotAnItemException{
        printItem(hs);
        selectItem(hs, list);
    }

    //MODIFIES: this
    //EFFECTS: takes user input and sets it to toRemove, instantiates listToRemove
    private void scanRemove(){
        System.out.println("Enter ID of item to remove.");
        toRemove = scan.nextLine();
    }


    //MODIFIES: this
    //EFFECTS: takes user input and adds the il with input id to listToRemove
    public void removeItem(ItemDone list, Item i){
//        found = false;
//        helpRemove(toRemove, fooddone, remove);
//
//        helpRemove(toRemove, exdone, remove);
//
//        removeFromRemove(fooddone, exdone);
        list.removeDone(i);
        try {
            removeCal(i);
        } catch (HighTotalException e) {
            MainMenu.exception.setText(e.getMessage());
            MainMenu.window.show();
        } catch (LowTotalException e) {
            MainMenu.exception.setText(e.getMessage());
            MainMenu.window.show();
        }
    }

    private void helpRemove(String toRemove, ItemDone done, ArrayList<Item> remove){
        for (Item i : done.getDone()){
            if (i.id.equals(toRemove) && !remove.contains(i)) {
                found = true;
                remove.add(i);
            }
        }
    }


    //EFFECTS: instantiates a list which will hold items to remove
    public void createRemoveList(){
        listToRemove = new ArrayList<>();
    }

    //REQUIRES: listToRemove and food_eaten are not empty
    //MODIFIES: this
    //EFFECTS: remove items from food_eaten that are
    public void removeFromRemove(ItemDone il, ItemDone il2){
        for (Item f : listToRemove) {
            il.removeDone(f);
            il2.removeDone(f);
            try {
                this.removeCal(f);
            } catch (LowTotalException e) {
                System.out.println(e.getMessage());

            } catch (HighTotalException e) {
                System.out.println(e.getMessage());
            }
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


    //REQUIRES: total is non-negative
    //MODIFIES: this
    //EFFECTS: add calories of f to total
    public void addCal(Item i) throws HighTotalException, LowTotalException {
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
    public void removeCal(Item i) throws LowTotalException, HighTotalException {
        this.total -= i.getCalories();
        if (this.total <= -100) {
            throw new LowTotalException("Warning: Low calorie intake. ");
        }
        if (this.total >= 3000) {
            throw new HighTotalException("Warning: High calorie intake. ");
        }
    }

    //EFFECTS: prints total calories
    protected String printTotal() {
        return ("Total calories: " + total);
    }

    //EFFECTS: prints calories until limit
    protected String printRemaining(){
        return ("Calories until limit: " + (MAX_TOTAL - total));
    }

    //EFFECTS: prints a summary
    protected void printLog(ItemDone foodList, ItemDone exList){
        Food f = new Food (null, null, 0, false);
        Exercise e = new Exercise (null, null, 0);
        System.out.println("Summary: ");
        System.out.println("-FOOD-");
        System.out.printf("%-5s %-40s %-7s %-5s \n", "ID", "Name", "KCals", "Healthy?");
        for (Item i : foodList.getDone()){
            System.out.println(f.summary(i));
        }
        System.out.println("-EXERCISE-");
        System.out.printf("%-5s %-40s %-7s \n", "ID", "Name", "KCals");
        for (Item i : exList.getDone()){
            System.out.println(e.summary(i));
        }
        System.out.println("Total calories: " + total + "\n" +
                "Calories until limit: " + (MAX_TOTAL - total));
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
        Double newTotal = Double.parseDouble(line);
        int intTotal = (int) Math.round(newTotal);
        total += intTotal;
    }


    //REQUIRES: file is not empty
    //MODIFIES: this
    //EFFECTS: adds il from previous log to current list of il eaten
    @Override
    public void setDone(ItemList all, ItemDone done) throws IOException, NoPreviousException {
        BufferedReader reader = new BufferedReader(new FileReader("previous.txt"));
        if (reader.readLine() == null){
            throw new NoPreviousException("No previous log found.");
        }
        int lines = 0;
        while (reader.readLine() != null) lines++;
        reader.close();
        for (int n = 0; n <= lines; n++) {
            String line = Files.readAllLines(Paths.get("previous.txt")).get(n);
            Set<Item> itemMap = all.getLog().keySet();
            for (Item f : itemMap) {
                if (line.equals(f.getId())) {
                    done.addDone(f);
                    MainMenu.updateAdd(this , f);
                }

            }
        }
    }


}

