package model;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class ItemList {
    private HashMap<Item, ArrayList<String>> log;

    public ItemList(){
        log = new HashMap<>();
    }

    public void setLog(HashMap<Item, ArrayList<String>> map){
        log = map;
    }

    public HashMap<Item, ArrayList<String>> getLog(){
        return log;
    }

    abstract public void addItem(Item i);




    //abstract public void removeItem(Item i);
}
