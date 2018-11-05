package model;

import exceptions.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;
import static ui.Main.options;

public abstract class Item implements Saveable{
    protected String id;
    protected String name;
    protected int calories;

    public ItemLog il;

    protected Scanner scan = new Scanner(System.in);
    protected boolean found;


    protected String toRemove;
    protected ArrayList<Item> listToRemove;

    protected ItemDone foodEaten;
    protected ItemDone exerciseDone;
    protected Nutrition nutriFacts;
    protected ItemList list;

    public Item(String id, String name, int calories) {
        this.id = id;
        this.name = name;
        this.calories = calories;
        nutriFacts = new Nutrition();
        il = new ItemLog();
    }

    //getters
    public int getCalories(){ return calories;}
    public String getId(){ return id;}
    public String getName(){ return name;}
    public int getTotal() { return il.getTotal();}
    public String getToRemove(){ return toRemove;}
    public abstract boolean getHealthy();
    public ArrayList<Item> getListToRemove(){return listToRemove;}
    abstract public void setList(ItemList itemList);
    abstract public void setCompleted(ItemDone done);
    public void setNutriFacts(Nutrition n){
        this.nutriFacts = n;
    }
    public ItemDone getFoodEaten(){ return this.foodEaten;}
    public ItemDone getExerciseDone(){ return this.exerciseDone;}


    //MODIFIES: this
    //EFFECTS: instantiates Items with id, name, and calories -- then adds them to a list of all items
    public void makeItems(){
        foodEaten = new FoodEaten();
        exerciseDone = new ExerciseDone();
        Item jog = new Exercise("0011", "Jog", -100);
        Item run = new Exercise("0021", "Run", -120);
        Item swim = new Exercise("1001", "Swim", -350);
        Item basketball = new Exercise("1011", "Basketball", -100);
        Item hockey = new Exercise("1021", "Hockey", -200);
        Item pilates = new Exercise("1031", "Pilates", -180);
        Item yoga = new Exercise("1041", "Yoga", -180);
        Item apple = new Food("001", "Apple", 100, true);
        Item orange = new Food("002", "Orange", 120, true);
        Item celery = new Food("003", "Celery", 60, true);
        Item hamburger = new Food("100", "Hamburger", 350, false);
        Item cheeseburger = new Food("101", "Cheeseburger", 100, false);
        Item p_pizza = new Food("102", "Pepperoni Pizza", 200, false);
        Item h_pizza = new Food("103", "Hawaiian Pizza", 180, false);
        Item v_pizza = new Food("104", "Veggie Pizza", 180, false);
        apple.setList(il.getAllFood());
        orange.setList(il.getAllFood());
        celery.setList(il.getAllFood());
        hamburger.setList(il.getAllFood());
        cheeseburger.setList(il.getAllFood());
        p_pizza.setList(il.getAllFood());
        h_pizza.setList(il.getAllFood());
        v_pizza.setList(il.getAllFood());
        jog.setList(il.getAllExercise());
        run.setList(il.getAllExercise());
        swim.setList(il.getAllExercise());
        basketball.setList(il.getAllExercise());
        hockey.setList(il.getAllExercise());
        pilates.setList(il.getAllExercise());
        yoga.setList(il.getAllExercise());

    }

    //MODIFIES: this
    //EFFECTS: gives add item options and takes user input
    public void optionAdd() throws NotAnOptionException, NotAnItemException {
        System.out.println("Add [1] Exercise or [2] Food?");
        System.out.println("[3] to view nutritional facts.");
        System.out.println("[4] to add a custom item.");
        String choice = scan.nextLine();
        if (choice.equals("1")) {
            itemOptions(il.getAllExercise(), exerciseDone);
        } else if (choice.equals("2")) {
            itemOptions(il.getAllFood(), foodEaten);
        } else if (choice.equals("3")){
            itemNutritionalOptions(il.getAllFood(), il.getAllExercise());
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
        il.printLog(foodEaten, exerciseDone);
        scanRemove();
        createRemoveList();
        removeItem(getToRemove(), foodEaten, exerciseDone, getListToRemove());
        if (!found){
            throw new NotAnItemException("Not an item!");
        }
    }


    //EFFECTS: prints previous log
    //         throws IOException if file is not found
    public void optionViewPrevious() throws IOException, NoPreviousException {
        il.viewPrevious();
    }

    //REQUIRES: file is not empty
    //MODIFIES: this
    //EFFECTS: resumes previous log and prints log
    public void optionResume() throws IOException, NoPreviousException {
        il.setDone(il.getAllExercise(), exerciseDone);
        il.setDone(il.getAllFood(), foodEaten);
        il.setTotal();
        il.printLog(foodEaten, exerciseDone);
    }

    //EFFECTS: prints log
    public void optionExit(){
        il.printLog(foodEaten, exerciseDone);
    }

    protected void itemNutritionalOptions(ItemList food, ItemList exercise){
        printItem(food);
        printItem(exercise);
        selectView();
    }//TODO: implement nutrition viewing

    protected void selectView(){
        ArrayList<String> details;
        System.out.println("Please enter the ID of the item which you are interested in.");
        String num = scan.nextLine();
        Set<Item> foodMap = il.getAllFood().getLog().keySet();
        for (Item i : foodMap){
            if (i.id.equals(num)){
                i.nutriFacts.setNutriFacts(i);
                printNutrition(i);
            }
        }
        Set<Item> exMap = il.getAllExercise().getLog().keySet();
        for (Item i : exMap){
            if(i.id.equals(num)){
                i.nutriFacts.setNutriFacts(i);
                printNutrition(i);
            }
        }
    }

    protected void printNutrition(Item i){
        for (String s : i.nutriFacts.getNutriFacts()){
            System.out.println(s);
        }
    }

    //MODIFIES: this
    //EFFECTS: displays options for the subclass
    protected void itemOptions(ItemList hs, ItemDone list) throws NotAnItemException{
        printItem(hs);
        selectItem(hs, list);
    }

    //EFFECTS: prints a list of all food with id, name, and calories
    protected void printItem(ItemList allItems){
        Food f = new Food(null, null, 0, false);
        Exercise e = new Exercise (null, null, 0);
        Set<Item> mapItem = allItems.getLog().keySet();
        for (Item i : mapItem){
            if (i.getClass() == Food.class)
                System.out.println(f.summary(i));
            else if (i.getClass() == Exercise.class)
                System.out.println(e.summary(i));
        }
    }

    //MODIFIES: this
    //EFFECTS: adds food to testFood -- adds calories of the food to total
    public void addItem(Item f, ItemDone list){
        list.addDone(f);
        try {
            il.addCal(f);
        } catch (HighTotalException e) {
            System.out.println(e.getMessage());
        } catch (LowTotalException e) {
            System.out.println(e.getMessage());
        }
    }

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
            Item newItem = new Food(id, name, parseInt(cal), Boolean.parseBoolean(health));
            addItem(newItem, foodEaten);
            newItem.nutriFacts.setNutriFacts(newItem);
            il.getAllFood().addItem(newItem);
        } else if (option.equals("2")){
            Item newItem = new Exercise(id, name, parseInt(cal));
            addItem(newItem, exerciseDone);
            newItem.nutriFacts.setNutriFacts(newItem);
            il.getAllExercise().addItem(newItem);
        } else {
            throw new NotAnOptionException("Not an option!");
        }
    }



    //MODIFIES: this
    //EFFECTS: takes user input and calls find food on the ID input
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


//    //EFFECTS: prints a summary
//    private void printLog(ItemDone foodList, ItemDone exList){
//        Food f = new Food (null, null, 0, false);
//        Exercise e = new Exercise (null, null, 0);
//        System.out.println("Summary: ");
//        System.out.println("-FOOD-");
//        for (Item i : foodList.getDone()){
////            System.out.println(f.id + " " + f.name + ": " + f.calories );
//            System.out.println(f.summary(i));
//        }
//        System.out.println("-EXERCISE-");
//        for (Item i : exList.getDone()){
////            System.out.println(e.id + " " + e.name + ": " + e.calories );
//            System.out.println(e.summary(i));
//        }
//        System.out.println("Total calories: " + il.getTotal() + "\n" +
//                "Calories until limit: " + (max - total));
//    }

    //MODIFIES: this
    //EFFECTS: takes user input and sets it to toRemove, instantiates listToRemove
    private void scanRemove(){
        System.out.println("Enter ID of item to remove.");
        toRemove = scan.nextLine();
    }


    //MODIFIES: this
    //EFFECTS: takes user input and adds the food with input id to listToRemove
    public void removeItem(String toRemove, ItemDone fooddone, ItemDone exdone, ArrayList<Item> remove){
        found = false;
        for (Item f : fooddone.getDone()) {
            if (f.id.equals(toRemove) && !remove.contains(f)) {
                found = true;
                remove.add(f);
                }
            }

        for (Item e : exdone.getDone()) {
                if (e.id.equals(toRemove) && !remove.contains(e)) {
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
    public void removeFromRemove(ItemDone il, ItemDone il2){
        for (Item f : listToRemove) {
            il.removeDone(f);
            il2.removeDone(f);
            try {
                this.il.removeCal(f);
            } catch (LowTotalException e) {
                System.out.println(e.getMessage());
            } catch (HighTotalException e) {
                System.out.println(e.getMessage());
            }
        }
    }

//    //REQUIRES: total is non-negative
//    //MODIFIES: this
//    //EFFECTS: add calories of f to total
//    public void addCal(CalorieCounter i) throws HighTotalException, LowTotalException {
//        il.getTotal() += i.getCalories();
//        if (this.total >= 3000) {
//            throw new HighTotalException("Warning: High calorie intake. ");
//        }
//        if (this.total <= -100) {
//            throw new LowTotalException("Warning: Low calorie intake. ");
//        }
//
//    }

//    //REQUIRES: total is non-negative
//    //MODIFIES: this
//    //EFFECTS: subtract calories of f from total
//    public void removeCal(CalorieCounter i) throws LowTotalException, HighTotalException {
//        this.total -= i.getCalories();
//        if (this.total <= -100) {
//            throw new LowTotalException("Warning: Low calorie intake. ");
//        }
//        if (this.total >= 3000) {
//            throw new HighTotalException("Warning: High calorie intake. ");
//        }
//    }

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

//    //EFFECTS: prints total calories
//    protected String printTotal() {
//        return il.printTotal();
//    }
//
//    //EFFECTS: prints calories until limit
//    protected String printRemaining(){
//        return il.printRemaining();
//    }
//
//
//    //REQUIRES: there exists a previous log
//    //EFFECTS: prints the previous log
//    @Override
//    public void viewPrevious() throws IOException, NoPreviousException {
//        Scanner inFile = new Scanner(new FileReader("inputfile.txt"));
//        String sFile = "";
//
//        if (!(inFile.hasNextLine())){
//            throw new NoPreviousException("No previous log!");
//        }
//
//        while(inFile.hasNextLine())
//            sFile = sFile + inFile.nextLine() + "\n";
//
//        // prints to console
//        System.out.println("Previous Log: ");
//        System.out.println(sFile);
//    }
//
//    //MODIFIES: this
//    //EFFECTS: adds previous total to current total
//    @Override
//    public void setTotal() throws IOException, NoPreviousException {
//        BufferedReader reader = new BufferedReader(new FileReader("previousTOTAL.txt"));
//        if (reader.readLine() == null){
//            throw new NoPreviousException("No previous total found.");
//        }
//        String line = Files.readAllLines(Paths.get("previousTOTAL.txt")).get(0);
//        total += parseInt(line);
//    }
//
    //EFFECTS: saves total to file
    @Override
    public void saveToPreviousTotal() throws IOException{
        FileWriter fw = new FileWriter("previousTOTAL.txt", false);
        PrintWriter writer = new PrintWriter(fw);
        writer.println(Integer.toString(getTotal()));
        writer.close();
    }
//
//    //REQUIRES: file is not empty
//    //MODIFIES: this
//    //EFFECTS: adds food from previous log to current list of food eaten
//    @Override
//    public void setDone(ItemList all, ItemDone done) throws IOException, NoPreviousException {
//        BufferedReader reader = new BufferedReader(new FileReader("previous.txt"));
//        if (reader.readLine() == null){
//            throw new NoPreviousException("No previous log found.");
//        }
//        int lines = 0;
//        while (reader.readLine() != null) lines++;
//        reader.close();
//        for (int n = 0; n <= lines; n++) {
//            String line = Files.readAllLines(Paths.get("previous.txt")).get(n);
//            Set<Item> itemMap = all.getLog().keySet();
//            for (Item f : itemMap) {
//                if (line.equals(f.getId())) {
//                    done.addDone(f);
//                }
//
//            }
//        }
//    }




}