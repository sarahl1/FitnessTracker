package model;

import observer.ItemDoneMonitor;
import observer.Observer;
import observer.Subject;

import java.util.ArrayList;

public abstract class ItemDone extends Subject{
    private ArrayList<Item> done;

    public ItemDone(){
        done = new ArrayList<>();
        Observer idm = new ItemDoneMonitor();
        this.addObserver(idm);
    }

    public ArrayList<Item> getDone(){
        return this.done;
    }

    abstract protected void addDone(Item i);

    abstract protected void removeDone(Item i);
}
