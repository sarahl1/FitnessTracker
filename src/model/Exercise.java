package model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class Exercise extends Item {

    public Exercise(String id, String name, int calories){
        super(id, name, calories);
        list = null;
    }

    //getter
    @Override
    public boolean getHealthy() {
        return true;
    }

    public void setCompleted(ItemDone done){
            this.exerciseDone = done;
    }

    @Override
    public void setList(ItemList exList) {
        if (list == null) {
            list = exList;
            exList.addItem(this);
        }
    }


    //EFFECTS: returns a log of food eaten for PrintWriter
    @Override
    protected String summary(Item i){
        return (i.id + " " + i.name + ": " + i.calories);
    }



    //REQUIRES: food eaten is non-empty
    //EFFECTS: saves ID of food eaten to file
    @Override
    public void saveToPrevious(ItemDone list) throws IOException{
        List<String> lines = new ArrayList<>();
        FileWriter fw = new FileWriter("previous.txt", false);
        PrintWriter writer = new PrintWriter(fw);
        for (Item i : list.getDone()) {
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
    public void saveToInput(ItemDone list) throws IOException {
        List<String> lines = new ArrayList<>();
        FileWriter file = new FileWriter("inputfile.txt", false);
        PrintWriter writeToInput = new PrintWriter(file);
        lines.add(date());
        lines.add("");
        lines.add("Exercise: ");
        for (Item i : list.getDone()) { //change summary parameter to Food f
            lines.add(summary(i));
        }
        for (String line : lines) {

            writeToInput.println(line);

        }
        writeToInput.println("");
        writeToInput.close();
    }






}
