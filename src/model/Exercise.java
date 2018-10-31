package model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class Exercise extends Item {
    private ItemList list;

    public Exercise(String id, String name, int calories){
        super(id, name, calories);
        setList(this.getAllExercise());
    }

    //getter
    @Override
    public boolean getHealthy() {
        return true;
    }

    @Override
    public void setList(ItemList exerciseList) {
        list = exerciseList;
        exerciseList.addItem(this);
    }


    //EFFECTS: returns a log of food eaten for PrintWriter
    @Override
    protected String summary(Item i){
        return (i.id + " " + i.name + ": " + i.calories);
    }



    //REQUIRES: food eaten is non-empty
    //EFFECTS: saves ID of food eaten to file
    @Override
    public void saveToPrevious(ArrayList<Item> list) throws IOException{
        List<String> lines = new ArrayList<>();
        FileWriter fw = new FileWriter("previous.txt", false);
        PrintWriter writer = new PrintWriter(fw);
        for (Item i : list) {
            lines.add(i.getId());
        }
        for (String line : lines){
            writer.println(line);
        }
        writer.close();
    }



    //REQUIRES: food eaten is non-empty
    //EFFECTS: saves log to file
    @Override
    public void saveToInput(ArrayList<Item> list) throws IOException {
        List<String> lines = new ArrayList<>();
        FileWriter file = new FileWriter("inputfile.txt", false);
        PrintWriter writeToInput = new PrintWriter(file);
        lines.add(date());
        lines.add("");
        lines.add("Exercise: ");
        for (Item i : list) { //change summary parameter to Food f
            lines.add(summary(i));
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
