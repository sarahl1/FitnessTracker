package observer;

import model.Item;

public class ItemDoneMonitor implements Observer{
    private int numItems = 0;

    @Override
    public String update(Item i) {
        numItems++;
        return ("You have added " + i.getName() + ". \n There are " + numItems + " item(s) logged today.");
    }

    @Override
    public String updateRemove(Item i) {
        numItems--;
        return("You have removed " + i.getName() + ". \n There are " + numItems + "item(s) logged today.");
    }
}
