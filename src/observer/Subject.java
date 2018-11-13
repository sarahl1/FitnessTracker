package observer;

import model.Item;

import java.util.ArrayList;
import java.util.List;

public class Subject {

    private List<Observer> observers = new ArrayList<>();

    public void addObserver(Observer observer){
        if (!observers.contains(observer))
            observers.add(observer);
    }

    public void notifyObservers(Item item){
        for (Observer o : observers)
            o.update(item);
    }
}
