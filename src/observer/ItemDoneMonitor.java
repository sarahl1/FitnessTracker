package observer;

import model.Item;

public class ItemDoneMonitor implements Observer{
    private int numItems = 0;

    @Override
    public void update(Item i) {
        numItems++;
        System.out.println("You have added " + i.getName() + ". \n There are " + numItems + " item(s) logged today.");
    }
}
