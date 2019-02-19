package model;

import observer.ItemDoneMonitor;
import observer.Observer;
import observer.Subject;

import java.util.HashSet;
import java.util.Iterator;

public abstract class ItemDone extends Subject{
    private HashSet<Item> done;

    public ItemDone(){
        done = new HashSet<>();
        Observer idm = new ItemDoneMonitor();
        this.addObserver(idm);
    }

    public HashSet<Item> getDone(){
        return this.done;
    }

    abstract public void addDone(Item i);

    abstract public void removeDone(Item i);


}
