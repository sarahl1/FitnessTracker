package model;

import exceptions.*;
import observer.Observer;

import java.io.*;
import java.util.Objects;

public abstract class Item implements Saveable {
    protected String id;
    protected String name;
    protected int calories;
//    protected String portion;


    public ItemLog il;

    protected ItemDone complete;

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
    public abstract boolean getHealthy();

    abstract public void setList(ItemList itemList);
    abstract public void setCompleted(ItemDone done);

    public void setNutriFacts(Nutrition n){
        this.nutriFacts = n;
    }

    public int getTotal() { return il.getTotal();}
    public ItemDone getFoodEaten(){ return il.foodEaten;}
    public ItemDone getExerciseDone(){ return il.exerciseDone;}


    //MODIFIES: this
    //EFFECTS: instantiates Items with id, name, and calories -- then adds them to a list of all items
    public void makeItems() throws IOException {
        il.makeItems();
    }

    //MODIFIES: this
    //EFFECTS: gives add item options and takes user input
    public void optionAdd() throws NotAnOptionException, NotAnItemException {
        il.optionAdd();
    }

    //MODIFIES: this
    //EFFECTS: prints log and removes item
    public void optionRemove() throws NotAnItemException{
        il.optionRemove();
    }


    //EFFECTS: prints previous log
    //         throws IOException if file is not found
    public void optionViewPrevious() throws IOException, NoPreviousException {
        il.optionViewPrevious();
    }

    //REQUIRES: file is not empty
    //MODIFIES: this
    //EFFECTS: resumes previous log and prints log
    public void optionResume() throws IOException, NoPreviousException {
        il.optionResume();
    }

    //EFFECTS: prints log
    public void optionExit(){
        il.optionExit();
    }

    //EFFECTS: prints nutritional facts of the item
    protected void printNutrition(){
        for (String s : this.nutriFacts.getNutriFacts()){
            System.out.println(s);
        }
    }

    //EFFECTS: prints end statement
    public void exit(){
        il.exit();}

    //EFFECTS: prints a summary of the item with id, name, calories, and healthy?
    protected String summary(Item i){ return i.summary(i);};


    //EFFECTS: saves total to file
    @Override
    public void saveToPreviousTotal() throws IOException{
        FileWriter fw = new FileWriter("previousTOTAL.txt", false);
        PrintWriter writer = new PrintWriter(fw);
        writer.println(Double.toString(getTotal()));
        writer.close();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(id, item.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}