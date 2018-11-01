package model;

import java.util.ArrayList;

public abstract class ItemDone {
    private ArrayList<Item> done;

    public ItemDone(){
        done = new ArrayList<>();
    }

    public ArrayList<Item> getDone(){
        return this.done;
    }

    abstract protected void addDone(Item i);

    abstract protected void removeDone(Item i);
}
