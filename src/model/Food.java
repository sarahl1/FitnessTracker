package model;


import java.io.*;
import java.util.*;


public class Food extends Item {
    private boolean healthy;

    public Food(String id, String name, int calories, boolean healthy){
        super(id, name, calories);
        this.healthy = healthy;
        list = null;
    }

    //MODIFIES: this
    //EFFECTS: sets foodEaten to done
    public void setCompleted(ItemDone done){
            this.complete = done;
    }

    //MODIFIES: THIS
    //EFFECTS: if the list is null, set list to foodList
    public void setList(ItemList foodList) {
        if (list == null) {
            list = foodList;
            foodList.addItem(this);
        }
    }

    @Override
    public boolean getHealthy(){ return healthy;}


    //EFFECTS: returns a log of il eaten for PrintWriter
    @Override
    protected String summary(Item i){
        return (i.id + " " + i.name + ": " + i.calories + " - " + i.getHealthy());
    }


    //REQUIRES: list is non-empty
    //EFFECTS: saves ID of il eaten to file
    @Override
    public void saveToPrevious(ItemDone list) throws IOException{
        List<String> lines = new ArrayList<>();
        FileWriter fw = new FileWriter("previous.txt", true);
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
        FileWriter file = new FileWriter("inputfile.txt", true);
        PrintWriter writeToInput = new PrintWriter(file);
        lines.add("");
        lines.add("Food: ");
        for (Item i : list.getDone()) {
            lines.add(summary(i));
        }
        lines.add(il.printTotal());
        lines.add(il.printRemaining());
        for (String line : lines) {

            writeToInput.println(line);

        }
        writeToInput.println("");
        writeToInput.close();
    }


}