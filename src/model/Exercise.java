package model;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date;


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

    //MODIFIES: this
    //EFFECTS: sets exerciseDone to done
    public void setCompleted(ItemDone done){
            this.complete = done;
    }

    //MODIFIES: THIS
    //EFFECTS: if the list is null, set list to exList
    @Override
    public void setList(ItemList exList) {
        if (list == null) {
            list = exList;
            exList.addItem(this);
        }
    }


    //EFFECTS: returns a summary of the item with id, name, and calories
    @Override
    protected String summary(Item i){
        return String.format("%-5s %-20.19s %-5s", i.id,  i.name, Double.toString(i.calories));

    }



    //REQUIRES: il eaten is non-empty
    //EFFECTS: saves ID of il eaten to file
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



    //REQUIRES: il eaten is non-empty
    //EFFECTS: saves log to file
    @Override
    public void saveToInput(ItemDone list) throws IOException {
        List<String> lines = new ArrayList<>();
        FileWriter file = new FileWriter("inputfile.txt", false);
        PrintWriter writeToInput = new PrintWriter(file);
        lines.add(il.date());
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
