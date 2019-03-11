package model;


public class FoodEaten extends ItemDone {

    //MODIFIES: this
    //EFFECTS: adds an item to list of food eaten
    public void addDone(Item i) {
        this.getDone().add(i);
        i.setCompleted(this);
    }

    //MODIFIES: this
    //EFFECTS: removes an item from list of food eaten
    public void removeDone(Item i) {
        this.getDone().remove(i);
        i.setCompleted(new FoodEaten());
    }


}
