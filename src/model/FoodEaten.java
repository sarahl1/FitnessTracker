package model;


public class FoodEaten extends ItemDone{

    public void addDone(Item i){
            this.getDone().add(i);
            i.setCompleted(this);
            notifyObservers(i, "add");
    }

    public void removeDone(Item i){
            this.getDone().remove(i);
            i.setCompleted(new FoodEaten());
            notifyObservers(i,"remove");
    }

}
