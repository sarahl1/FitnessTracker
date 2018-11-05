package model;

import exceptions.HighTotalException;
import exceptions.LowTotalException;
import exceptions.NoPreviousException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.Set;

import static java.lang.Integer.parseInt;

public class ItemLog implements CalorieCounter, Loadable{
    protected ItemList allFood;
    protected ItemList allExercise;

    protected int total;
    protected int max = 2500;

    public ItemLog(){
        allFood = new FoodList();
        allExercise = new ExerciseList();
    }

    //EFFECTS: returns allFood
    public ItemList getAllFood(){
        return allFood;
    }

    //EFFECTS: returns allExercise
    public ItemList getAllExercise(){
        return allExercise;
    }

    public int getTotal(){ return total; }

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
        return ("Calories until limit: " + (max - total));
    }

    //EFFECTS: prints a summary
    protected void printLog(ItemDone foodList, ItemDone exList){
        Food f = new Food (null, null, 0, false);
        Exercise e = new Exercise (null, null, 0);
        System.out.println("Summary: ");
        System.out.println("-FOOD-");
        for (Item i : foodList.getDone()){
//            System.out.println(f.id + " " + f.name + ": " + f.calories );
            System.out.println(f.summary(i));
        }
        System.out.println("-EXERCISE-");
        for (Item i : exList.getDone()){
//            System.out.println(e.id + " " + e.name + ": " + e.calories );
            System.out.println(e.summary(i));
        }
        System.out.println("Total calories: " + total + "\n" +
                "Calories until limit: " + (max - total));
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


    //REQUIRES: file is not empty
    //MODIFIES: this
    //EFFECTS: adds food from previous log to current list of food eaten
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
                }

            }
        }
    }

}
