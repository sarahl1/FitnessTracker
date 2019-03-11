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

    public void notifyObservers(Item item, String action){
        if (action == "add") {
            for (Observer o : observers)
                o.update(item);
        } else if (action == "remove") {
            for (Observer o : observers)
                o.updateRemove(item);
        }
    }
}
