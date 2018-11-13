package model;

import observer.Subject;

import java.util.ArrayList;

public abstract class ItemDone extends Subject{
    private ArrayList<Item> done;
    protected ItemLog il = new ItemLog();

    public ItemDone(){
        done = new ArrayList<>();
        this.addObserver(il);
    }

    public ArrayList<Item> getDone(){
        return this.done;
    }

    abstract protected void addDone(Item i);

    abstract protected void removeDone(Item i);
}
