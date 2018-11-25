package observer;

import model.Item;

public class ItemDoneMonitor implements Observer{
    private int numItems = 0;

    public int getNumItems(){
        return numItems;
    }

    @Override
    public String update(Item i) {
        numItems++;
        return ("You have added " + i.getName() + ". \n There are " + numItems + " item(s) logged today.");
    }

    @Override
    public String updateRemove(Item i) {
        numItems--;
        if (numItems == 0){
            return ("You have removed " + i.getName() + ". \n There are " + 0 + " item(s) logged today.");
        }
        return("You have removed " + i.getName() + ". \n There are " + numItems + " item(s) logged today.");
    }
}
