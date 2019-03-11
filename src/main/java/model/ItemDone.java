package model;

import observer.ItemDoneMonitor;
import observer.Observer;
import observer.Subject;

import java.util.HashSet;

public abstract class ItemDone extends Subject {
    private HashSet<Item> done;

    public ItemDone() {
        done = new HashSet<>();
        Observer idm = new ItemDoneMonitor();
        this.addObserver(idm);
    }

    //EFFECTS: returns the set of items
    public HashSet<Item> getDone() {
        return this.done;
    }

    //MODIFIES: this
    //EFFECTS: adds an item to list of items done
    abstract public void addDone(Item i);

    //MODIFIES: this
    //EFFECTS: removes an item from list of items done
    abstract public void removeDone(Item i);


}
