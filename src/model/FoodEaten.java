package model;

import java.util.ArrayList;

public class FoodEaten extends ItemDone{

    public void addDone(Item i){
        this.getDone().add(i);
        i.setCompleted(this);
    }

    //MODIFIES: this
    //EFFECTS: if ItemList does not contain item, put the itemList and nutritional facts into the ItemList
    public void removeDone(Item i){
        if (this.getDone().contains(i)){
            this.getDone().remove(i);
            i.setCompleted(null);
        }
    }

}
